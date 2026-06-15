package com.uchechukwu.store.dtos.request;

public record VerifyEmailRequest(
        String otp,

        String token
) {
}
