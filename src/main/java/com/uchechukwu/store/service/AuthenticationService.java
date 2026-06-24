package com.uchechukwu.store.service;

import com.uchechukwu.store.api_builder_response.ApiResponse;
import com.uchechukwu.store.api_builder_response.ApiResponseBuilder;
import com.uchechukwu.store.core.OtpRateLimit;
import com.uchechukwu.store.dtos.request.LoginRequest;
import com.uchechukwu.store.dtos.request.ResetPasswordRequest;
import com.uchechukwu.store.dtos.request.UserRequestDto;
import com.uchechukwu.store.dtos.request.VerifyEmailRequest;
import com.uchechukwu.store.dtos.response.TokenResponse;
import com.uchechukwu.store.dtos.response.UserResponseDto;
import com.uchechukwu.store.enums.JwtType;
import com.uchechukwu.store.exceptions.BadRequestException;
import com.uchechukwu.store.exceptions.ResourceNotFoundException;
import com.uchechukwu.store.jwt.Jwt;
import com.uchechukwu.store.jwt.JwtResponseCookie;
import com.uchechukwu.store.mappers.UserMapper;
import com.uchechukwu.store.repositories.UserRepository;
import com.uchechukwu.store.tasks.SendVerifyAndPasswordResetEmail;
import com.uchechukwu.store.validators.RequestValidators;
import com.uchechukwu.store.verification.UserVerification;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jobrunr.scheduling.JobScheduler;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final JWTService jwtService;
    private final BlacklistedTokenService blacklistService;
    private final UserRepository userRepository;

    private final ApplicationEventPublisher applicationEventPublisher;

    private final RequestValidators requestValidate;
    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;
    private final OtpRateLimit otpRateLimit;

    public final JwtResponseCookie jwtResponseCookie;

    private final UserVerification verificationService;
    private final JobScheduler jobScheduler;
    private final SendVerifyAndPasswordResetEmail notificationService;

    @Transactional
    public ResponseEntity<ApiResponse<UserResponseDto>> register(UserRequestDto userRequest,
                                                                 UriComponentsBuilder uriBuilder) {
        requestValidate.throwIfTrue(
                userRepository.findByEmail(userRequest.email().trim().toLowerCase()).isPresent(),
                "Email already exists");
        var user = userMapper.toEntity(userRequest);
        user.setVerified(false);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);

        var userDto = userMapper.getUserResponseDto(user);
        if (userDto.getEmail() != null) {
            resendVerificationEmail(userDto.getEmail());
        }

        return ApiResponseBuilder.created(
                "User created successfully",
                "/users/{id}",
                userDto.getId(),
                userDto,
                uriBuilder);
    }

    public ResponseEntity<ApiResponse<TokenResponse>> login(
            LoginRequest request,
            HttpServletResponse response) {

        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()));

        var user = userRepository.findByEmail(request.email())
                .orElseThrow();

        var accessToken = jwtService.generateAccessToken(user);

        var refreshToken = jwtService.generateRefreshToken(user);

        jwtResponseCookie.setCookies(
                response,
                accessToken.toString(),
                refreshToken.toString());

        return ApiResponseBuilder.success(
                "Logged in Successfully",
                new TokenResponse(accessToken.toString(), refreshToken.toString()));
    }

    public ResponseEntity<?> logout(
            HttpServletRequest request,
            HttpServletResponse response) {
        var accessToken = jwtResponseCookie.extractCookie(request, "access_token");
        var refreshToken = jwtResponseCookie.extractCookie(request, "refresh_token");
        var accessJwt = jwtService.parseToken(accessToken);

        var refreshJwt = jwtService.parseToken(refreshToken);

        blacklistService.blacklist(accessJwt);
        blacklistService.blacklist(refreshJwt);
        jwtResponseCookie.clearCookies(response);
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok(
                Map.of("message", "Logout successful"));
    }

    @Transactional
    public TokenResponse refresh(String refreshToken, HttpServletResponse response) {

        var jwt = jwtService.parseToken(refreshToken);

        validateRefreshToken(jwt);

        var user = userRepository.findById(
                        jwt.getUserId())
                .orElseThrow(
                        () -> new RuntimeException("User not found"));

        blacklistService.blacklist(jwt);

        var newAccessToken = jwtService.generateAccessToken(user);

        var newRefreshToken = jwtService.generateRefreshToken(user);
        jwtResponseCookie.setCookies(
                response,
                newAccessToken.toString(),
                newRefreshToken.toString());

        return new TokenResponse(
                newAccessToken.toString(),
                newRefreshToken.toString());
    }

    private void validateRefreshToken(Jwt jwt) {

        if (jwt == null) {
            throw new RuntimeException(
                    "Invalid refresh token");
        }

        if (jwt.isExpired()) {
            throw new RuntimeException(
                    "Refresh token expired");
        }

        if (jwt.getType() != JwtType.REFRESH) {
            throw new RuntimeException(
                    "Invalid token type");
        }

        if (blacklistService.isBlacklisted(jwt.getJti())) {
            throw new RuntimeException(
                    "Refresh token revoked");
        }
    }

    @Transactional
    public Object resendVerificationEmail(
            String email) {

        var user = userRepository.findByEmail(email)
                .orElse(null);

        if (user != null) {

            if (Boolean.TRUE.equals(
                    user.getVerified())) {

                return Map.of(
                        "message",
                        "Email already verified.");
            }

            var allowed = otpRateLimit
                    .canRequestOtp(user.getEmail());

            if (allowed) {

                var otp = verificationService
                        .generateOtp(user.getEmail());

                var token = verificationService
                        .generateVerifyToken(
                                user.getEmail());

                otpRateLimit
                        .recordOtp(
                                user.getEmail());
                var userEmail = user.getEmail();
                var name = user.getName();
                var phoneNumber = user.getPhoneNumber();

                jobScheduler.enqueue(
                        () -> notificationService.sendVerificationEmail(userEmail,
                                otp,
                                name,
                                token));
                jobScheduler.enqueue(
                        () -> notificationService.sendVerificationSms(
                                phoneNumber,
                                otp,
                                name

                        ));
            }
        }

        return Map.of(
                "message",
                "If the email exists, a verification message has been sent");
    }

    @Transactional
    public Object resendPasswordResetLink(
            String email) {

        var user = userRepository.findByEmail(email)
                .orElse(null);

        Long retryAfter = null;

        if (user != null) {

            boolean allowed = otpRateLimit
                    .canRequestOtp(user.getEmail());

            if (allowed) {

                var token = verificationService
                        .generateResetToken(
                                user.getEmail());

                var otp = verificationService
                        .generateOtp(
                                user.getEmail());
                var userEmail = user.getEmail();
                var name = user.getName();
                var phoneNumber = user.getPhoneNumber();

                otpRateLimit
                        .recordOtp(
                                user.getEmail());

                jobScheduler.enqueue(
                        () -> notificationService
                                .sendPasswordResetEmail(
                                        userEmail,
                                        otp,
                                        name,
                                        token));
                jobScheduler.enqueue(
                        () -> notificationService
                                .sendPasswordResetSms(
                                        phoneNumber,
                                        otp,
                                        name

                                ));
            }

            retryAfter = otpRateLimit
                    .getRetryAfter(
                            user.getEmail());
        }

        assert retryAfter != null;
        return Map.of(
                "message",
                "If the email exists, a reset link has been sent",
                "retry_after",
                retryAfter);
    }

    @Transactional
    public Object forgotPassword(
            String email) {

        return resendPasswordResetLink(email);
    }

    @Transactional
    public Object verifyEmail(
            VerifyEmailRequest request) {

        if ((request.otp() == null
                && request.token() == null)
                ||
                (request.otp() != null
                        && request.token() != null)) {

            throw new BadRequestException("Provide either a token or an Otp");
        }

        String email;

        if (request.otp() != null) {

            email = verificationService.verifyOtp(
                    request.otp());

        } else {

            email = verificationService.verifyVerifyToken(
                    request.token());
        }

        if (email == null) {

            throw new BadRequestException(
                    "Invalid or expired verification");
        }

        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found"));

        user.setVerified(true);
        user.setVerifiedAt(LocalDateTime.now());

        userRepository.save(user);

        return Map.of(
                "message",
                "Email verified successfully");
    }

    @Transactional
    public Object resetPassword(
            ResetPasswordRequest request) {

        String email = null;

        if (request.token() != null
                && !request.token().isBlank()) {

            email = verificationService.verifyResetToken(
                    request.token());
        }

        if (email == null
                && request.otp() != null
                && !request.otp().isBlank()) {

            email = verificationService.verifyOtp(
                    request.otp());
        }

        if (email == null) {

            throw new BadRequestException(
                    "Invalid or expired token");
        }

        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Invalid reset data"));

        user.setPassword(passwordEncoder.encode(request.newPassword()));

        userRepository.save(user);

        return Map.of(
                "message",
                "Password reset successfully");
    }

}
