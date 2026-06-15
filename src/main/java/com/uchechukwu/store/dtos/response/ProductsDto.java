package com.uchechukwu.store.dtos.response;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductsDto(UUID id, String name, String description, BigDecimal price, String thumbNail, UUID categoryId,
                          String categoryName) {
}
