package com.uchechukwu.store.dtos.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record OrderDto(
        UUID id,
        String status,
        BigDecimal totalPrice,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        List<OrderItemDto> items
) {
}
