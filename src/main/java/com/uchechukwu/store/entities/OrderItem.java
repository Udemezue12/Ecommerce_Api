package com.uchechukwu.store.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "order_items", uniqueConstraints = {
        @UniqueConstraint(name = "uq_order_product", columnNames = {"order_id", "product_id"})
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.BINARY)
    @Column(name = "id")
    private UUID id;


    @ManyToOne(fetch = FetchType.LAZY)
//    @BatchSize(size = 50)
    @JoinColumn(name = "product_id")
    private Product product;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Min(1)
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "unit_price")
    private BigDecimal unitPrice;

    @Column(name = "total_price")
    private BigDecimal totalPrice;


}
