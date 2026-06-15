package com.uchechukwu.store.mappers;

import com.uchechukwu.store.dtos.response.DeliveryDetailResponse;
import com.uchechukwu.store.dtos.response.DeliveryFlatRow;
import com.uchechukwu.store.dtos.response.DeliveryManyResponse;
import com.uchechukwu.store.dtos.response.DeliveryResponse;
import com.uchechukwu.store.entities.*;
import com.uchechukwu.store.enums.PaymentStatus;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class DeliveryMapper {
    public static DeliveryManyResponse toManyResponse(Delivery delivery) {
        var riderName = delivery.getRider().getFullName() != null ? delivery.getRider().getFullName() : "Logistics Company was used for this delivery";
        var trackingId = delivery.getTrackingId() != null ? delivery.getTrackingId() : "Rider was used for this delivery";
        var logisticsCompany = delivery.getLogisticsCompany() != null ? delivery.getLogisticsCompany() : "Rider was used for this delivery";
        return new DeliveryManyResponse(
                delivery.getId(),
                riderName,
                trackingId,
                logisticsCompany,
                delivery.getOrder().getId(),
                delivery.getPaymentTransaction().getId(),
                delivery.getDeliveryType(),
                delivery.getCustomer().getName(),
                delivery.getStatus(),
                delivery.getCreatedAt(),
                delivery.getNotes()
        );

    }

    public static DeliveryDetailResponse toDetailResponse(Delivery delivery) {
        if (delivery == null) return null;

        var order = delivery.getOrder();
        User customer = delivery.getCustomer();

        var tx = extractSuccessfulTransaction(order);

        var customerInfo = (customer == null) ? null :
                new DeliveryDetailResponse.CustomerInfo(
                        customer.getId(),
                        customer.getName(),
                        customer.getEmail(),
                        customer.getPhoneNumber()
                );

        var paymentDetails = (tx == null) ? null :
                new DeliveryDetailResponse.PaymentDetails(
                        tx.getPaymentProviderTransactionId(),
                        tx.getPaymentMethod(),
                        tx.getGeneratedReference(),
                        tx.getStatus()
                );

        List<DeliveryDetailResponse.ItemInfo> itemsList =
                (order == null || order.getItems() == null)
                        ? List.of()
                        : order.getItems().stream()
                        .map(item -> new DeliveryDetailResponse.ItemInfo(
                                item.getProduct().getId(),
                                item.getProduct().getName(),
                                item.getQuantity(),
                                item.getUnitPrice()
                        ))
                        .toList();

        var orderInfo = (order == null)
                ? null
                : new DeliveryDetailResponse.OrderInfo(
                order.getId(),
                order.getTotalPrice(),
                itemsList
        );

        return new DeliveryDetailResponse(
                delivery.getId(),
                delivery.getStatus(),
                delivery.getDeliveryType(),
                delivery.getPickupAddress(),
                delivery.getDeliveryAddress(),
                delivery.getEstimatedDelivery(),
                delivery.getNotes(),
                customerInfo,
                orderInfo,
                paymentDetails
        );
    }

    // =========================
    // SIMPLE RESPONSE
    // =========================
    public static DeliveryResponse response(Delivery delivery) {
        if (delivery == null) return null;

        Order order = delivery.getOrder();
        User customer = delivery.getCustomer();

        return new DeliveryResponse(
                delivery.getId(),
                order != null ? order.getId() : null,
                customer != null ? customer.getId() : null,
                delivery.getPaymentTransaction() != null ? delivery.getPaymentTransaction().getId() : null,
                delivery.getStatus(),
                delivery.getDeliveryType(),
                delivery.getPickupAddress(),
                delivery.getDeliveryAddress(),
                delivery.getEstimatedDelivery(),
                delivery.getDeliveredAt(),
                delivery.getNotes(),
                getRiderInfo(delivery),
                getLogisticsInfo(delivery)
        );
    }

    // =========================
    // HELPERS
    // =========================
    private static PaymentTransaction extractSuccessfulTransaction(Order order) {
        if (order == null || order.getTransactions() == null) {
            return null;
        }

        return order.getTransactions()
                .stream()
                .filter(tx -> tx.getStatus() == PaymentStatus.SUCCESS)
                .findFirst()
                .orElse(null);
    }

    private static DeliveryResponse.@Nullable LogisticsInfo getLogisticsInfo(Delivery delivery) {
        if (delivery.getLogisticsCompany() == null || delivery.getLogisticsCompany().isBlank()) {
            return null;
        }

        return new DeliveryResponse.LogisticsInfo(
                delivery.getLogisticsCompany(),
                delivery.getTrackingId()
        );
    }

    private static DeliveryResponse.@Nullable RiderInfo getRiderInfo(Delivery delivery) {
        if (delivery.getRider() == null) return null;

        Rider rider = delivery.getRider();

        return new DeliveryResponse.RiderInfo(
                rider.getId(),
                rider.getFirstName() + " " + rider.getLastName(),
                rider.getPhone(),
                rider.getVehiclePlateNumber(),
                rider.getImageUrl() != null ? rider.getImageUrl() : "No Image"
        );
    }

    public static DeliveryDetailResponse fromFlat(List<DeliveryFlatRow> rows) {
        if (rows.isEmpty()) return null;

        var first = rows.getFirst();

        var customer = new DeliveryDetailResponse.CustomerInfo(

                UUID.fromString(first.getCustomerId()),
                first.getCustomerName(),
                first.getCustomerEmail(),
                first.getCustomerPhone()
        );

        var items = rows.stream()
                .map(r -> new DeliveryDetailResponse.ItemInfo(
                        UUID.fromString(r.getProductId()),
                        r.getProductName(),
                        r.getQuantity(),
                        r.getUnitPrice()
                ))
                .toList();

        var order = new DeliveryDetailResponse.OrderInfo(
                first.getOrderId(),
                first.getOrderTotal(),
                items
        );

        var payment = first.getProviderTxId() == null ? null :
                new DeliveryDetailResponse.PaymentDetails(
                        first.getProviderTxId(),
                        first.getPaymentMethod(),
                        first.getProviderRef(),
                        first.getPaymentStatus()
                );

        return new DeliveryDetailResponse(
                UUID.fromString(first.getDeliveryId()),
                first.getStatus(),
                first.getDeliveryType(),
                first.getPickupAddress(),
                first.getDeliveryAddress(),
                first.getEstimatedDelivery(),
                first.getNotes(),
                customer,
                order,
                payment
        );
    }
}