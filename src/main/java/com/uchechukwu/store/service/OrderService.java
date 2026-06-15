package com.uchechukwu.store.service;

import com.uchechukwu.store.core.GetCurrentUser;
import com.uchechukwu.store.dtos.response.CheckoutResponse;
import com.uchechukwu.store.dtos.response.OrderDto;
import com.uchechukwu.store.entities.CartItem;
import com.uchechukwu.store.entities.Order;
import com.uchechukwu.store.entities.OrderItem;
import com.uchechukwu.store.enums.OrderStatus;
import com.uchechukwu.store.exceptions.BadRequestException;
import com.uchechukwu.store.exceptions.ResourceNotFoundException;
import com.uchechukwu.store.mappers.OrderMapper;
import com.uchechukwu.store.repositories.AddressRepository;
import com.uchechukwu.store.repositories.CartRepository;
import com.uchechukwu.store.repositories.OrderRepository;
import com.uchechukwu.store.validators.ValidatedSortedData;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final GetCurrentUser getCurrentUser;
    private final OrderRepository orderRepository;
    private final ValidatedSortedData validSortedData;
    private final CartRepository cartRepo;
    private final AddressRepository addressRepo;
    private final OrderRepository orderRepo;

    private final InventoryService inventoryService;


    @Transactional
    public ResponseEntity<?> checkoutOrder(UUID cartId) {


        var cart = cartRepo.findById(cartId)
                .orElseThrow(() -> new BadRequestException("Cart not found"));

        if (cart.getItems().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Cart is empty"));
        }


        var user = getCurrentUser.getCurrentUser();
        addressRepo.findByUserId(user.getId()).orElseThrow(() -> new RuntimeException("Add an address before checkout"));

        cart.setUser(user);


        var order = new Order();
        order.setTotalPrice(cart.getTotalPrice());
        order.setStatus(OrderStatus.PENDING);
        order.setUser(user);


        cart.getItems()
                .stream()
                .sorted(compareByProductId())
                .forEach(item -> {
                    var inventory = inventoryService.validateInventoryStock(item);
                    inventoryService.validateSellerCannotBuyOwnProduct(user.getId(), inventory);

                    var orderItem = new OrderItem();
                    orderItem.setProduct(inventory.getProduct());
                    orderItem.setQuantity(item.getQuantity());
                    orderItem.setTotalPrice(item.getTotalPrice());
                    orderItem.setUnitPrice(item.getProduct().getPrice());


                    orderItem.setOrder(order);

                    order.addItem(orderItem);
                });


        cartRepo.saveAndFlush(cart);


        orderRepo.save(order);

        var newOrderId = order.getId();
        return ResponseEntity.ok(new CheckoutResponse(newOrderId));
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "user-orders-list", key = "@getCurrentUser.getCurrentUser().id + '-' + #sort + '-' + #sortingValue1 + '-' + #page + '-' + #size",
            condition = "@getCurrentUser.getCurrentUser().id != null")
    public List<OrderDto> fetchAllUserOrders(String sort, String sortingValue1, int page, int size) {
        var userId = getCurrentUser.getCurrentUserId();
        var pageable = validSortedData.getValidatedPageableData(sort, sortingValue1, page, size);

        return orderRepository
                .findByUserId(userId, pageable)
                .stream()
                .map(OrderMapper::orderResponse)
                .toList();

    }

    @Cacheable(value = "single-user-order", key = "@getCurrentUser.getCurrentUser().id + '-' + #orderId")
    @Transactional(readOnly = true)
    public OrderDto fetchUserOrder(UUID orderId) {
        var userId = getCurrentUser.getCurrentUserId();

        var order = orderRepository
                .findByIdAndUserId(
                        orderId,
                        userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Order not found"));
        return OrderMapper.orderResponse(order);

    }

    @Cacheable(value = "admin-all-orders-list", key = "#sort + '-' + #sortingValue1 + '-' + #page + '-' + #size")
    @Transactional(readOnly = true)
    public List<OrderDto> fetchAllOrders(String sort, String sortingValue1, int page, int size) {
        var pageable = validSortedData.getValidatedPageableData(sort, sortingValue1, page, size);
        return orderRepository.findAll(pageable).getContent()
                .stream()
                .map(OrderMapper::orderResponse)
                .toList();

    }

    @Transactional(readOnly = true)
    @Cacheable(value = "admin-single-order", key = "#orderId")
    public OrderDto fetchOrder(UUID orderId) {

        var order = getOrderId(orderId);

        return OrderMapper.orderResponse(order);
    }

    @Transactional(propagation = Propagation.REQUIRED)

    @Caching(evict = {
            @CacheEvict(value = "admin-single-order", key = "#orderId"),
            @CacheEvict(value = "single-user-order", allEntries = true), // Wiped because we don't pass 'userId' directly to this method signature
            @CacheEvict(value = "user-orders-list", allEntries = true),
            @CacheEvict(value = "admin-all-orders-list", allEntries = true)
    })
    public void updateOrderStatus(UUID orderId, OrderStatus status) {

        var order = getOrderId(orderId);

        order.setStatus(status);
        inventoryService.processSuccessfulOrder(order);
        orderRepository.save(order);
    }

    @Transactional(readOnly = true)
    public Order getOrderId(UUID orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    @Transactional
    public void cancelOrders() {

        var updated = orderRepository.cancelExpiredOrders(
                OrderStatus.PENDING,
                OrderStatus.CANCELED,
                LocalDateTime.now().minusHours(24)
        );

        System.out.println(updated + " orders cancelled");
    }

    private static Comparator<CartItem> compareByProductId() {
        return Comparator.comparing(
                item -> item.getProduct().getId());
    }

}
