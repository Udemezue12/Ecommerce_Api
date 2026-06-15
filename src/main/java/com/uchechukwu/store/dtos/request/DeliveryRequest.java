package com.uchechukwu.store.dtos.request;

import com.uchechukwu.store.enums.DeliveryType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.util.UUID;

public record DeliveryRequest(
        UUID riderId,
        String logisticsCompany,
        String trackingId,
        @NotNull(message = "Delivery Type is required")
        DeliveryType deliveryType,
        Instant estimatedDelivery,

        @NotBlank(message = "Pickup Address is needed")
        @Size(min = 6, max = 256)
        String pickUpAddress,

        @NotBlank(message = "Notes is needed")
        @Size(min = 6, max = 2246)
        String notes


) {
}
