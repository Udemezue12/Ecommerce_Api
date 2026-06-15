package com.uchechukwu.store.dtos.response;

public record TokenResponse(
        String accessToken,
        String refreshToken
) {

}
