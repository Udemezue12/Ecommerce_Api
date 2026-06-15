package com.uchechukwu.store.webhooks;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uchechukwu.store.config.PaymentPropertiesConfig;
import com.uchechukwu.store.dtos.request.PaymentWebhookPayload;
import com.uchechukwu.store.enums.PaymentMethod;
import com.uchechukwu.store.exceptions.ResourceNotFoundException;
import com.uchechukwu.store.exceptions.UnAuthorizedException;
import com.uchechukwu.store.fintech.foreignGateways.ForeignPaymentGateway;
import com.uchechukwu.store.repositories.PaymentTransactionRepository;
import com.uchechukwu.store.responses.WebhookRequest;
import com.uchechukwu.store.service.OrderService;
import com.uchechukwu.store.tasks.PaymentVerificationTask;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jobrunr.scheduling.JobScheduler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

@RequiredArgsConstructor
@Service
@Slf4j
public class WebhookService {


    private final PaymentTransactionRepository paymentRepo;
    private final PaymentVerificationTask paymentTask;
    private final JobScheduler jobScheduler;
    private final ObjectMapper objectMapper;
    private final PaymentPropertiesConfig paymentConfig;
    private final ForeignPaymentGateway foreignPaymentGateway;
    private final OrderService orderService;

    public ResponseEntity<Void> verifyWebhook(String paymentMethodHeader, String signature, String rawJsonRequestBody, String signatureHeader) {


        var method = PaymentMethod.valueOf(paymentMethodHeader.toUpperCase());

        if (method == PaymentMethod.PAYSTACK) {

            if (!WebhookValidator.isValid(rawJsonRequestBody, signature, paymentConfig.getPaystackSecretKey())) {
                log.warn("Unauthorized Paystack webhook attempt blocked!");
                throw new UnAuthorizedException("Not Authorized");
            }

            try {
                PaymentWebhookPayload payload = objectMapper.readValue(rawJsonRequestBody, PaymentWebhookPayload.class);

                if ("charge.success".equals(payload.event())) {
                    var reference = payload.data().reference();

                    var transaction = paymentRepo.findByGeneratedReference(reference)
                            .orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));


                    var transactionId = transaction.getId();
                    jobScheduler.enqueue(() -> paymentTask.verifyPaymentUsingReference(reference, transactionId.toString()));
                }
                return ResponseEntity.ok().build();

            } catch (Exception e) {
                log.error("Failed to process Paystack webhook", e);
                return ResponseEntity.badRequest().build();
            }

        } else {

            if (signatureHeader == null || !safeStringEquals(signatureHeader, paymentConfig.getFlutterwaverSecretHash())) {
                log.warn("Unauthorized Flutterwave webhook attempt blocked!");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            try {
                PaymentWebhookPayload payload = objectMapper.readValue(rawJsonRequestBody, PaymentWebhookPayload.class);

                if (payload.event() != null && payload.event().contains("charge")) {
                    var incomingReference = payload.data().reference();

                    var internalPayment = paymentRepo.findByGeneratedReference(incomingReference)
                            .orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));


                    if (!internalPayment.getGeneratedReference().equals(incomingReference)) {
                        log.error("CRITICAL MISMATCH: Internal Ref: {} vs Webhook Ref: {}",
                                internalPayment.getGeneratedReference(), incomingReference);
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
                    }
                    var id = internalPayment.getId();
                    jobScheduler.enqueue(() -> paymentTask.verifyPaymentUsingReference(incomingReference, id.toString()));
                }
                return ResponseEntity.ok().build();

            } catch (Exception e) {
                log.error("Failed to process Flutterwave webhook", e);
                return ResponseEntity.badRequest().build();
            }
        }
    }

    public void handleWebhookEvent(WebhookRequest request) {
        foreignPaymentGateway.parseWebhookRequest(request).ifPresent(webhookResult -> {
            orderService.updateOrderStatus(webhookResult.orderId(), webhookResult.status());
        });
    }

    private boolean safeStringEquals(String a, String b) {
        return MessageDigest.isEqual(
                a.getBytes(StandardCharsets.UTF_8),
                b.getBytes(StandardCharsets.UTF_8)
        );
    }
}