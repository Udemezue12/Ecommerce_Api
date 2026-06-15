package com.uchechukwu.store.entities;

import com.uchechukwu.store.enums.InventoryTransactionType;
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
@Table(name = "inventory_transactions")
public class InventoryTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.BINARY)
    @Column(name = "id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "inventory_id",
            nullable = false
    )
    private Inventory inventory;

    @Enumerated(EnumType.STRING)
    private InventoryTransactionType transactionType;

    private Integer quantity;

    private String referenceId;

    private String notes;
}