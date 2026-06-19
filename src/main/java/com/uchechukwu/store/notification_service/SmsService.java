package com.uchechukwu.store.notification_service;


import com.uchechukwu.store.configProperties.NotificationProperties;
import com.uchechukwu.store.dtos.response.TermiiSmsResponse;
import com.uchechukwu.store.sms.TermiiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SmsService {

    private final TermiiClient termiiClient;

    private final NotificationProperties properties;

    public TermiiSmsResponse sendOtp(
            String phone,
            String otp,
            String name) {

        return termiiClient.sendOtpSms(
                phone,
                otp,
                null,
                name,
                properties.getTermiiSenderId());
    }

    public TermiiSmsResponse sendCustomSms(
            String phone,
            String message) {

        return termiiClient.sendOtpSms(
                phone,
                null,
                message,
                null,
                properties.getTermiiSenderId());
    }

    public TermiiSmsResponse sendPaymentSuccessSms(String phoneNumber,
                                                   String name,
                                                   String orderId) {
        return termiiClient.sendPaymentSuccessSms(
                phoneNumber,
                name,
                orderId,
                properties.getTermiiSenderId()
        );

    }
}