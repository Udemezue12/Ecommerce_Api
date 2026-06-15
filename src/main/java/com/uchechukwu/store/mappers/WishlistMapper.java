package com.uchechukwu.store.mappers;

import com.uchechukwu.store.dtos.response.WishlistResponse;
import com.uchechukwu.store.entities.Wishlist;
import com.uchechukwu.store.exceptions.BadRequestException;
import com.uchechukwu.store.repositories.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class WishlistMapper {
    private final InventoryRepository inventoryRepository;

    public WishlistResponse manyResponse(Wishlist wishlist) {
        if (wishlist == null) {
            throw new BadRequestException("Wishlist is empty");
        }
        var product = wishlist.getProduct();
        var inventory = inventoryRepository.findByProductId(product.getId()).orElseThrow(() -> new BadRequestException("This Product does not exist in the inventory"));


        boolean isAvailable = inventory.getAvailableQuantity() != null
                && inventory.getSoldQuantity() != null
                && inventory.getAvailableQuantity() > inventory.getSoldQuantity();
        var image = product.getThumbnailUrl() != null ? product.getThumbnailUrl() : "No Image";
        return new WishlistResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                image,
                isAvailable
        );
    }

    public WishlistResponse response(Wishlist wishlist) {
        if (wishlist == null) {
            throw new BadRequestException("Wishlist is empty");
        }
        var product = wishlist.getProduct();
        var inventory = inventoryRepository.findByProductId(product.getId()).orElseThrow(() -> new BadRequestException("This Product does not exist in the inventory"));
        String imageUrl = null;

        if (product.getImages() != null && !product.getImages().isEmpty()) {
            imageUrl = product.getImages().getFirst().getImageUrl();
        }

        boolean isAvailable = inventory.getAvailableQuantity() != null
                && inventory.getSoldQuantity() != null
                && inventory.getAvailableQuantity() > inventory.getSoldQuantity();

        return new WishlistResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                imageUrl,
                isAvailable
        );
    }
}
