package com.uchechukwu.store.dtos.request;

import com.uchechukwu.store.enums.PaymentMethod;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CheckoutRequest(

        @NotNull(message = "CartId is required")
        UUID cartId,

        // @NotBlank(message = "Currency is required")
        // String currency,

        @NotNull(message = "Payment Method is required")
        PaymentMethod paymentMethod

) {
}
