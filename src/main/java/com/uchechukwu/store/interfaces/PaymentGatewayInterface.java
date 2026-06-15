package com.uchechukwu.store.interfaces;

import com.uchechukwu.store.fintech.africanGateways.PaymentInitializeResponse;
import com.uchechukwu.store.fintech.africanGateways.PaymentRefundResponse;
import com.uchechukwu.store.fintech.africanGateways.PaymentVerifyResponse;

import java.math.BigDecimal;

public interface PaymentGatewayInterface {
    PaymentInitializeResponse initializePayment(
            String email,
            String reference,
            BigDecimal amount,
            String callbackUrl);

    PaymentVerifyResponse verifyPayment(
            String reference);

    PaymentVerifyResponse webhookPaymentVerification(String reference);

    PaymentRefundResponse refundPayment(String transactionId, BigDecimal amount, String reason);


}
