package com.uchechukwu.store.controllers;

import com.uchechukwu.store.Idempotency.Idempotent;
import com.uchechukwu.store.dtos.request.DeliveryRequest;
import com.uchechukwu.store.dtos.request.PageResponse;
import com.uchechukwu.store.dtos.request.UpdateDeliveryStatusRequest;
import com.uchechukwu.store.dtos.response.DeliveryDetailResponse;
import com.uchechukwu.store.dtos.response.DeliveryManyResponse;
import com.uchechukwu.store.dtos.response.DeliveryResponse;
import com.uchechukwu.store.responses.ApiResponse;
import com.uchechukwu.store.responses.ApiResponseBuilder;
import com.uchechukwu.store.service.DeliveryService;
import com.uchechukwu.store.utilities.rateLimiter.RateLimit;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/api/v1")
@Tag(name = "Delivery", description = "Endpoints for order delivery and management")
@RequiredArgsConstructor
public class DeliveryController {
    private final DeliveryService deliveryService;

    @PostMapping("/delivery/{orderId}/rider/assign")
    @Idempotent(ttl = 120)
    @RateLimit
    public ResponseEntity<ApiResponse<DeliveryResponse>> assign(@PathVariable UUID orderId, @Valid @RequestBody DeliveryRequest request) {
        return deliveryService.assignRiderToDelivery(orderId, request);
    }

    @PatchMapping("/delivery/{orderId}/status/update")
    @RateLimit
    public ResponseEntity<ApiResponse<Void>> assign(@PathVariable UUID orderId, @Valid @RequestBody UpdateDeliveryStatusRequest request) {
        return deliveryService.updateDeliveryStatus(orderId, request);
    }

    @GetMapping("/delivery/{deliveryId}")
    @RateLimit

    public ResponseEntity<ApiResponse<DeliveryDetailResponse>> getDetailedDelivery(@PathVariable UUID deliveryId) {
        var result = deliveryService.getDetailedDelivery(deliveryId);
        return ApiResponseBuilder.success("Fetched Successfully", result);
    }

    @GetMapping("/delivery/{deliveryId}/user")
    @RateLimit

    public ResponseEntity<ApiResponse<DeliveryDetailResponse>> getUserDetailedDelivery(@PathVariable UUID deliveryId) {
        var result = deliveryService.getCustomerDeliveryById(deliveryId);
        return ApiResponseBuilder.success("Fetched Successfully", result);
    }

    @GetMapping("/delivery/user/all")
    @RateLimit

    public ResponseEntity<ApiResponse<Page<DeliveryManyResponse>>> getAllUserDeliveries(@RequestParam(required = false, defaultValue = "", name = "sortingValue1") String sortingValue1, @RequestParam(required = false, defaultValue = "", name = "sort") String sort,
                                                                                        @RequestParam(required = false, defaultValue = "0", name = "page") int page,
                                                                                        @RequestParam(required = false, defaultValue = "10", name = "size") int size) {
        var delivery = deliveryService.getCustomerDeliveries(sort, sortingValue1, page, size);
        return ApiResponseBuilder.success("Fetched Successfully", delivery);
    }

    @GetMapping("/delivery/all")
    @RateLimit

    public ResponseEntity<ApiResponse<PageResponse>> getAllDeliveries(@RequestParam(required = false, defaultValue = "", name = "sortingValue1") String sortingValue1, @RequestParam(required = false, defaultValue = "", name = "sort") String sort,
                                                                                            @RequestParam(required = false, defaultValue = "0", name = "page") int page,
                                                                                            @RequestParam(required = false, defaultValue = "10", name = "size") int size) {
        var delivery = deliveryService.getAllDetailedDeliveries(sort, sortingValue1, page, size);
        return ApiResponseBuilder.success("Fetched Successfully", delivery);
    }


}
