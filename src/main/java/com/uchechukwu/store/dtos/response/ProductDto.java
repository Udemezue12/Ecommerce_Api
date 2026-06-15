package com.uchechukwu.store.dtos.response;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record ProductDto(
        UUID id,
        String name,
        String description,
        BigDecimal price,
        List<String> imageUrls,
        UUID categoryId,
        String categoryName
) {
}
