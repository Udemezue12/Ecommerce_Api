package com.uchechukwu.store.service;

import com.uchechukwu.store.GatewaysController.PaymentGateway;
import com.uchechukwu.store.core.GenerateReference;
import com.uchechukwu.store.core.GetCurrentUser;
import com.uchechukwu.store.customCache.CustomCacheEvict;
import com.uchechukwu.store.customCache.CustomCacheable;
import com.uchechukwu.store.dtos.request.PaymentRefundRequest;
import com.uchechukwu.store.dtos.request.PaymentRequest;
import com.uchechukwu.store.dtos.response.PaymentTransactionResponse;
import com.uchechukwu.store.dtos.response.PaymentTransactionsPageResponse;
import com.uchechukwu.store.entities.PaymentTransaction;
import com.uchechukwu.store.enums.OrderStatus;
import com.uchechukwu.store.enums.PaymentStatus;
import com.uchechukwu.store.events.PaymentSuccessEvent;
import com.uchechukwu.store.exceptions.BadRequestException;
import com.uchechukwu.store.exceptions.ResourceNotFoundException;
import com.uchechukwu.store.fintech.africanGateways.PaymentInitializeResponse;
import com.uchechukwu.store.fintech.africanGateways.PaymentRefundResponse;
import com.uchechukwu.store.fintech.africanGateways.PaymentVerifyResponse;
import com.uchechukwu.store.mappers.PaymentTransactionMapper;
import com.uchechukwu.store.repositories.OrderRepository;
import com.uchechukwu.store.repositories.PaymentTransactionRepository;
import com.uchechukwu.store.validators.EntityValidator;
import com.uchechukwu.store.validators.ValidatedSortedData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentTransactionService {
    private final PaymentTransactionRepository paymentRepo;
    private final GetCurrentUser getCurrentUser;
    private final CartService cartService;
    private final OrderRepository orderRepo;
    private final EntityValidator entityValidator;
    private final OrderService orderService;

    private final ApplicationEventPublisher applicationEventPublisher;
    private final PaymentGateway paymentGateway;
    private final ValidatedSortedData validatedSortedData;

    @Transactional
    @CustomCacheEvict(cacheNames = {
            "userTransactions", "adminTransactions", "vendorTransactions"})


    public PaymentInitializeResponse initializePayment(UUID orderId, PaymentRequest request) {
        var order = entityValidator.findByIdOrThrow(orderRepo, orderId, "Order");
        if (order.getStatus() == OrderStatus.CANCELED || order.getStatus() == OrderStatus.REFUNDED) {
            throw new BadRequestException(
                    "Order has canceled, create a new Order");
        }
        if (order.getStatus() == OrderStatus.PAID) {

            throw new BadRequestException(
                    "Order has already been Paid");
        }
        var currentUser = getCurrentUser.getCurrentUser();
        var reference = GenerateReference.generateReference(
                request.paymentMethod().getPrefix());
        var amount = order.getTotalPrice();
        var payment = paymentGateway.getPaymentGateway(
                request.paymentMethod()
                        .name()
                        .toLowerCase(),
                reference,
                amount,
                currentUser.getEmail());

        var transaction = PaymentTransaction.builder()
                .generatedReference(reference)
                .paymentMethod(request.paymentMethod())
                .amount(amount)
                .user(currentUser)
                .order(order)
                .status(PaymentStatus.PENDING)
                .build();
        paymentRepo.save(transaction);
        return payment;

    }

    @Transactional
    @CustomCacheEvict(cacheNames = {
            "userTransactions", "adminTransactions", "vendorTransactions"})
    public PaymentVerifyResponse webhookVerifyPayment(String generatedReference, PaymentTransaction transaction) {

        if (transaction.getStatus() == PaymentStatus.SUCCESS) {

            throw new BadRequestException(
                    "Transaction already verified");
        }

        if (Boolean.TRUE.equals(transaction.getOrderProcessed())) {

            throw new BadRequestException(
                    "Order already processed for this transaction");
        }
        var response = paymentGateway.verifyPaymentGateway(generatedReference,
                transaction.getPaymentMethod().name().toLowerCase());
        var orderId = transaction.getOrder().getId();
        var userId = transaction.getOrder().getUser().getId();

        var finalReference = response.gatewayReference() != null
                ? response.gatewayReference()
                : generatedReference;
        var verified = response.success();
        updatePaymentTransaction(
                transaction,
                verified,
                finalReference,
                response.transactionId(),
                response.currency(),
                response.channel(),
                orderId, userId);

        return verified
                ? response
                : PaymentVerifyResponse.builder()
                .success(false)
                .status(PaymentStatus.FAILED.name())
                .build();
    }

    @Transactional
    @CustomCacheEvict(cacheNames = {
            "userTransactions", "adminTransactions", "vendorTransactions"})
    public PaymentVerifyResponse verifyPayment(String generatedReference) {
        var transaction = paymentRepo.findByGeneratedReference(generatedReference)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
        return webhookVerifyPayment(transaction.getGeneratedReference(), transaction);

    }

    @Transactional
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

    private PaymentSuccessEvent getPaymentSuccessEventPublisher(PaymentTransaction transaction, UUID orderId) {
        return new PaymentSuccessEvent(
                orderId,
                transaction.getUser().getName(),
                transaction.getUser().getEmail(),
                transaction.getId(),
                transaction.getUser().getPhoneNumber());
    }

    @Transactional
    @CustomCacheEvict(cacheNames = {
            "userTransactions", "adminTransactions", "vendorTransactions"})
    public PaymentRefundResponse refundPayment(
            String transactionId, PaymentRefundRequest request) {

        var transaction = paymentRepo.findByPaymentProviderTransactionId(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));


        if (transaction.getStatus() == PaymentStatus.REFUNDED) {

            return PaymentRefundResponse.builder()
                    .success(false)
                    .status("ALREADY_REFUNDED")
                    .message("Payment has already been refunded")
                    .reference(transaction.getGeneratedReference())
                    .build();
        }

        if (transaction.getStatus() != PaymentStatus.SUCCESS) {

            return PaymentRefundResponse.builder()
                    .success(false)
                    .status("INVALID_STATUS")
                    .message("Only successful payments can be refunded")
                    .reference(transaction.getGeneratedReference())
                    .build();
        }
        var finalAmount = request.refundAmount() != null ? request.refundAmount() : transaction.getAmount();
        var gateway = paymentGateway.refundPaymentGateway(
                transaction.getPaymentProviderTransactionId(),
                transaction.getPaymentMethod().name().toLowerCase(), finalAmount, request.reason());

        var refundSuccess = gateway.success();

        if (!refundSuccess) {
            log.warn(
                    "Refund failed. Reference={}, Gateway={}, Message={}",
                    transaction.getPaymentProviderReference(),
                    transaction.getPaymentMethod(),
                    gateway.message());

            return PaymentRefundResponse.builder()
                    .success(false)
                    .status("REFUND_FAILED")
                    .message(gateway.message())
                    .reference(transaction.getGeneratedReference())
                    .build();
        }

        transaction.setStatus(PaymentStatus.REFUNDED);
        transaction.setRefundedAt(LocalDateTime.now());
        orderService.updateOrderStatus(transaction.getOrder().getId(), OrderStatus.REFUNDED);

        paymentRepo.save(transaction);


        var amount = gateway.amount() != null
                ? gateway.amount()
                : transaction.getAmount();

        return PaymentRefundResponse.builder()
                .success(true)
                .status("REFUNDED")
                .message(gateway.message())
                .refundId(gateway.refundId())
                .reference(transaction.getPaymentProviderReference())
                .amount(amount)
                .currency(transaction.getCurrency())
                .build();
    }

    @Transactional(readOnly = true)
    @CustomCacheable(
            value = "userTransactions",
            key = "#userId + '-' + '-' + #page + '-' + #size"
    )
    public PaymentTransactionsPageResponse getAllUserTransactions(
            String sort, String sortingValue1, int page, int size) {
        var userId = getCurrentUser.getCurrentUserId();
        var pageable = validatedSortedData.getValidatedPageableData(sort, sortingValue1, page, size);

        var result = paymentRepo
                .findAllByUserId(userId, pageable)
                .map(PaymentTransactionMapper::manyResponseDto);
        return new PaymentTransactionsPageResponse(
                result.getContent(),
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages(),
                result.isFirst(),
                result.isLast()
        );
    }

    @Transactional(readOnly = true)
    @CustomCacheable(
            value = "userTransactions",
            key = "#user' + #transactionId"
    )
    public PaymentTransactionResponse getUserTransaction(UUID transactionId) {
        return paymentRepo.findByIdAndUserId(transactionId, getCurrentUser.getCurrentUserId()).map(PaymentTransactionMapper::toResponseDto).orElseThrow(() -> new ResourceNotFoundException("No payment transaction found"));

    }

    @Transactional(readOnly = true)
    @CustomCacheable(
            value = "adminTransactions",
            key = "'page-' + '-' + #page + '-' + #size"
    )
    public PaymentTransactionsPageResponse getAllAdminTransactions(
            String sort, String sortingValue1, int page, int size) {
        var pageable = validatedSortedData.getValidatedPageableData(sort, sortingValue1, page, size);

        var result = paymentRepo
                .findAll(pageable)
                .map(PaymentTransactionMapper::manyResponseDto);
        return new PaymentTransactionsPageResponse(
                result.getContent(),
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages(),
                result.isFirst(),
                result.isLast()
        );
    }

    @Transactional(readOnly = true)
    @CustomCacheable(
            value = "adminTransactions",
            key = "'admin-' + #transactionId"
    )
    public PaymentTransactionResponse getAdminTransactionById(
            UUID transactionId) {

        return paymentRepo
                .findById(transactionId)
                .map(PaymentTransactionMapper::toResponseDto)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Transaction not found"));
    }

    @Transactional(readOnly = true)
    @CustomCacheable(
            value = "vendorTransactions",
            key = "#vendorId + '-' + #page + '-' + #size"
    )
    public PaymentTransactionsPageResponse getTransactionsForProductOwner(
            String sort, String sortingValue1, int page, int size) {
        var pageable = validatedSortedData.getValidatedPageableData(sort, sortingValue1, page, size);
        var userId = getCurrentUser.getCurrentUserId();
        var result = paymentRepo
                .findAllByProductOwner(userId, pageable)
                .map(PaymentTransactionMapper::manyResponseDto);
        return new PaymentTransactionsPageResponse(
                result.getContent(),
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages(),
                result.isFirst(),
                result.isLast()
        );
    }


}