package com.uchechukwu.store.tasks;

import com.uchechukwu.store.exceptions.BadRequestException;
import com.uchechukwu.store.repositories.PaymentTransactionRepository;
import com.uchechukwu.store.service.PaymentTransactionService;
import lombok.RequiredArgsConstructor;
import org.jobrunr.jobs.annotations.Job;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PaymentVerificationTask {


    private final PaymentTransactionService paymentService;


    private final PaymentTransactionRepository paymentRepo;

    @Job(name = "verify-payment-for-reference %0", retries = 3)
    public void verifyPaymentUsingReference(String generatedReference, String transactionId) {
        var transactionUuid = UUID.fromString(transactionId);

        var transaction = paymentRepo.findById(transactionUuid)
                .orElseThrow(() -> new BadRequestException("Transaction entity state missing for ID: " + transactionId));

        paymentService.webhookVerifyPayment(generatedReference, transaction);
    }
}