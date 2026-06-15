package com.uchechukwu.store.repositories;

import com.uchechukwu.store.entities.PaymentTransaction;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, UUID> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<PaymentTransaction> findByGeneratedReference(String reference);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<PaymentTransaction> findByPaymentProviderReference(String reference);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<PaymentTransaction> findByPaymentProviderTransactionId(String transactionId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @EntityGraph(attributePaths = {"order"})
    Optional<PaymentTransaction>
    findTopByOrderIdOrderByCreatedAtDesc(
            UUID orderId);

    @EntityGraph(attributePaths = {
            "order",
            "user",
            "order.items"
    })
    Page<PaymentTransaction> findByUserId(
            UUID userId,
            Pageable pageable
    );

    @EntityGraph(attributePaths = {
            "user",
            "order",
            "order.items"
    })
    Optional<PaymentTransaction> findByIdAndUserId(
            UUID id,
            UUID userId
    );


    @EntityGraph(attributePaths = {
            "order"

    })
    Page<PaymentTransaction> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {
            "order",
            "user"
    })
    Page<PaymentTransaction> findAllByUserId(UUID userId, Pageable pageable);

    @EntityGraph(attributePaths = {
            "order",

    })
    @Query(
            value = """
                        SELECT DISTINCT pt
                        FROM PaymentTransaction pt
                        JOIN pt.order o
                        JOIN o.items oi
                        JOIN Inventory i
                            ON oi.product.id = i.product.id
                        WHERE i.user.id = :vendorId
                    """,
            countQuery = """
                        SELECT COUNT(DISTINCT pt.id)
                        FROM PaymentTransaction pt
                        JOIN pt.order o
                        JOIN o.items oi
                        JOIN Inventory i
                            ON oi.product.id = i.product.id
                        WHERE i.user.id = :vendorId
                    """
    )
    Page<PaymentTransaction> findAllByProductOwner(
            UUID vendorId,
            Pageable pageable
    );

}
