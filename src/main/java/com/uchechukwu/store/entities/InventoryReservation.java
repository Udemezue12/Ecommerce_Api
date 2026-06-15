package com.uchechukwu.store.entities;

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
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "inventory_reservations")
public class InventoryReservation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.BINARY)
    @Column(name = "id")
    private UUID id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_id")
    private Inventory inventory;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_item_id")
    private CartItem cartItem;


    @Column(name = "quantity", nullable = false)
    private Integer quantity;


    @Column(name = "expires_at", nullable = false)
    private Instant expiresAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private Instant createdAt;


}