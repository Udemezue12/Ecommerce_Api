package com.uchechukwu.store.mappers;


import com.uchechukwu.store.entities.Product;
import com.uchechukwu.store.entities.ProductImage;

public class ProductImageMapper {
    public static ProductImage createImage(String imageUrl, String resourceType, String publicId, String imageHash, Product product) {
        return ProductImage.builder()
                .product(product)
                .imageUrl(imageUrl)
                .resourceType(resourceType)
                .publicId(publicId)
                .imageHash(imageHash)
                .build();
    }
}
