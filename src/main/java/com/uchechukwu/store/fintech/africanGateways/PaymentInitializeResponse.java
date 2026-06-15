package com.uchechukwu.store.fintech.africanGateways;

public record PaymentInitializeResponse(
        String authorizationUrl,
        // private String accessCode;
        String reference
) {

}
