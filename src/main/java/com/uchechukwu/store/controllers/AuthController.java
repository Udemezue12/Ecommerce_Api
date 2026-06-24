package com.uchechukwu.store.controllers;

import com.uchechukwu.store.api_builder_response.ApiResponse;
import com.uchechukwu.store.dtos.request.LoginRequest;
import com.uchechukwu.store.dtos.request.ResendVerificationRequest;
import com.uchechukwu.store.dtos.request.UserRequestDto;
import com.uchechukwu.store.dtos.request.VerifyEmailRequest;
import com.uchechukwu.store.dtos.response.TokenResponse;
import com.uchechukwu.store.dtos.response.UserResponseDto;
import com.uchechukwu.store.service.AuthenticationService;
import com.uchechukwu.store.service.UserService;
import com.uchechukwu.store.utilities.rateLimiter.RateLimit;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication", description = "Endpoints for user authentication")
public class AuthController {
    private final AuthenticationService authService;
    private final UserService userService;

    @GetMapping("/auth/me")
    @RateLimit(times = 4, seconds = 8)
    public ResponseEntity<ApiResponse<UserResponseDto>> me() {
        return userService.me();
    }


    @GetMapping("/auth/get-current-user")
    @RateLimit
    public ResponseEntity<ApiResponse<Object>> getCurrentUser() {
        return userService.getCurrentUser();
    }

    @PostMapping("/register")
    @RateLimit(times = 4, seconds = 8)
    public ResponseEntity<ApiResponse<UserResponseDto>> registerUser(
            @Valid @RequestBody UserRequestDto userRequest,
            UriComponentsBuilder uriBuilder) {
        return authService.register(userRequest, uriBuilder);
    }

    @PostMapping("/login")
    @RateLimit(times = 4, seconds = 8)
    public ResponseEntity<ApiResponse<TokenResponse>> loginUser(@Valid @RequestBody LoginRequest request,
                                                                HttpServletResponse response) {
        return authService.login(request, response);

    }

    @PostMapping("/refresh")
    @RateLimit(times = 4, seconds = 8)
    public ResponseEntity<TokenResponse> refresh(
            @CookieValue(name = "refresh_token") String refreshToken,

            HttpServletResponse response) {

        var tokens = authService.refresh(refreshToken, response);


        return ResponseEntity.ok(tokens);
    }

    @PostMapping("/logout")
    @RateLimit(times = 4, seconds = 8)
    public ResponseEntity<?> logout(
            HttpServletRequest request,
            HttpServletResponse response) {
        return authService.logout(request, response);
    }

    @PostMapping("/verify-email")
    @RateLimit(times = 4, seconds = 8)
    public Object verifyEmail(@Valid @RequestBody VerifyEmailRequest request) {

        return authService.verifyEmail(request);
    }

    @PostMapping("/forgot-password")
    @RateLimit(times = 4, seconds = 8)
    public Object forgotPassword(@Valid @RequestBody ResendVerificationRequest request) {

        return authService.forgotPassword(request.email());
    }


    @PostMapping("/resend-email-verification-link")
    @RateLimit(times = 4, seconds = 8)
    public Object resendVerification(@Valid @RequestBody ResendVerificationRequest request) {

        var result = authService.resendVerificationEmail(request.email());

        if (result instanceof Map && "Email already verified.".equals(((Map<?, ?>) result).get("message"))) {
            return ResponseEntity.badRequest().body(result);
        }

        return result;
    }

    @PostMapping("/resend-password-verification-link")
    @RateLimit(times = 4, seconds = 8)
    public Object resendPasswordVerification(@Valid @RequestBody ResendVerificationRequest request) {

        return authService.resendPasswordResetLink(request.email());
    }


}
