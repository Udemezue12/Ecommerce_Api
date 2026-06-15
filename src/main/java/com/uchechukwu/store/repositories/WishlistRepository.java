package com.uchechukwu.store.repositories;

import com.uchechukwu.store.entities.Wishlist;
import com.uchechukwu.store.entities.WishlistId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, WishlistId> {
    Optional<Wishlist> findById(WishlistId id);


    @EntityGraph(attributePaths = {"product"})
    Page<Wishlist> findByUserId(UUID userId, Pageable pageable);


    boolean existsById(WishlistId id);
}
