package com.uchechukwu.store.dtos.response;

import com.uchechukwu.store.enums.DeliveryStatus;
import com.uchechukwu.store.enums.DeliveryType;
import com.uchechukwu.store.enums.PaymentMethod;
import com.uchechukwu.store.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record DeliveryDetailResponse(
        UUID deliveryId,
        DeliveryStatus status,
        DeliveryType deliveryType,
        String pickupAddress,
        String deliveryAddress,
        Instant estimatedDelivery,
        String notes,


        CustomerInfo customer,

        OrderInfo order,

        PaymentDetails payment
) {
    public record CustomerInfo(UUID id, String fullName, String email, String phone) {
    }

    public record OrderInfo(UUID id, BigDecimal totalAmount, List<ItemInfo> items) {
    }

    public record ItemInfo(UUID productId, String productName, int quantity, BigDecimal price) {
    }

    public record PaymentDetails(String providerTransactionId, PaymentMethod paymentMethod, String providerReference,
                                 PaymentStatus status) {
    }
}