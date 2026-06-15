package com.uchechukwu.store.service;

import com.uchechukwu.store.entities.*;
import com.uchechukwu.store.exceptions.BadRequestException;
import com.uchechukwu.store.exceptions.ResourceNotFoundException;
import com.uchechukwu.store.repositories.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepo;

    @Transactional
    public void createInventory(Product product, User user, Integer initialQuantity) {
        var inventory = Inventory.builder().product(product)
                .user(user)
                .availableQuantity(initialQuantity)
                .initialQuantity(initialQuantity)
                .build();
        inventoryRepo.save(inventory);
    }

    @Transactional
    public Inventory validateInventoryStock(CartItem item) {
        var inventory = getInventoryProduct(item.getProduct().getId());

        int available = inventory.getAvailableQuantity() != null ? inventory.getAvailableQuantity() : 0;
        int reserved = inventory.getReservedQuantity() != null ? inventory.getReservedQuantity() : 0;

        var sellableStock = available - reserved;

        if (sellableStock < item.getQuantity()) {
            throw new BadRequestException(
                    inventory.getProduct().getName() + " is out of stock");
        }

        inventory.setReservedQuantity(reserved + item.getQuantity());

        return inventory;
    }

    @Transactional
    public void reserveStock(UUID productId, int quantity) {

        var inventory = getInventoryProduct(productId);

        if (quantity <= 0) {
            throw new BadRequestException("Quantity must be greater than 0");
        }

        var sellableStock = inventory.getAvailableQuantity()
                - inventory.getReservedQuantity();

        if (sellableStock < quantity) {
            throw new BadRequestException(
                    inventory.getProduct().getName()
                            + " only has "
                            + sellableStock
                            + " items left");
        }

        inventory.setReservedQuantity(
                inventory.getReservedQuantity() + quantity);

        inventoryRepo.save(inventory);
    }

    @Transactional
    public void releaseReservedStock(UUID productId, int quantity) {

        var inventory = getInventoryProduct(productId);
        if (quantity <= 0) {
            throw new BadRequestException("Quantity must be greater than 0");
        }

        inventory.setReservedQuantity(
                Math.max(
                        0,
                        inventory.getReservedQuantity() - quantity));

        inventoryRepo.save(inventory);
    }

    @Transactional
    public void confirmSale(UUID productId, int quantity) {
        var inventory = getInventoryProduct(productId);

        if (quantity <= 0) {
            throw new BadRequestException("Quantity must be greater than 0");
        }


        int reserved = inventory.getReservedQuantity() != null ? inventory.getReservedQuantity() : 0;
        int available = inventory.getAvailableQuantity() != null ? inventory.getAvailableQuantity() : 0;
        int sold = inventory.getSoldQuantity() != null ? inventory.getSoldQuantity() : 0;


        inventory.setReservedQuantity(reserved - quantity);
        inventory.setAvailableQuantity(available - quantity);
        inventory.setSoldQuantity(sold + quantity);

        inventoryRepo.save(inventory);
    }


    @Transactional
    public void processSuccessfulOrder(Order order) {
        for (OrderItem item : order.getItems()) {
            confirmSale(
                    item.getProduct().getId(),
                    item.getQuantity());

        }

    }

    @Transactional
    public void updateProductQuantity(Integer quantity, UUID productId) {
        var inventory = getInventoryProduct(productId);
        var initialQuantity = inventory.getInitialQuantity() + quantity;
        var availableQuantity = inventory.getAvailableQuantity() + quantity;
        inventory.setAvailableQuantity(availableQuantity);
        inventory.setInitialQuantity(initialQuantity);
        inventoryRepo.save(inventory);
    }

    @Transactional(readOnly = true)
    public Inventory getInventoryProduct(UUID productId) {
        return inventoryRepo.findByProductIdForUpdate(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not Found"));
    }

    public void validateSellerCannotBuyOwnProduct(
            UUID userId,
            Inventory inventory) {

        var sellerId =
                inventory.getUser() != null
                        ? inventory.getUser().getId()
                        : null;

        if (Objects.equals(userId, sellerId)) {

            throw new BadRequestException(
                    "You cannot add your own products to cart"
            );
        }
    }
}
