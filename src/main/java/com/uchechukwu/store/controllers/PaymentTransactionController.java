package com.uchechukwu.store.controllers;

import com.uchechukwu.store.Idempotency.Idempotent;
import com.uchechukwu.store.dtos.request.PaymentRefundRequest;
import com.uchechukwu.store.dtos.request.PaymentRequest;
import com.uchechukwu.store.dtos.response.PaymentTransactionsPageResponse;
import com.uchechukwu.store.fintech.africanGateways.PaymentInitializeResponse;
import com.uchechukwu.store.fintech.africanGateways.PaymentRefundResponse;
import com.uchechukwu.store.fintech.africanGateways.PaymentVerifyResponse;
import com.uchechukwu.store.responses.PaymentTransactionResponse;
import com.uchechukwu.store.service.PaymentTransactionService;
import com.uchechukwu.store.utilities.rateLimiter.RateLimit;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Payment Transactions", description = "Endpoints for everything payments")
public class PaymentTransactionController {

    private final PaymentTransactionService paymentService;

    @PostMapping("/{orderId}/initialize-payment")
    @RateLimit(times = 4, seconds = 8)
    @Idempotent
    public ResponseEntity<PaymentInitializeResponse> startPayment(
            @PathVariable UUID orderId,
            @Valid @RequestBody PaymentRequest request) {

        var response = paymentService.initializePayment(orderId, request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{reference}/verify-payment")
    @RateLimit(times = 4, seconds = 8)
    public ResponseEntity<PaymentVerifyResponse> verifyPayment(
            @PathVariable String reference) {

        var response = paymentService.verifyPayment(reference);
        return ResponseEntity.ok(response);

    }

    @PostMapping("/{reference}/refund-payment")
    @RateLimit(times = 4, seconds = 8)
    @Idempotent(ttl = 60)
    public ResponseEntity<PaymentRefundResponse> refundPayment(
            @PathVariable String reference, @Valid @RequestBody PaymentRefundRequest request) {
        var response = paymentService.refundPayment(reference, request);
        return ResponseEntity.ok(response);

    }

    @GetMapping("/payment/user/all")
    @RateLimit(times = 4, seconds = 8)
    public ResponseEntity<PaymentTransactionsPageResponse> getAllUserTransactions(
            @RequestParam(required = false, defaultValue = "", name = "sort") String sort,
            @RequestParam(required = false, defaultValue = "0", name = "page") int page,
            @RequestParam(required = false, defaultValue = "10", name = "size") int size) {
        var response = paymentService.getAllUserTransactions(sort, "createdAt", page, size);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/payment/{paymentId}/get")
    @RateLimit(times = 4, seconds = 8)
    public ResponseEntity<PaymentTransactionResponse> getUserTransaction(
            @PathVariable UUID paymentId) {
        var response = paymentService.getUserTransaction(paymentId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/vendor/payments/get")
    @RateLimit
    public ResponseEntity<PaymentTransactionsPageResponse> getVendorPayments(@RequestParam(required = false, defaultValue = "", name = "sort") String sort,
                                                                             @RequestParam(required = false, defaultValue = "0", name = "page") int page,
                                                                             @RequestParam(required = false, defaultValue = "10", name = "size") int size) {
        var response = paymentService.getTransactionsForProductOwner(sort, "createdAt", page, size);
        return ResponseEntity.ok(response);

    }

    @GetMapping("/admin/payments/get")
    @RateLimit
    public ResponseEntity<PaymentTransactionsPageResponse> getAdminPayments(@RequestParam(required = false, defaultValue = "", name = "sort") String sort,
                                                                            @RequestParam(required = false, defaultValue = "0", name = "page") int page,
                                                                            @RequestParam(required = false, defaultValue = "10", name = "size") int size) {
        var response = paymentService.getAllAdminTransactions(sort, "createdAt", page, size);
        return ResponseEntity.ok(response);

    }

    @GetMapping("/admin/{paymentId}/get")
    @RateLimit
    public ResponseEntity<PaymentTransactionResponse> getPaymentsForAdmin(@PathVariable UUID paymentId) {
        var response = paymentService.getAdminTransactionById(paymentId);
        return ResponseEntity.ok(response);

    }
}
