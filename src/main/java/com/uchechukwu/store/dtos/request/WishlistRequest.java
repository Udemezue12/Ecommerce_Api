package com.uchechukwu.store.dtos.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record WishlistRequest(

        @NotNull(message = "Product Id is required")
        UUID productId
) {
}
