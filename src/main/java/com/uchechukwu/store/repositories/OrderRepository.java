package com.uchechukwu.store.repositories;


import com.uchechukwu.store.entities.Order;
import com.uchechukwu.store.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    @EntityGraph(attributePaths = {"user"})
    Page<Order> findByUserId(UUID userId, Pageable pageable);


    @EntityGraph(attributePaths = {"user"})
    Optional<Order> findByIdAndUserId(UUID orderId, UUID userId);


    Optional<Order> findOrderById(UUID orderId);

    @EntityGraph(attributePaths = {"transactions"})
    Optional<Order> findWithTransactionsById(UUID orderId);

    Optional<Order>
    findTopByUserIdAndStatusOrderByCreatedAtDesc(
            UUID userId,
            OrderStatus status);

    @EntityGraph(attributePaths = {"items", "items.product"})
    Page<Order> findAll(Pageable pageable);

    List<Order> findAllByStatusAndCreatedAtBefore(
            OrderStatus status,
            LocalDateTime cutoffTime
    );

    @Modifying
    @Query("""
                update Order o
                set o.status = :newStatus
                where o.status = :oldStatus
                  and o.createdAt < :cutoff
            """)
    int cancelExpiredOrders(
            @Param("oldStatus") OrderStatus oldStatus,
            @Param("newStatus") OrderStatus newStatus,
            @Param("cutoff") LocalDateTime cutoff
    );

}
