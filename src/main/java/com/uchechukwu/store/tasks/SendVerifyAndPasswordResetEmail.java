package com.uchechukwu.store.tasks;

import com.uchechukwu.store.notification_service.EmailService;
import com.uchechukwu.store.notification_service.SmsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jobrunr.jobs.annotations.Job;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SendVerifyAndPasswordResetEmail {
        private final EmailService emailService;
        private final SmsService smsService;

        @Job(name = "Send verification email", retries = 2)
        public void sendVerificationEmail(
                        String email,
                        String otp,
                        String fullName,
                        String token) {

                emailService.sendVerificationEmail(
                                email,
                                otp,
                                token,
                                fullName);

        }

        @Job(name = "Send verification sms", retries = 2)
        public void sendVerificationSms(String phoneNumber, String otp, String fullName) {
                smsService.sendOtp(
                                phoneNumber,
                                otp,
                                fullName);
        }

        @Job(name = "Send password reset email", retries = 2)
        public void sendPasswordResetEmail(

                        String email,
                        String otp,
                        String fullName,
                        String token) {

                emailService.sendPasswordResetEmail(
                                email,
                                otp,
                                token,
                                fullName);

        }

        @Job(name = "Send password reset sms")
        public void sendPasswordResetSms(String phoneNumber, String otp, String fullName) {
                smsService.sendOtp(
                                phoneNumber,
                                otp,
                                fullName);
        }

}
