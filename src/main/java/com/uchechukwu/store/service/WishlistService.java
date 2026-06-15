package com.uchechukwu.store.service;


import com.uchechukwu.store.core.GetCurrentUser;
import com.uchechukwu.store.customCache.CustomCacheEvict;
import com.uchechukwu.store.customCache.CustomCacheable;
import com.uchechukwu.store.dtos.response.WishlistPageResponse;
import com.uchechukwu.store.dtos.response.WishlistResponse;
import com.uchechukwu.store.entities.Wishlist;
import com.uchechukwu.store.entities.WishlistId;
import com.uchechukwu.store.exceptions.BadRequestException;
import com.uchechukwu.store.exceptions.ResourceNotFoundException;
import com.uchechukwu.store.mappers.WishlistMapper;
import com.uchechukwu.store.repositories.WishlistRepository;
import com.uchechukwu.store.responses.ApiResponse;
import com.uchechukwu.store.responses.ApiResponseBuilder;
import com.uchechukwu.store.validators.ValidatedSortedData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class WishlistService {
    private final GetCurrentUser getCurrentUser;
    private final ProductService productService;
    private final WishlistRepository wishlistRepository;
    private final ValidatedSortedData validatedSortedData;
    private final WishlistMapper wishlistMapper;

    @Transactional

    @CustomCacheEvict(cacheNames = {
            "user-wishlist", "single-profile", "single-wishlist-check"})
    public WishlistResponse addToWishlist(UUID productId) {
        var currentUser = getCurrentUser.getCurrentUser();
        var product = productService.getProductId(productId);
        var compositeId = new WishlistId(product.getId(), currentUser.getId());

        wishlistRepository.findById(compositeId).ifPresent(existing -> {
            throw new BadRequestException("Product already in wishlist");
        });
        var wishlist = Wishlist.builder()
                .product(product)
                .user(currentUser)
                .id(new WishlistId(product.getId(), currentUser.getId()))
                .build();

        var result = wishlistRepository.save(wishlist);
        return wishlistMapper.response(result);
    }

    @Transactional
    @CustomCacheEvict(cacheNames = {
            "user-wishlist", "single-profile", "single-wishlist-check"})
    public ResponseEntity<ApiResponse<Void>> removeFromWishlist(UUID productId) {
        var user = getCurrentUser.getCurrentUser();
        WishlistId compositeId = new WishlistId(productId, user.getId());

        Wishlist wishlist = wishlistRepository.findById(compositeId)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found in your wishlist"));

        wishlistRepository.delete(wishlist);
        return ApiResponseBuilder.deletedResponse("Item removed from your wishlist successfully");
    }

    @Transactional(readOnly = true)
    @CustomCacheable(value = "single-wishlist-check", key = "'user-' + @getCurrentUser.getCurrentUser().id + '-prod-' + #productId")
    public WishlistResponse getSingleWishlistItem(UUID productId) {
        var user = getCurrentUser.getCurrentUser();
        var compositeId = new WishlistId(productId, user.getId());

        var wishlist = wishlistRepository.findById(compositeId)
                .orElseThrow(() -> new ResourceNotFoundException("This product is not on your wishlist"));

        return wishlistMapper.response(wishlist);
    }

    @Transactional(readOnly = true)
    @CustomCacheable(
            value = "user-wishlist",
            key = "@getCurrentUser.getCurrentUser().id + '-' + #sort + '-' + #sortingValue1 + '-' + #page + '-' + #size"
    )
    public WishlistPageResponse getAllWishlistItemsForUser(String sort, String sortingValue1, int page, int size) {
        var user = getCurrentUser.getCurrentUser();
        var pageable = validatedSortedData.getValidatedPageableData(sort, sortingValue1, page, size);

        var result =
                wishlistRepository.findByUserId(
                                user.getId(),
                                pageable)
                        .map(wishlistMapper::manyResponse);

        return new WishlistPageResponse(
                result.getContent(),
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages(),
                result.isFirst(),
                result.isLast()
        );
    }
}

