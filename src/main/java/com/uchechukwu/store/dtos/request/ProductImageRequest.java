package com.uchechukwu.store.dtos.request;

import jakarta.validation.constraints.NotBlank;

public record ProductImageRequest(

        @NotBlank(message = "Product Image is required")
        String imageUrl,
        @NotBlank(message = "Public ID is required")
        String publicId,
        @NotBlank(message = "Resource Type is required")
        String resourceType
) {
}
