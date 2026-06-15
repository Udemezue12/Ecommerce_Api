package com.uchechukwu.store.repositories;

import com.uchechukwu.store.entities.Inventory;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, UUID> {


    @EntityGraph(attributePaths = {"product"})
    Page<Inventory> findAll(Pageable pageable);


    Optional<Inventory> findById(UUID inventoryId);

    @EntityGraph(attributePaths = {"product"})
    Optional<Inventory> findByProductId(UUID productId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({
            @QueryHint(
                    name = "jakarta.persistence.lock.timeout",
                    value = "5000"
            )
    })
    @EntityGraph(attributePaths = {"product"})
    @Query("""
                SELECT i
                FROM Inventory i
                WHERE i.product.id = :productId
            """)
    Optional<Inventory> findByProductIdForUpdate(
            @Param("productId") UUID productId
    );


    @EntityGraph(attributePaths = {"product"})
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
                SELECT i
                FROM Inventory i
                WHERE i.product.id IN :productIds
            """)
    List<Inventory> findAllByProductIdInForUpdate(
            @Param("productIds") List<UUID> productIds
    );
}