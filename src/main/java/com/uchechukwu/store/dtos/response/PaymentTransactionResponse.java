package com.uchechukwu.store.dtos.response;

import com.uchechukwu.store.enums.PaymentMethod;
import com.uchechukwu.store.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record PaymentTransactionResponse(UUID id,
                                         String generatedReference,
                                         String paymentProviderTransactionId,
                                         String currency,
                                         String paymentChannel,
                                         PaymentMethod paymentMethod,
                                         PaymentStatus status,
                                         BigDecimal amount,
                                         Boolean orderProcessed,
                                         UUID userId,
                                         OrderDto order,
                                         LocalDateTime paidAt,
                                         LocalDateTime createdAt
) {

    public record OrderDto(
            UUID id,
            List<OrderItemDto> items
    ) {
    }

    public record OrderItemDto(
            UUID id,
            UUID productId,
            String productName,
            Integer quantity,
            BigDecimal price
    ) {
    }
}
