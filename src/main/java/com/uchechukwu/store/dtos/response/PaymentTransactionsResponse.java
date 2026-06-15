package com.uchechukwu.store.dtos.response;

import com.uchechukwu.store.enums.PaymentMethod;
import com.uchechukwu.store.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record PaymentTransactionsResponse(
        UUID id,
        String generatedReference,
        String paymentProviderTransactionId,
        String currency,
        String paymentChannel,
        PaymentMethod paymentMethod,
        PaymentStatus status,
        BigDecimal amount,
        UUID orderId, Boolean orderProcessed,
        String customerName,
        LocalDateTime paidAt,
        LocalDateTime createdAt
) {
}
