package com.uchechukwu.store.controllers;

import com.uchechukwu.store.dtos.response.CsrfResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.WebUtils;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "CSRF TOKEN", description = "Endpoints for csrfToken generation")
public class CsrfController {

    @GetMapping("/csrf")
    public ResponseEntity<CsrfResponse> csrf(
            CsrfToken csrfToken) {
        System.out.println(
                "Generated CSRF Token: "
                        + csrfToken.getToken()
        );
        System.out.println("Generated CSRF Header " + csrfToken.getHeaderName());

        return ResponseEntity.ok(
                new CsrfResponse(
                        csrfToken.getToken(),
                        csrfToken.getHeaderName(),
                        csrfToken.getParameterName()
                )
        );
    }

    @GetMapping("/csrf/custom")
    public ResponseEntity<CsrfResponse> csrf(
            HttpServletRequest request,
            CsrfToken token) {

        var cookie =
                WebUtils.getCookie(
                        request,
                        "XSRF-TOKEN");

        System.out.println(
                "Cookie = "
                        + (cookie != null
                        ? cookie.getValue()
                        : null));

        System.out.println(
                "Token = "
                        + token.getToken());

        return ResponseEntity.ok(
                new CsrfResponse(
                        token.getToken(),
                        token.getHeaderName(),
                        token.getParameterName()
                )
        );
    }

}