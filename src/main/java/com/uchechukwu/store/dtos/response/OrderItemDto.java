package com.uchechukwu.store.dtos.response;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderItemDto(
        UUID id,
        Integer quantity,
        BigDecimal unitPrice,
        BigDecimal totalPrice,
        UUID orderId,
        UUID productId,
        String productName

) {

}
