package com.uchechukwu.store.dtos.response;

import java.math.BigDecimal;
import java.util.UUID;

public record GetItemCart(
        UUID productId,
        String productName,
        BigDecimal unitPrice,
        Integer quantity,
        BigDecimal totalPrice) {
}
