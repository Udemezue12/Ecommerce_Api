package com.uchechukwu.store.GatewaysController;

import com.uchechukwu.store.exceptions.ResourceNotFoundException;
import com.uchechukwu.store.fintech.africanGateways.PaymentInitializeResponse;
import com.uchechukwu.store.fintech.africanGateways.PaymentRefundResponse;
import com.uchechukwu.store.fintech.africanGateways.PaymentVerifyResponse;
import com.uchechukwu.store.interfaces.PaymentGatewayInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class PaymentGateway {
    private final Map<String, PaymentGatewayInterface> paymentGateways;

    public PaymentInitializeResponse getPaymentGateway(String paymentMethod, String reference, BigDecimal amount,
                                                       String email) {
        var paymentGateway = paymentGateways.get(paymentMethod);
        if (paymentGateway == null) {
            throw new ResourceNotFoundException("No Payment Service found");
        }
        return paymentGateway.initializePayment(
                email,
                reference,
                amount,
                "http://localhost:8080/payment/callback");
    }

    public PaymentVerifyResponse verifyPaymentGateway(String reference, String paymentMethod) {
        var paymentGateway = paymentGateways.get(paymentMethod);
        if (paymentGateway == null) {
            throw new ResourceNotFoundException("No Payment Service found");
        }
        return paymentGateway.verifyPayment(
                reference);
    }

    public PaymentVerifyResponse verifyWebhookPaymentGateway(String reference, String paymentMethod) {
        var paymentGateway = paymentGateways.get(paymentMethod);
        if (paymentGateway == null) {
            throw new ResourceNotFoundException("No Payment Service found");
        }
        return paymentGateway.webhookPaymentVerification(
                reference);
    }

    public PaymentRefundResponse refundPaymentGateway(String transactionId, String paymentMethod, BigDecimal amount, String reason) {
        var paymentGateway = paymentGateways.get(paymentMethod);
        if (paymentGateway == null) {
            throw new ResourceNotFoundException("No Payment Service found");
        }
        return paymentGateway.refundPayment(
                transactionId, amount, reason);
    }
}
