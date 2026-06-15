package com.uchechukwu.store.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
        name = "wishlist",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"product_id", "user_id"}
                )
        }
)
public class Wishlist {

    @EmbeddedId
    @Builder.Default
    private WishlistId id = new WishlistId();

    @MapsId("productId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}