package com.uchechukwu.store.dtos.response;

import java.math.BigDecimal;
import java.util.UUID;

public record CartProductDto(
        UUID id,
        String name,
        BigDecimal price
) {


}
