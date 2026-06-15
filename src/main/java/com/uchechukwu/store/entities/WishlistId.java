package com.uchechukwu.store.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class WishlistId implements Serializable {

    @JdbcTypeCode(SqlTypes.BINARY)
    @Column(name = "product_id")
    private UUID productId;

    @JdbcTypeCode(SqlTypes.BINARY)
    @Column(name = "user_id")
    private UUID userId;
}