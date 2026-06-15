package com.uchechukwu.store.mappers;

import com.uchechukwu.store.dtos.request.ProductRequest;
import com.uchechukwu.store.dtos.response.ProductDto;
import com.uchechukwu.store.dtos.response.ProductsDto;
import com.uchechukwu.store.entities.Category;
import com.uchechukwu.store.entities.Product;
import com.uchechukwu.store.entities.ProductImage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductMapper {

    public static ProductDto toResponse(Product product) {

        List<String> urls = Collections.emptyList();
        if (product.getImages() != null) {
            urls = product.getImages().stream()
                    .map(ProductImage::getImageUrl)
                    .toList();
        }
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                urls,
                product.getCategory().getId(),
                product.getCategory().getName()
        );
    }


    public static Product createEntity(ProductRequest request, Category category) {

        return Product.builder()
                .name(request.name())
                .description(request.description())
                .price(request.price())
                .category(category)
                .images(new ArrayList<>())
                .build();
    }

    public static ProductsDto toManyResponse(Product product) {
        var image = product.getThumbnailUrl() != null ? product.getThumbnailUrl() : "No Image";

        return new ProductsDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                image,
                product.getCategory().getId(),
                product.getCategory().getName()
        );
    }

}