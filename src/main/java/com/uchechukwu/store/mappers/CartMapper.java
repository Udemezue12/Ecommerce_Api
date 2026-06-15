package com.uchechukwu.store.mappers;

import com.uchechukwu.store.dtos.response.CartProductDto;
import com.uchechukwu.store.dtos.response.CartResponse;
import com.uchechukwu.store.dtos.response.GetItemCart;
import com.uchechukwu.store.dtos.response.ItemCartResponse;
import com.uchechukwu.store.entities.Cart;
import com.uchechukwu.store.entities.CartItem;

public class CartMapper {

    public static CartResponse getResponse(Cart cart) {

        return new CartResponse(
                cart.getId(),
                cart.getDateCreated());
    }

    public static CartProductDto getDto(CartItem cartItem) {
        return new CartProductDto(cartItem.getProduct().getId(), cartItem.getProduct().getName(),
                cartItem.getProduct().getPrice());
    }
    // public static CartItemDto toDto(CartItem cartItem){
    // return new CartItemDto( CartProductMapper.toDto(
    // cartItem.getProduct()
    // ));
    // }

    public static GetItemCart toCartItemResponse(CartItem item) {


        return new GetItemCart(
                item.getProduct().getId(),
                item.getProduct().getName(),
                item.getProduct().getPrice(),
                item.getQuantity(),
                item.getTotalPrice());
    }

    public static ItemCartResponse toCartResponse(Cart cart) {

        var items = cart.getItems()
                .stream()
                .map(CartMapper::toCartItemResponse)
                .toList();

        return new ItemCartResponse(
                cart.getId(),
                cart.getDateCreated(),
                cart.getDateUpdated(),
                items,
                cart.getTotalItems(),
                cart.getTotalPrice());
    }


}
