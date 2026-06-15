package com.uchechukwu.store.dtos.request;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record ProductUpdateRequest(
        String name,
        String description,
        BigDecimal price,
        UUID categoryId,
        String imageUrl,
        String publicId,
        String resourceType,
        Integer quantity,
        List<ProductImageRequest> images

) {

    public List<ProductImageRequest> getImages() {
        return images;
    }
}
