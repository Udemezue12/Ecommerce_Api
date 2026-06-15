package com.uchechukwu.store.dtos.response;

import com.uchechukwu.store.enums.DeliveryStatus;
import com.uchechukwu.store.enums.DeliveryType;

import java.time.Instant;
import java.util.UUID;

public record DeliveryResponse(
        UUID deliveryId,
        UUID orderId,
        UUID customerId,
        UUID paymentTransactionId,
        DeliveryStatus status,
        DeliveryType deliveryType,
        String pickupAddress,
        String deliveryAddress,
        Instant estimatedDelivery,
        Instant deliveredAt,
        String notes,


        RiderInfo rider,


        LogisticsInfo logistics
) {
    public record RiderInfo(
            UUID id,
            String fullName,
            String phone,
            String vehiclePlateNumber,
            String imageUrl
    ) {
    }

    public record LogisticsInfo(
            String companyName,
            String trackingId
    ) {
    }
}