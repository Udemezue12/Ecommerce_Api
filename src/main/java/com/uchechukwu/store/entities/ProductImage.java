package com.uchechukwu.store.entities;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "product_images")
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.BINARY)
    private UUID id;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "image_hash")
    private String imageHash;

    @Column(name = "resource_type")
    private String resourceType;

    @Column(name = "public_id")
    private String publicId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}
