package com.uchechukwu.store.dtos.response;

import java.math.BigDecimal;
import java.util.UUID;

public record WishlistResponse(
        UUID productId,
        String productName,
        BigDecimal productPrice,
        String productImageUrl,
        boolean isAvailable
//        LocalDateTime addedAt
) {
}
