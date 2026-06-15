package com.uchechukwu.store.securityFilters;

import com.uchechukwu.store.service.BlacklistedTokenService;
import com.uchechukwu.store.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    private final JWTService jwtService;
    private final BlacklistedTokenService blacklistService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = getAccessTokenFromHeader(request);
        if (token != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            var jwt = jwtService.parseToken(token);
            if (jwt != null && !jwt.isExpired() && !blacklistService.isBlacklisted(jwt.getJti())) {
                var userId = jwt.getUserId();
                var role = jwt.getUserRole();
                var result = List.of(new SimpleGrantedAuthority("ROLE_" + role));
                var authToken = new UsernamePasswordAuthenticationToken(userId, null, result);
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);

            }
        }
        filterChain.doFilter(request, response);

    }


    private String getAccessTokenFromHeader(HttpServletRequest request) {
        String token = null;
        var authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);

        }
        if (token == null && request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("access_token".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }

        }
        return token;
    }
}
