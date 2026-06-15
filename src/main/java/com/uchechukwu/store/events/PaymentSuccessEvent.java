package com.uchechukwu.store.events;

import java.util.UUID;

public record PaymentSuccessEvent(
        UUID orderId,
        String name,
        String email,
        UUID transactionId,
        String phoneNumber
) {
}