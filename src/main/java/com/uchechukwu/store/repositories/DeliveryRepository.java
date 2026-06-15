package com.uchechukwu.store.repositories;


import com.uchechukwu.store.dtos.response.DeliveryFlatRow;
import com.uchechukwu.store.entities.Delivery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, UUID> {

    @Query("""
                SELECT DISTINCT d
                FROM Delivery d
                LEFT JOIN FETCH d.rider
                JOIN FETCH d.order o
                JOIN FETCH o.user u
            
                WHERE d.id = :deliveryId
            """)
    Optional<Delivery> findDetailedDeliveryById(@Param("deliveryId") UUID deliveryId);


    Page<Delivery> findAll(Pageable pageable);


    @Query("""
                SELECT DISTINCT d
                FROM Delivery d
                LEFT JOIN FETCH d.rider
                JOIN FETCH d.order o
                JOIN FETCH o.user u
                WHERE d.id = :deliveryId
                  AND d.customer.id = :customerId
            """)
    Optional<Delivery> findDetailedDeliveryByIdAndCustomerId(
            @Param("deliveryId") UUID deliveryId,
            @Param("customerId") UUID customerId
    );


    @EntityGraph(attributePaths = {
            "customer",
            "order"

    })
    @Query("""
                SELECT d
                FROM Delivery d
                WHERE d.customer.id = :customerId
            """)
    Page<Delivery> findDetailedDeliveriesByCustomerId(
            UUID customerId,
            Pageable pageable
    );

    @Query(value = """
            SELECT 
                BIN_TO_UUID(d.id)  as deliveryId,
                d.status as status,
                d.delivery_type as deliveryType,
                d.pickup_address as pickupAddress,
                d.delivery_address as deliveryAddress,
                d.estimated_delivery as estimatedDelivery,
                d.notes as notes,
            
                BIN_TO_UUID(u.id) as customerId,
                u.name as customerName,
                u.email as customerEmail,
                u.phone_number as customerPhone,
            
                BIN_TO_UUID(o.id) as orderId,
                o.total_price as orderTotal,
            
                BIN_TO_UUID(p.id) as productId,
                p.name as productName,
                oi.quantity as quantity,
                oi.unit_price as unitPrice,
            
                pt.payment_provider_transaction_id as providerTxId,
                pt.payment_method as paymentMethod,
                pt.generated_reference as providerRef,
                pt.status as paymentStatus
            
            FROM deliveries d
            JOIN orders o ON o.id = (d.order_id)
            JOIN users u ON u.id = o.customer_id
            LEFT JOIN payment_transactions pt ON pt.order_id = o.id AND pt.status = 'SUCCESS'
            JOIN order_items oi ON oi.order_id = o.id
            JOIN products p ON p.id = oi.product_id
            
            WHERE d.id = :deliveryId
            AND d.customer_id = :customerId
            ORDER BY oi.id         
            """, nativeQuery = true)
    List<DeliveryFlatRow> findDeliveryFlat(
            @Param("deliveryId") UUID deliveryId,
            @Param("customerId") UUID customerId
    );
//    LEFT JOIN payment_transactions pt
//    ON pt.order_id = o.id
//    AND pt.status = 'SUCCESS' #Use this when 1 order -> 1 successful payment
}