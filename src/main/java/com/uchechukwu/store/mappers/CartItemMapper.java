package com.uchechukwu.store.mappers;

import com.uchechukwu.store.dtos.request.CartItemRequest;
import com.uchechukwu.store.dtos.response.CartItemResponse;
import com.uchechukwu.store.entities.Cart;
import com.uchechukwu.store.entities.CartItem;
import com.uchechukwu.store.entities.Product;

import java.math.BigDecimal;

public class CartItemMapper {
    public static CartItemResponse getResponse(
            CartItem item,
            BigDecimal totalAmount) {
        return new CartItemResponse(
                item.getId(),
                CartMapper.getResponse(item.getCart()),
                item.getQuantity(),
                ProductMapper.toResponse(item.getProduct()),
                totalAmount);
    }

    public static CartItemResponse getManyItemsResponse(
            CartItem item) {

        BigDecimal totalAmount = item.getProduct()
                .getPrice()
                .multiply(
                        BigDecimal.valueOf(
                                item.getQuantity()));

        return new CartItemResponse(
                item.getId(),
                CartMapper.getResponse(item.getCart()),
                item.getQuantity(),
                ProductMapper.toResponse(item.getProduct()),
                totalAmount);
    }

    public static CartItem addItemToCart(CartItemRequest request, Product product, Cart cart) {
        return CartItem.builder()
                .cart(cart)
                .quantity(request.quantity())
                .product(product)
                .build();
    }

    public static CartItem addItemsToCart(
            Integer quantity,
            Product product,
            Cart cart) {

        return CartItem.builder()
                .quantity(quantity)
                .product(product)
                .cart(cart)
                .build();
    }
}
