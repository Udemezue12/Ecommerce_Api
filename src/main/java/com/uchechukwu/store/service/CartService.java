package com.uchechukwu.store.service;

import com.uchechukwu.store.api_builder_response.ApiResponse;
import com.uchechukwu.store.api_builder_response.ApiResponseBuilder;
import com.uchechukwu.store.core.GetCurrentUser;
import com.uchechukwu.store.dtos.request.AddItemToCartRequest;
import com.uchechukwu.store.dtos.request.AllCartItemsRequest;
import com.uchechukwu.store.dtos.request.CartItemRequest;
import com.uchechukwu.store.dtos.request.UpdateCartItemRequest;
import com.uchechukwu.store.dtos.response.CartItemResponse;
import com.uchechukwu.store.dtos.response.CartResponse;
import com.uchechukwu.store.dtos.response.ItemCartResponse;
import com.uchechukwu.store.entities.Cart;
import com.uchechukwu.store.entities.CartItem;
import com.uchechukwu.store.exceptions.BadRequestException;
import com.uchechukwu.store.exceptions.ForbiddenException;
import com.uchechukwu.store.exceptions.ResourceNotFoundException;
import com.uchechukwu.store.mappers.CartItemMapper;
import com.uchechukwu.store.mappers.CartMapper;
import com.uchechukwu.store.repositories.CartItemRepository;
import com.uchechukwu.store.repositories.CartRepository;
import com.uchechukwu.store.repositories.InventoryRepository;
import com.uchechukwu.store.validators.EntityValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepo;
    private final EntityValidator entityValidator;
    private final ProductService productService;
    private final InventoryRepository inventoryRepository;
    private final InventoryService inventoryService;
    private final CartItemRepository itemRepo;
    private final GetCurrentUser getCurrentUser;


    @Transactional
    public ResponseEntity<ApiResponse<CartItemResponse>> addItemToCart(
            UUID cartId,
            CartItemRequest request) {

        var cart = getCartId(cartId);
        var userId = getCurrentUser.getCurrentUserIdOrNull();

        var product = productService.getProductIdOrBadRequest(
                request.productId());

        var existing = itemRepo.findSingleByCartIdAndProductIdIn(cart.getId(), product.getId());
        var inventory = inventoryRepository
                .findByProductIdForUpdate(product.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Inventory not found"));
        if (userId != null) {
            inventoryService.validateSellerCannotBuyOwnProduct(userId, inventory);

        }
        CartItem savedItem;

        if (existing.isPresent()) {

            var existingItem = existing.get();

            int newQuantity = existingItem.getQuantity()
                    + request.quantity();

            if (inventory.getAvailableQuantity()
                    < request.quantity()) {

                throw new BadRequestException(
                        "Only "
                                + inventory.getAvailableQuantity()
                                + " items available");
            }

            existingItem.setQuantity(newQuantity);

            savedItem = itemRepo.save(existingItem);

        } else {
            if (inventory.getAvailableQuantity()
                    < request.quantity()) {

                throw new BadRequestException(
                        "Only "
                                + inventory.getAvailableQuantity()
                                + " items available");
            }

            var item = CartItemMapper.addItemToCart(
                    request,
                    product,
                    cart);

            savedItem = itemRepo.save(item);
        }

        var totalAmount = calculateTotal(savedItem);

        var response = CartItemMapper.getResponse(
                savedItem,
                totalAmount);

        return ApiResponseBuilder.success(
                "Items added to cart",
                response);
    }

    @Transactional
    public ResponseEntity<ApiResponse<List<CartItemResponse>>> addItemsToCart(
            UUID cartId,
            AllCartItemsRequest request) {

        var cart = getCartId(cartId);

        var productIds = request.items()
                .stream()
                .map(AllCartItemsRequest.Item::productId)
                .toList();

        var inventoryMap = inventoryRepository
                .findAllByProductIdInForUpdate(productIds)
                .stream()
                .collect(Collectors.toMap(
                        inv -> inv.getProduct().getId(),
                        inv -> inv));
        var existingMap = itemRepo.findAllByCartIdAndProductIdIn(cart.getId(), productIds)
                .stream()
                .collect(Collectors.toMap(
                        ci -> ci.getProduct().getId(),
                        ci -> ci));

        var itemsToSave = new ArrayList<CartItem>();
        int finalQuantity;

        for (var itemReq : request.items()) {

            var inventory =
                    inventoryMap.get(itemReq.productId());

            if (inventory == null) {

                throw new BadRequestException(
                        "Inventory not found for "
                                + itemReq.productId());
            }

            var cartItem = existingMap.get(itemReq.productId());


            if (cartItem != null) {

                finalQuantity = cartItem.getQuantity()
                        + itemReq.quantity();

            } else {
                finalQuantity = itemReq.quantity();
                cartItem = CartItemMapper.addItemsToCart(
                        finalQuantity,
                        inventory.getProduct(),
                        cart);
            }
            inventoryService.reserveStock(itemReq.productId(), itemReq.quantity());
            itemsToSave.add(cartItem);
        }
        inventoryRepository.saveAll(
                inventoryMap.values());
        var savedItems = itemRepo.saveAll(itemsToSave);

        var responses = savedItems.stream()
                .map(CartItemMapper::getManyItemsResponse)
                .toList();

        return ApiResponseBuilder.success(
                "Items added",
                responses);
    }

    @Transactional
    public ResponseEntity<ApiResponse<ItemCartResponse>> updateCartItemQuantity(
            UUID cartId,
            UUID productId,
            UpdateCartItemRequest request) {

        var cart = getCartId(cartId);
        var userId = getCurrentUser.getCurrentUserId();

        if (userId != cart.getUser().getId()) {
            throw new ForbiddenException("You cannot update this cart");
        }

        var item = itemRepo
                .findSingleByCartIdAndProductIdIn(
                        cart.getId(),
                        productId)
                .orElseThrow(() -> new BadRequestException(
                        "Item not found in cart"));


        if (request.quantity() == 0) {

            itemRepo.delete(item);

            cart.getItems().remove(item);

            return ApiResponseBuilder.success(
                    "Item removed from cart",
                    CartMapper.toCartResponse(cart));
        }
        var inventory = inventoryRepository
                .findByProductId(item.getProduct().getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Inventory not found"));
        if (inventory.getAvailableQuantity() < request.quantity()) {

            throw new BadRequestException(
                    "Only "
                            + inventory.getAvailableQuantity()
                            + " items available");
        }
        item.setQuantity(request.quantity());

        return ApiResponseBuilder.success(
                "Cart updated successfully",
                CartMapper.toCartResponse(cart));
    }

    public static BigDecimal calculateTotal(CartItem item) {
        return item.getProduct()
                .getPrice()
                .multiply(
                        BigDecimal.valueOf(
                                item.getQuantity()));
    }

    public Cart getCartId(UUID Id) {
        return entityValidator.findByIdOrThrow(cartRepo, Id, "Cart");
    }

    @Transactional
    public void addToCart(UUID cartId, AddItemToCartRequest request) {
        var cart = getCartId(cartId);
        var product = productService.getProductIdOrReturnNull(request.productId());
        var cartItem = cart.getItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .findFirst()
                .orElse(null);
        if (cartItem != null) {
            cartItem.setQuantity(cartItem.getQuantity() + 1);
        } else {
            cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(1);
            cartItem.setCart(cart);
            cart.getItems().add(cartItem);
        }
        cartRepo.save(cart);
        ResponseEntity.ok(null);

    }

    @Transactional
    public ResponseEntity<ApiResponse<CartResponse>> createCart() {
        var cart = new Cart();
        var createdCart = cartRepo.save(cart);
        var cartDto = CartMapper.getResponse(createdCart);
        return ApiResponseBuilder.success("Cart Created Successfully", cartDto);
    }

    @Transactional
    public ResponseEntity<ApiResponse<Void>> deleteCart(UUID cartId) {
        var cart = getCartId(cartId);
        cartRepo.delete(cart);
        return ApiResponseBuilder.success("Deleted Successfully", null);
    }

    @Transactional(readOnly = true)
    public ItemCartResponse getCart(UUID cartId) {

        Cart cart = cartRepo.findWithItemsById(cartId);

        if (cart == null) {
            throw new RuntimeException("Cart not found");
        }

        return CartMapper.toCartResponse(cart);
    }

    @Transactional
    public void clearCartByUserId(UUID userId) {

        var cart = cartRepo.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Active cart not found for user ID: " + userId));

        var items = itemRepo.findByCartId(cart.getId());

        // Alternative optimized approach inventory service:
        // Map<UUID, Integer> stockToRelease = items.stream()
        // .collect(Collectors.toMap(item -> item.getProduct().getId(),
        // CartItem::getQuantity));

        // inventoryService.releaseReservedStockBatch(stockToRelease);
        for (var item : items) {
            inventoryService.releaseReservedStock(
                    item.getProduct().getId(),
                    item.getQuantity());
        }

        itemRepo.clearCartItems(cart.getId());
    }

}