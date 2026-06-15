package com.uchechukwu.store.entities;

import com.uchechukwu.store.enums.DeliveryStatus;
import com.uchechukwu.store.enums.DeliveryType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "deliveries")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.BINARY)
    @Column(name = "id")
    private UUID id;


    @Column(name = "logistics_company")
    private String logisticsCompany;


    @Column(name = "tracking_id")
    private String trackingId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private DeliveryStatus status;


    @Enumerated(EnumType.STRING)
    @Column(name = "delivery_type", nullable = false)
    private DeliveryType deliveryType;

    @Column(name = "estimated_delivery")
    private Instant estimatedDelivery;

    @Column(name = "delivered_at")
    private Instant deliveredAt;
    @Column(name = "picked_up_at")
    private Instant pickedUpAt;
    @Column(name = "cancelled_at")
    private Instant cancelledAt;
    @Column(name = "failed_at")
    private Instant failedAt;


    @Column(name = "pickup_address")
    private String pickupAddress;


    @Column(name = "delivery_address")
    private String deliveryAddress;


    @Column(name = "notes")
    private String notes;


    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    private Instant updatedAt;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false, unique = true)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private User customer;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_transaction_id")
    private PaymentTransaction paymentTransaction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id")
    private Rider rider;


}