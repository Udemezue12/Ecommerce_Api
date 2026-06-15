package com.uchechukwu.store.entities;

import com.uchechukwu.store.enums.PaymentMethod;
import com.uchechukwu.store.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "payment_transactions")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.BINARY)
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "generated_reference")
    private String generatedReference;
    @Column(name = "payment_provider_transaction_id")
    private String paymentProviderTransactionId;
    @Column(name = "currency")
    private String currency;
    @Column(name = "payment_channel")
    private String paymentChannel;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PaymentStatus status;

    @Column(name = "amount", precision = 38, scale = 2)
    private BigDecimal amount;

    @Column(name = "order_processed")
    @Builder.Default
    private Boolean orderProcessed = false;

    @Column(name = "payment_provider_reference")
    private String paymentProviderReference;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "order_id",
            nullable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    private Order order;

    @Column(name = "paid_at")
    private LocalDateTime paidAt;
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "verified_at")
    private LocalDateTime verifiedAt;
    @Column(name = "refunded_at")
    private LocalDateTime refundedAt;

    @Column(name = "failed_at")
    private LocalDateTime failedAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }
}
