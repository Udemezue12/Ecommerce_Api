package com.uchechukwu.store.dtos.response;

import com.uchechukwu.store.enums.DeliveryStatus;
import com.uchechukwu.store.enums.DeliveryType;
import com.uchechukwu.store.enums.PaymentMethod;
import com.uchechukwu.store.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public interface DeliveryFlatRow {

    String getDeliveryId();

    DeliveryStatus getStatus();

    DeliveryType getDeliveryType();

    String getPickupAddress();

    String getDeliveryAddress();

    Instant getEstimatedDelivery();

    String getNotes();

    String getCustomerId();

    String getCustomerName();

    String getCustomerEmail();

    String getCustomerPhone();

    UUID getOrderId();


    BigDecimal getOrderTotal();

    String getProductId();

    String getProductName();

    Integer getQuantity();

    BigDecimal getUnitPrice();

    String getProviderTxId();

    PaymentMethod getPaymentMethod();

    String getProviderRef();

    PaymentStatus getPaymentStatus();
}
