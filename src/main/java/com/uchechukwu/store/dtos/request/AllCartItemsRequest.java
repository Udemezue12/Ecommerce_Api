package com.uchechukwu.store.dtos.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record AllCartItemsRequest(
        @NotEmpty(message = "Items cannot be empty") @Valid List<Item> items

) {

    public record Item(

            @NotNull(message = "Product ID is required") UUID productId,

            @NotNull(message = "Quantity is required") @Min(value = 1, message = "Quantity must be at least 1") Integer quantity

    ) {
    }
}
