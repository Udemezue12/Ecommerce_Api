package com.uchechukwu.store.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "carts")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.BINARY)
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "date_created", nullable = false, updatable = false)
    private LocalDateTime dateCreated;

    @Column(name = "date_updated", nullable = false)
    private LocalDateTime dateUpdated;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<CartItem> items = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @PrePersist
    public void prePersist() {

        this.dateCreated = LocalDateTime.now();
        this.dateUpdated = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.dateUpdated = LocalDateTime.now();
    }

    @Transient
    public Integer getTotalItems() {
        return items.stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
    }

    @Transient
    public BigDecimal getTotalPrice() {
        return items.stream()
                .map(item -> item.getProduct()
                        .getPrice()
                        .multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
