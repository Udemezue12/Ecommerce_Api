package com.uchechukwu.store.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "inventories",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"product_id", "user_id"}
                )
        }
)
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.BINARY)
    @Column(name = "id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "product_id",
            nullable = false
    )
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            nullable = false
    )
    private User user;

    @Column(name = "available_quantity", nullable = false)
    private Integer availableQuantity;

    @Column(name = "reserved_quantity", nullable = true)
    private Integer reservedQuantity;
    @Column(name = "sold_quantity", nullable = true)
    private Integer soldQuantity;
    @Column(name = "initial_quantity", nullable = false)
    private Integer initialQuantity;
}