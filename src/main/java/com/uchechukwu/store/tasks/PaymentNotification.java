package com.uchechukwu.store.tasks;

import com.uchechukwu.store.notification_service.EmailService;
import com.uchechukwu.store.notification_service.SmsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jobrunr.jobs.annotations.Job;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentNotification {

    private final EmailService emailService;

    private final SmsService smsService;

    @Job(name = "Send Payment Successful email", retries = 3)
    public void sendPaymentSuccessNotificationEmail(UUID orderId,
                                                    String name,
                                                    String email,
                                                    UUID transactionId
    ) {
        emailService.sendPaymentSuccessEmail(email, name, orderId.toString(), transactionId);


    }

    @Job(name = "Send Payment Successful Sms", retries = 3)
    public void sendPaymentSuccessNotificationSms(
            String phoneNumber,
            String name,
            UUID orderId
    ) {


        smsService.sendPaymentSuccessSms(phoneNumber,
                name,
                orderId.toString());

    }
}