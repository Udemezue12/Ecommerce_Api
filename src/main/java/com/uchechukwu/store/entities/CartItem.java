package com.uchechukwu.store.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "cart_items")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.BINARY)
    @Column(name = "id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
    private Cart cart;

    @Transient
    public BigDecimal getTotalPrice() {

        if (product == null || product.getPrice() == null) {
            return BigDecimal.ZERO;
        }

        return product.getPrice()
                .multiply(BigDecimal.valueOf(quantity));
    }
}
