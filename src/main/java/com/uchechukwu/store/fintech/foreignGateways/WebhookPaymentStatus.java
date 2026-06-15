package com.uchechukwu.store.fintech.foreignGateways;

import com.uchechukwu.store.enums.OrderStatus;

import java.util.UUID;

public record WebhookPaymentStatus(
        UUID orderId,
        OrderStatus status
) {
}
