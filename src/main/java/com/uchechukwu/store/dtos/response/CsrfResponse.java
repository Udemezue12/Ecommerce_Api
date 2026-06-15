package com.uchechukwu.store.dtos.response;

public record CsrfResponse(
        String token,
        String headerName,
        String parameterName
) {
}