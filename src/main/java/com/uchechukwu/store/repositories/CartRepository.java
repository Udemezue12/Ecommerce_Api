package com.uchechukwu.store.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uchechukwu.store.entities.Cart;


@Repository
public interface CartRepository extends JpaRepository<Cart, UUID> {

    @EntityGraph(attributePaths = {
            "items",
            "items.product"
    })
    Cart findWithItemsById(UUID id);
    Optional<Cart> findById (UUID id);
    @EntityGraph(attributePaths = {
            "user"
    })
    Optional<Cart> findByUserId(UUID userId);

}
