package com.uchechukwu.store.dtos.request;

import com.uchechukwu.store.enums.DeliveryStatus;

import jakarta.validation.constraints.NotNull;

public record UpdateDeliveryStatusRequest(
        @NotNull(message = "Delivery Type is required")
        DeliveryStatus deliveryStatus
) {
}
