package com.uchechukwu.store.controllers;


import com.uchechukwu.store.api_builder_response.ApiResponse;
import com.uchechukwu.store.api_builder_response.ApiResponseBuilder;
import com.uchechukwu.store.dtos.request.WishlistRequest;
import com.uchechukwu.store.dtos.response.WishlistPageResponse;
import com.uchechukwu.store.dtos.response.WishlistResponse;
import com.uchechukwu.store.service.WishlistService;
import com.uchechukwu.store.utilities.rateLimiter.RateLimit;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Wishlist")
public class WishlistController {
    private final WishlistService wishlistService;

    @PostMapping("/wishlist/add")

    @RateLimit
    public ResponseEntity<ApiResponse<WishlistResponse>> addProductToWishlist(@Valid @RequestBody WishlistRequest request) {
        var wishlist = wishlistService.addToWishlist(request.productId());
        return ApiResponseBuilder.success("Product successfully added to wishlist", wishlist);

    }

    @DeleteMapping("/wishlist/{productId}/delete")
    @RateLimit
    public ResponseEntity<ApiResponse<Void>> deleteProductFromWishlist(@PathVariable UUID productId) {
        return wishlistService.removeFromWishlist(productId);
    }

    @GetMapping("/wishlist/get")
    @RateLimit
    public ResponseEntity<ApiResponse<WishlistResponse>> getSingleWishlistItem(@Valid @RequestBody WishlistRequest request) {
        var wishlist = wishlistService.getSingleWishlistItem(request.productId());
        return ApiResponseBuilder.success("Wishlist successfully fetched", wishlist);
    }

    @GetMapping("/wishlist/all")
    @RateLimit
    public ResponseEntity<ApiResponse<WishlistPageResponse>> getAllWishlistItems(@RequestParam(required = false, defaultValue = "", name = "sort") String sort,
                                                                                 @RequestParam(required = false, defaultValue = "0", name = "page") int page,
                                                                                 @RequestParam(required = false, defaultValue = "10", name = "size") int size) {
        var wishlist = wishlistService.getAllWishlistItemsForUser(sort, "product", page, size);
        return ApiResponseBuilder.success("Wishlist successfully fetched", wishlist);
    }
}
