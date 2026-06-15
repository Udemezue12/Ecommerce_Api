package com.uchechukwu.store.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.uchechukwu.store.core.GetCurrentUser;
import com.uchechukwu.store.customCache.CustomCacheEvict;
import com.uchechukwu.store.customCache.CustomCacheable;
import com.uchechukwu.store.dtos.request.DeliveryRequest;
import com.uchechukwu.store.dtos.request.PageResponse;
import com.uchechukwu.store.dtos.request.UpdateDeliveryStatusRequest;
import com.uchechukwu.store.dtos.response.DeliveryDetailResponse;
import com.uchechukwu.store.dtos.response.DeliveryManyResponse;
import com.uchechukwu.store.dtos.response.DeliveryResponse;
import com.uchechukwu.store.entities.Delivery;
import com.uchechukwu.store.enums.DeliveryStatus;
import com.uchechukwu.store.enums.PaymentStatus;
import com.uchechukwu.store.exceptions.BadRequestException;
import com.uchechukwu.store.exceptions.ResourceNotFoundException;
import com.uchechukwu.store.mappers.DeliveryMapper;
import com.uchechukwu.store.mappers.PageMapper;
import com.uchechukwu.store.repositories.DeliveryRepository;
import com.uchechukwu.store.responses.ApiResponse;
import com.uchechukwu.store.responses.ApiResponseBuilder;
import com.uchechukwu.store.validators.ValidatedSortedData;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeliveryService {
    private final OrderService orderService;
    private final AddressService addressService;
    private final RiderService riderService;
    private final DeliveryRepository deliveryRepo;
    private final ValidatedSortedData validatedSortedData;
    private final GetCurrentUser getCurrentUser;
    private final CacheManager cacheManager;
    private final ObjectMapper objectMapper;

    @Transactional
    @CustomCacheEvict(cacheNames = {"single-delivery-details", "delivery-listings",})
    public ResponseEntity<ApiResponse<DeliveryResponse>> assignRiderToDelivery(UUID orderId, DeliveryRequest request) {
        var order = orderService.getOrderId(orderId);
        var customer = order.getUser();
        var customerId = order.getUser().getId();


        var successfulTransaction = order.getTransactions()
                .stream()
                .filter(tx -> tx.getStatus() == PaymentStatus.SUCCESS)
                .findFirst()
                .orElseThrow(() -> new BadRequestException(
                        "Cannot initialize processing: No successful transaction linked to this order."
                ));

        if (!Boolean.TRUE.equals(successfulTransaction.getOrderProcessed())) {
            throw new BadRequestException("Order not processed yet");
        }

        var address = addressService.getAddress(customerId);
        Delivery delivery = null;
        if (request.riderId() != null) {
            var rider = riderService.getRider(request.riderId());
            delivery = Delivery.builder()
                    .rider(rider)
                    .customer(customer)
                    .paymentTransaction(successfulTransaction)
                    .order(order)
                    .deliveryAddress(address)
                    .estimatedDelivery(request.estimatedDelivery())
                    .pickupAddress(request.pickUpAddress())
                    .deliveryType(request.deliveryType())
                    .createdAt(Instant.now())
                    .status(DeliveryStatus.ASSIGNED)
                    .notes(request.notes())
                    .build();
        } else if (request.logisticsCompany() != null) {
            delivery = Delivery.builder()
                    .customer(customer)
                    .paymentTransaction(successfulTransaction)
                    .order(order)
                    .logisticsCompany(request.logisticsCompany())
                    .trackingId(request.trackingId())
                    .deliveryAddress(address)
                    .estimatedDelivery(request.estimatedDelivery())
                    .pickupAddress(request.pickUpAddress())
                    .createdAt(Instant.now())
                    .status(DeliveryStatus.ASSIGNED)
                    .notes(request.notes())
                    .deliveryType(request.deliveryType())
                    .build();


        } else {
            throw new BadRequestException("Invalid dispatch request: You must specify either a valid Rider ID or full Logistics Company parameters.");
        }
        var savedDelivery = deliveryRepo.save(delivery);
        var responsePayload = DeliveryMapper.response(savedDelivery);
        var successMessage = (savedDelivery.getRider() != null)
                ? "Delivery assigned successfully to rider: " + savedDelivery.getRider().getFirstName()
                : "Delivery successfully configured for outbound freight via " + savedDelivery.getLogisticsCompany();
        return ApiResponseBuilder.success(successMessage, responsePayload);

    }

    @CustomCacheEvict(cacheNames = {"single-delivery-details", "delivery-listings",})
    @Transactional
    public ResponseEntity<ApiResponse<Void>> updateDeliveryStatus(
            UUID deliveryId,
            UpdateDeliveryStatusRequest request) {

        var delivery = deliveryRepo.findById(deliveryId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Delivery details not found"));

        Instant now = Instant.now();
        DeliveryStatus newStatus = request.deliveryStatus();

        delivery.setStatus(newStatus);

        if (newStatus == DeliveryStatus.PICKED_UP
                && delivery.getPickedUpAt() == null) {
            delivery.setPickedUpAt(now);
        }

        if (newStatus == DeliveryStatus.DELIVERED
                && delivery.getDeliveredAt() == null) {
            delivery.setDeliveredAt(now);
        }
        if (newStatus == DeliveryStatus.FAILED
                && delivery.getDeliveredAt() == null) {
            delivery.setFailedAt(now);
        }
        if (newStatus == DeliveryStatus.RETURNED
                && delivery.getDeliveredAt() == null) {
            delivery.setDeliveredAt(now);
        }
        if (newStatus == DeliveryStatus.CANCELLED
                && delivery.getDeliveredAt() == null) {
            delivery.setCancelledAt(now);
        }


        delivery.setUpdatedAt(now);

        return ApiResponseBuilder.deletedResponse(
                "Delivery updated successfully");
    }

    @Transactional(readOnly = true)
    @CustomCacheable(value = "single-delivery-details", key = "#deliveryId")
    public DeliveryDetailResponse getDetailedDelivery(UUID deliveryId) {
        return deliveryRepo.findDetailedDeliveryById(deliveryId)
                .map(DeliveryMapper::toDetailResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Delivery record with ID " + deliveryId + " does not exist."));
    }


    @Transactional(readOnly = true)
    @CustomCacheable(value = "delivery-listings", key = "'all-' + #sort + '-' + #sortingValue1 + '-' + #page + '-' + #size")
    public PageResponse getAllDetailedDeliveries(String sort, String sortingValue1, int page, int size) {
        var pageable = validatedSortedData.getValidatedPageableData(sort, sortingValue1, page, size);

        var result = deliveryRepo.findAll(pageable)
                .map(DeliveryMapper::toManyResponse);
        return PageMapper.toResponse(result);
    }


    @Transactional(readOnly = true)
    @CustomCacheable(value = "single-delivery-details", key = "'user-' + @getCurrentUser.getCurrentUser().id + '-delivery-' + #deliveryId")
    public DeliveryDetailResponse getCustomerDeliveryById(UUID deliveryId) {

        var user = getCurrentUser.getCurrentUser();

        var rows = deliveryRepo.findDeliveryFlat(deliveryId, user.getId());

        if (rows.isEmpty()) {
            throw new ResourceNotFoundException(
                    "Delivery record not found or no permission"
            );
        }

        return DeliveryMapper.fromFlat(rows);
    }


    @Transactional(readOnly = true)
    @CustomCacheable(
            value = "delivery-listings",
            key = "'user-' + @getCurrentUser.getCurrentUser().id + '-' + #sort + '-' + #sortingValue1 + '-' + #page + '-' + #size"
    )
    public Page<DeliveryManyResponse> getCustomerDeliveries(String sort, String sortingValue1, int page, int size) {
        var user = getCurrentUser.getCurrentUser();
        var pageable = validatedSortedData.getValidatedPageableData(sort, sortingValue1, page, size);

        return deliveryRepo.findDetailedDeliveriesByCustomerId(user.getId(), pageable)
                .map(DeliveryMapper::toManyResponse);
    }


}
