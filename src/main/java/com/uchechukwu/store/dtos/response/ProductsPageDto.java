package com.uchechukwu.store.dtos.response;

import java.util.List;

public record ProductsPageDto(
        List<ProductsDto> content,
        int page,
        int size,
        long totalElements,
        int totalPages,
        boolean first,
        boolean last
) {
}
