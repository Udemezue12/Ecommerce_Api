package com.uchechukwu.store.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.BINARY)
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    private String name;
    @Column(name = "thumb_nail")
    private String thumbnailUrl;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private BigDecimal price;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;


    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
//    @BatchSize(size = 50)
    private List<ProductImage> images = new ArrayList<>();
}