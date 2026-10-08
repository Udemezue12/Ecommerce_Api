package com.uchechukwu.store.service;

import com.uchechukwu.store.customCache.CustomCacheEvict;
import com.uchechukwu.store.entities.Order;
import com.uchechukwu.store.entities.PaymentTransaction;
import com.uchechukwu.store.entities.User;
import com.uchechukwu.store.enums.OrderStatus;
import com.uchechukwu.store.enums.PaymentMethod;
import com.uchechukwu.store.enums.PaymentStatus;
import com.uchechukwu.store.events.PaymentSuccessEvent;
import com.uchechukwu.store.repositories.PaymentTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentProcessingService {
    private final PaymentTransactionRepository paymentRepo;
    private final OrderService orderService;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final CartService cartService;

    @Transactional
    @CustomCacheEvict(cacheNames = {
            "userTransactions", "adminTransactions", "vendorTransactions"})
    public void savePayment(String reference, PaymentMethod paymentMethod, BigDecimal amount, User currentUser, Order order) {
        var transaction = PaymentTransaction.builder()
                .generatedReference(reference)
                .paymentMethod(paymentMethod)
                .amount(amount)
                .user(currentUser)
                .order(order)
                .status(PaymentStatus.PENDING)
                .build();
        paymentRepo.save(transaction);
    }

    @Transactional
    @CustomCacheEvict(cacheNames = {
            "userTransactions", "adminTransactions", "vendorTransactions"})
    public void updatePaymentTransaction(
            PaymentTransaction transaction,
            boolean verified,
            String reference,
            String transactionId,
            String currency,
            String channel,
            UUID orderId, UUID userId) {

        if (verified) {

            transaction.setStatus(PaymentStatus.SUCCESS);
            transaction.setVerifiedAt(LocalDateTime.now());
            transaction.setPaidAt(LocalDateTime.now());
            transaction.setPaymentProviderReference(reference);
            transaction.setPaymentProviderTransactionId(transactionId);
            transaction.setOrderProcessed(true);
            transaction.setCurrency(currency);
            transaction.setPaymentChannel(channel);

            orderService.updateOrderStatus(orderId, OrderStatus.PAID);

            cartService.clearCartByUserId(userId);
            paymentRepo.save(transaction);

            applicationEventPublisher.publishEvent(
                    getPaymentSuccessEventPublisher(
                            transaction,
                            orderId));

            return;
        }

        transaction.setStatus(PaymentStatus.FAILED);
        transaction.setFailedAt(LocalDateTime.now());

        paymentRepo.save(transaction);
    }

    @Transactional
    @CustomCacheEvict(cacheNames = {
            "userTransactions", "adminTransactions", "vendorTransactions"})
    public void refundPayment(PaymentTransaction transaction) {
        transaction.setStatus(PaymentStatus.REFUNDED);
        transaction.setRefundedAt(LocalDateTime.now());
        orderService.updateOrderStatus(transaction.getOrder().getId(), OrderStatus.REFUNDED);

        paymentRepo.save(transaction);
    }

    private PaymentSuccessEvent getPaymentSuccessEventPublisher(PaymentTransaction transaction, UUID orderId) {
        return new PaymentSuccessEvent(
                orderId,
                transaction.getUser().getName(),
                transaction.getUser().getEmail(),
                transaction.getId(),
                transaction.getUser().getPhoneNumber());
    }
}
