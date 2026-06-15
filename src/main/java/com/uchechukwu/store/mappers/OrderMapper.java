package com.uchechukwu.store.mappers;

import com.uchechukwu.store.dtos.response.OrderDto;
import com.uchechukwu.store.dtos.response.OrderItemDto;
import com.uchechukwu.store.entities.Order;
import com.uchechukwu.store.entities.OrderItem;

public class OrderMapper {
    public static OrderItemDto orderItemResponse(OrderItem item) {
        return new OrderItemDto(
                item.getId(),
                item.getQuantity(),
                item.getUnitPrice(),
                item.getTotalPrice(),
                item.getOrder().getId(),
                item.getProduct().getId(),
                item.getProduct().getName()

        );
    }

    public static OrderDto orderResponse(Order order) {
        var items = order.getItems()
                .stream()
                .map(OrderMapper::orderItemResponse)
                .toList();
        return new OrderDto(
                order.getId(),
                order.getStatus().name(),
                order.getTotalPrice(),
                order.getCreatedAt(),
                order.getUpdatedAt(),
                items);

    }
}
