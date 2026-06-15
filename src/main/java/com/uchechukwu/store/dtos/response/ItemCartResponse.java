package com.uchechukwu.store.dtos.response;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ItemCartResponse(
        UUID cartId,
        LocalDateTime dateCreated,
        LocalDateTime dateUpdated,
        List<GetItemCart> items,
        Integer totalItems,
        BigDecimal totalPrice
) {
}
