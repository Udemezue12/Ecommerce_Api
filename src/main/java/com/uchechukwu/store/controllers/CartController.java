package com.uchechukwu.store.controllers;


import com.uchechukwu.store.dtos.request.AllCartItemsRequest;
import com.uchechukwu.store.dtos.request.CartItemRequest;
import com.uchechukwu.store.dtos.request.UpdateCartItemRequest;
import com.uchechukwu.store.dtos.response.CartItemResponse;
import com.uchechukwu.store.dtos.response.CartResponse;
import com.uchechukwu.store.dtos.response.ItemCartResponse;
import com.uchechukwu.store.responses.ApiResponse;
import com.uchechukwu.store.service.CartService;
import com.uchechukwu.store.utilities.rateLimiter.RateLimit;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "Cart", description = "Endpoints for cart creation and management")
public class CartController {
    private final CartService cartService;


    @PostMapping("/cart/create")
    @RateLimit(times = 4, seconds = 8)

    public ResponseEntity<ApiResponse<CartResponse>> createCart() {

        return cartService.createCart();
    }

    @GetMapping("/cart/{cartId}")
    @RateLimit(times = 4, seconds = 8)
    public ResponseEntity<ItemCartResponse> getCart(@PathVariable UUID cartId) {

        return ResponseEntity.ok(
                cartService.getCart(cartId));
    }


    @PostMapping("/cart/{cartId}/item/add")
    @RateLimit(times = 4, seconds = 8)
    public ResponseEntity<ApiResponse<CartItemResponse>> addItemToCart(
            @PathVariable UUID cartId,
            @Valid @RequestBody CartItemRequest request) {

        return cartService.addItemToCart(cartId, request);
    }

    @PostMapping("/cart/{cartId}/items")
    @RateLimit(times = 4, seconds = 8)
    @Hidden
    public ResponseEntity<ApiResponse<List<CartItemResponse>>> addItemsToCart(
            @PathVariable UUID cartId,
            @Valid @RequestBody AllCartItemsRequest request) {

        return cartService.addItemsToCart(cartId, request);
    }

    @PatchMapping("/{cartId}/items/{productId}")
    @RateLimit(times = 4, seconds = 8)
    public ResponseEntity<ApiResponse<ItemCartResponse>>
    updateCartItemQuantity(

            @PathVariable UUID cartId,

            @PathVariable UUID productId,

            @Valid
            @RequestBody UpdateCartItemRequest request
    ) {

        return cartService.updateCartItemQuantity(
                cartId,
                productId,
                request
        );
    }

}
