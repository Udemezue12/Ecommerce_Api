package com.uchechukwu.store.repositories;

import com.uchechukwu.store.entities.Product;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    @EntityGraph(attributePaths = {"category"})
    Page<Product> findByCategoryId(UUID categoryId, Pageable pageable);

    @EntityGraph(attributePaths = {"category"})
    Optional<Product> findByIdAndCategoryId(
            UUID productId,
            UUID categoryId);

    @EntityGraph(attributePaths = {"category"})
    Page<Product> findAll(Pageable pageable);


    Optional<Product> findById(UUID productId);


    Optional<Product> findByDescriptionIgnoreCase(String description);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({
            @QueryHint(
                    name = "jakarta.persistence.lock.timeout",
                    value = "5000"
            )
    })
    @Query("""
                SELECT p
                FROM Product p
                WHERE p.id = :productId
            """)
    Optional<Product> findByIdForUpdate(
            @Param("productId") UUID productId
    );

}