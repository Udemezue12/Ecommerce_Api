/**
 * csrf.js — CSRF Token Manager for Astro Stores
 *
 * Fetches the CSRF token from GET /csrf once, caches it in memory,
 * and exposes helpers for fetch() calls that need X-XSRF-TOKEN.
 *
 * Usage:
 *   import { csrfFetch, getCsrfHeaders } from './csrf.js';
 *   await csrfFetch('/api/v1/product/create', { method: 'POST', body: JSON.stringify(data) });
 */

const CSRF_ENDPOINT = '/csrf';

/** @type {{ token: string, headerName: string, parameterName: string } | null} */
let _csrfCache = null;
let _fetchPromise = null;

/**
 * Fetches and caches the CSRF token from the server.
 * Subsequent calls within the same session return the cached value.
 * @returns {Promise<{ token: string, headerName: string, parameterName: string }>}
 */
async function loadCsrfToken() {
    if (_csrfCache) return _csrfCache;

    // Deduplicate concurrent calls — only one in-flight request at a time
    if (_fetchPromise) return _fetchPromise;

    _fetchPromise = fetch(CSRF_ENDPOINT, {
        method: 'GET',
        credentials: 'include',          // send session cookie so server recognises the session
        headers: {'Accept': 'application/json'},
    })
        .then(res => {
            if (!res.ok) throw new Error(`CSRF endpoint returned ${res.status}`);
            return res.json();
        })
        .then(data => {
            _csrfCache = {
                token: data.token,
                headerName: data.headerName,     // typically "X-XSRF-TOKEN"
                parameterName: data.parameterName // typically "_csrf"
            };
            _fetchPromise = null;
            return _csrfCache;
        })
        .catch(err => {
            _fetchPromise = null;
            console.error('[csrf.js] Failed to load CSRF token:', err);
            throw err;
        });

    return _fetchPromise;
}

/**
 * Returns headers object containing the CSRF token header.
 * Await this before constructing your fetch options.
 * @returns {Promise<Record<string, string>>}
 */
async function getCsrfHeaders() {
    const csrf = await loadCsrfToken();
    return {
        [csrf.headerName]: csrf.token,   // "X-XSRF-TOKEN": "<token>"
    };
}

/**
 * Drop-in replacement for window.fetch that automatically attaches
 * the CSRF token header on mutating requests (POST, PUT, PATCH, DELETE).
 *
 * @param {string | URL} url
 * @param {RequestInit} [options]
 * @returns {Promise<Response>}
 */
async function csrfFetch(url, options = {}) {
    const method = (options.method || 'GET').toUpperCase();
    const mutating = ['POST', 'PUT', 'PATCH', 'DELETE'].includes(method);

    if (mutating) {
        const csrfHeaders = await getCsrfHeaders();
        options.headers = {
            'Content-Type': 'application/json',
            ...options.headers,
            ...csrfHeaders,              // X-XSRF-TOKEN always wins
        };
        options.credentials = options.credentials ?? 'include';
    }

    return fetch(url, options);
}

/**
 * Invalidates the cached token — call this after a 403 response
 * to force a fresh fetch on the next mutating request.
 */
function invalidateCsrfToken() {
    _csrfCache = null;
    _fetchPromise = null;
}

/**
 * Convenience wrapper: POST JSON and return parsed response body.
 * Throws on non-2xx responses.
 *
 * @param {string} url
 * @param {unknown} body
 * @returns {Promise<unknown>}
 */
async function postJson(url, body) {
    const res = await csrfFetch(url, {
        method: 'POST',
        body: JSON.stringify(body),
    });

    if (res.status === 403) {
        // Token may have expired — invalidate and retry once
        invalidateCsrfToken();
        const retry = await csrfFetch(url, {
            method: 'POST',
            body: JSON.stringify(body),
        });
        if (!retry.ok) throw new Error(`POST ${url} failed with ${retry.status}`);
        return retry.json();
    }

    if (!res.ok) throw new Error(`POST ${url} failed with ${res.status}`);
    return res.json();
}

/**
 * Convenience wrapper: PATCH JSON.
 * @param {string} url
 * @param {unknown} body
 * @returns {Promise<unknown>}
 */
async function patchJson(url, body) {
    const res = await csrfFetch(url, {
        method: 'PATCH',
        body: JSON.stringify(body),
    });
    if (!res.ok) throw new Error(`PATCH ${url} failed with ${res.status}`);
    return res.json();
}

/**
 * Convenience wrapper: DELETE.
 * @param {string} url
 * @returns {Promise<void>}
 */
async function deleteResource(url) {
    const res = await csrfFetch(url, {method: 'DELETE'});
    if (!res.ok) throw new Error(`DELETE ${url} failed with ${res.status}`);
}

// ── Pre-warm the CSRF token on page load ───────────────────────────────────
// This fires a single GET /csrf request immediately so the token is ready
// before the user clicks anything that triggers a mutation.
if (typeof window !== 'undefined') {
    window.addEventListener('DOMContentLoaded', () => {
        loadCsrfToken().catch(() => {
            // Non-fatal on page load — will retry when first mutation fires
        });
    });
}

// ── Exports (works both as ES module and as plain script with window.CSRF) ─
const CSRF = {loadCsrfToken, getCsrfHeaders, csrfFetch, postJson, patchJson, deleteResource, invalidateCsrfToken};

if (typeof window !== 'undefined') {
    window.CSRF = CSRF;
}

export {loadCsrfToken, getCsrfHeaders, csrfFetch, postJson, patchJson, deleteResource, invalidateCsrfToken};
export default CSRF;