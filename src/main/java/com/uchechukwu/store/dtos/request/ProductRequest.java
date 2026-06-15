package com.uchechukwu.store.dtos.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record ProductRequest(

        @NotBlank(message = "Product name is required")
        String name,

        @NotNull(message = "Quantity is required")
        @Min(
                value = 0,
                message = "Quantity cannot be negative"
        )
        Integer quantity,

        @NotBlank(message = "Description is required")
        String description,


        @NotNull(message = "Price is required")
        @DecimalMin(
                value = "0.01",
                message = "Price must be greater than 0"
        )
        @Digits(
                integer = 10,
                fraction = 2,
                message = "Price must have at most 2 decimal places"
        )
        BigDecimal price,

        @NotNull(message = "Category is required")

        UUID categoryId,
        List<ProductImageRequest> images
) {
    public List<ProductImageRequest> getImages() {
        return images;
    }
}