package com.uchechukwu.store.dtos.request;

import com.uchechukwu.store.enums.PaymentMethod;

import jakarta.validation.constraints.NotNull;

public record PaymentRequest(

        @NotNull(
                message = "Payment method is required"
        )
        PaymentMethod paymentMethod

) {
}
