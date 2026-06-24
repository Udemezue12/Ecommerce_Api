package com.uchechukwu.store.controllers;

import com.uchechukwu.store.Idempotency.Idempotent;
import com.uchechukwu.store.api_builder_response.ApiResponse;
import com.uchechukwu.store.api_builder_response.ApiResponseBuilder;
import com.uchechukwu.store.dtos.response.OrderDto;
import com.uchechukwu.store.service.OrderService;
import com.uchechukwu.store.utilities.rateLimiter.RateLimit;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/v1")
@Tag(name = "Order", description = "Endpoints for order creation, checkout and management")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;


    @PostMapping("/{cartId}/checkout/order")
    @RateLimit(times = 4, seconds = 8)
    @Idempotent(ttl = 120)
    public ResponseEntity<?> checkout(
            @PathVariable UUID cartId) {

        return orderService.checkoutOrder(cartId);

    }


    @GetMapping("/orders")
    public ResponseEntity<ApiResponse<List<OrderDto>>> getOrders(
            @RequestParam(required = false, defaultValue = "", name = "sort") String sort,
            @RequestParam(required = false, defaultValue = "0", name = "page") int page,
            @RequestParam(required = false, defaultValue = "10", name = "size") int size
    ) {
        var orders = orderService.fetchAllUserOrders(sort, "createdAt", page, size);
        return ApiResponseBuilder.success(
                "Orders fetched successfully",
                orders);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<ApiResponse<OrderDto>> fetchUserOrder(
            @PathVariable UUID orderId
    ) {
        var order = orderService.fetchUserOrder(orderId);
        return ApiResponseBuilder.success(
                "Orders fetched successfully",
                order);

    }

    @GetMapping("/admin/orders")
    public ResponseEntity<ApiResponse<List<OrderDto>>> getAllOrders(
            @RequestParam(required = false, defaultValue = "", name = "sort") String sort,
            @RequestParam(required = false, defaultValue = "0", name = "page") int page,
            @RequestParam(required = false, defaultValue = "10", name = "size") int size
    ) {
        var orders = orderService.fetchAllOrders(sort, "createdAt", page, size);
        return ApiResponseBuilder.success(
                "Orders fetched successfully",
                orders);
    }
}
