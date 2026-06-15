package com.uchechukwu.store.dtos.response;

import com.uchechukwu.store.enums.DeliveryStatus;
import com.uchechukwu.store.enums.DeliveryType;

import java.time.Instant;
import java.util.UUID;

public record DeliveryManyResponse(UUID id, String riderName, String trackingId, String logisticsCompany, UUID orderId,
                                   UUID paymentId, DeliveryType deliveryType, String customerName,
                                   DeliveryStatus status,
                                   Instant createdAt, String notes) {
}
