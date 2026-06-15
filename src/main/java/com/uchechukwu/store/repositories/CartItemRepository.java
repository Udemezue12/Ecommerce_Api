package com.uchechukwu.store.repositories;

import com.uchechukwu.store.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, UUID> {

    @Query("""
                select ci from CartItem ci
                where ci.cart.id = :cartId
                and ci.product.id in :productIds
            """)
    List<CartItem> findAllByCartIdAndProductIdIn(
            UUID cartId,
            List<UUID> productIds);

    @Query("""
                SELECT ci
                FROM CartItem ci
                WHERE ci.cart.id = :cartId
                AND ci.product.id = :productId
            """)
    Optional<CartItem> findSingleByCartIdAndProductIdIn(
            UUID cartId,
            UUID productId);

    @Modifying
    @Query("""
                DELETE FROM CartItem ci
                WHERE ci.cart.id = :cartId
            """)
    void clearCartItems(@Param("cartId") UUID cartId);


    List<CartItem> findByCartId(UUID cartId);
}
