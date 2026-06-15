package com.uchechukwu.store.notification_service;

import com.uchechukwu.store.config.NotificationProperties;
import com.uchechukwu.store.core.NotificationCircuitBreaker;
import com.uchechukwu.store.email.brevo.BrevoClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final NotificationCircuitBreaker breaker;
    private final NotificationProperties properties;

    private final BrevoClient brevoClient;

    public void sendVerificationEmail(
            String email,
            String otp,
            String token,
            String name) {

        String verifyLink = properties.getFrontendUrl()
                + "/verify-email.html?token="
                + token;

        String html = """
                <html>
                <body style="font-family: Arial;">
                    <h2>Verify Your Email</h2>
                    <p>Hello %s,</p>
                    <p>Your OTP is:</p>
                    <h3>%s</h3>
                    <a href="%s">
                        Verify Email
                    </a>
                </body>
                </html>
                """.formatted(name, otp, verifyLink);

        String text = """
                Hello %s,
                
                Your OTP is: %s
                
                Verify email:
                %s
                """.formatted(name, otp, verifyLink);

        brevoClient.sendBrevoEmail(
                email,
                name,
                "Verify Your Email",
                html,
                text);
    }

    public void sendPasswordResetEmail(
            String email,
            String otp,
            String token,
            String name) {

        String resetLink = properties.getFrontendUrl()
                + "/reset-password?token="
                + token;

        String html = """
                <html>
                <body style="font-family: Arial;">
                    <h2>Password Reset</h2>
                    <p>Hello %s,</p>
                    <p>Your OTP is:</p>
                    <h3>%s</h3>
                    <a href="%s">
                        Reset Password
                    </a>
                </body>
                </html>
                """.formatted(name, otp, resetLink);

        String text = """
                Hello %s,
                
                Your Password Reset OTP is: %s
                
                Reset password:
                %s
                """.formatted(name, otp, resetLink);

        brevoClient.sendBrevoEmail(
                email,
                name,
                "Reset Your Password",
                html,
                text);
    }

    public void sendPaymentSuccessEmail(
            String email,
            String name,
            String orderId,
            UUID transactionId) {

        String html = """
                <html>
                <body style="font-family: Arial, sans-serif;">
                    <h2>Payment Successful</h2>
                
                    <p>Hello %s,</p>
                
                    <p>
                        Your payment has been successfully processed.
                    </p>
                
                    <p>
                        <strong>Order ID:</strong> %s
                    </p>
                
                    <p>
                        <strong>Transaction ID:</strong> %s
                    </p>
                
                    <p>
                        Thank you for shopping with us.
                    </p>
                
                    <p>
                        Your order is now being processed.
                    </p>
                
                    <br>
                
                    <p>
                        Regards,<br>
                        Support Team
                    </p>
                
                </body>
                </html>
                """.formatted(
                name,
                orderId,
                transactionId);

        String text = """
                Hello %s,
                
                Your payment was successful.
                
                Order ID: %s
                
                Transaction ID: %s
                
                Your order is now being processed.
                
                Thank you for shopping with us.
                """
                .formatted(
                        name,
                        orderId,
                        transactionId);

        brevoClient.sendBrevoEmail(
                email,
                name,
                "Payment Successful",
                html,
                text);
    }

}
