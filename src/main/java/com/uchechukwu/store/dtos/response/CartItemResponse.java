package com.uchechukwu.store.dtos.response;

import java.math.BigDecimal;
import java.util.UUID;

public record CartItemResponse(
        UUID Id,
        CartResponse cart,
        Integer quantity,
        ProductDto product,
        BigDecimal totalAmount
) {

}
