package com.uchechukwu.store.dtos.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AddItemToCartRequest(

        @NotNull(message = "Product ID is required") UUID productId

) {

}
