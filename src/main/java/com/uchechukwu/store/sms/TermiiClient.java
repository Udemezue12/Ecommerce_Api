package com.uchechukwu.store.sms;

import com.uchechukwu.store.configProperties.NotificationProperties;
import com.uchechukwu.store.dtos.response.TermiiSmsResponse;
import com.uchechukwu.store.exceptions.NotificationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class TermiiClient {

    private final NotificationProperties properties;

    private WebClient client() {

        return WebClient.builder()
                .baseUrl(properties.getTermiiBaseUrl())
                .defaultHeader(
                        HttpHeaders.CONTENT_TYPE,
                        MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public boolean ping() {

        try {

            Map<String, Object> payload = Map.of(
                    "to", "2340000000000",
                    "from", properties.getTermiiSenderId(),
                    "sms", "Ping test",
                    "type", "plain",
                    "channel", "generic",
                    "api_key", properties.getTermiiApiKey());

            TermiiSmsResponse response = client()
                    .post()
                    .uri("/api/sms/send")
                    .bodyValue(payload)
                    .retrieve()
                    .bodyToMono(TermiiSmsResponse.class)
                    .timeout(Duration.ofSeconds(15))
                    .block();

            return response != null;

        } catch (Exception ex) {

            log.error("Termii ping failed", ex);

            return false;
        }
    }

    public TermiiSmsResponse sendPaymentSuccessSms(
            String phoneNumber,
            String name,
            String orderId,
            String senderId) {

        String smsMessage = """
                Hello %s,
                
                Your payment was successful.
                
                Order ID: %s
                
                Your order is now being processed.
                
                Thank you for shopping with us.
                """
                .formatted(name, orderId);

        var payload = Map.of(
                "to", normalizePhone(phoneNumber),
                "from", senderId,
                "sms", smsMessage,
                "type", "plain",
                "channel", "generic",
                "api_key", properties.getTermiiApiKey());

        var response = client()
                .post()
                .uri("/api/sms/send")
                .bodyValue(payload)
                .retrieve()
                .onStatus(
                        HttpStatusCode::isError,
                        clientResponse -> getMonoResponse(clientResponse))
                .bodyToMono(TermiiSmsResponse.class)
                .block();

        if (response == null) {
            throw new NotificationException(
                    "Empty response from Termii");
        }

        return response;
    }

    private Mono<? extends Throwable> getMonoResponse(ClientResponse clientResponse) {
        return clientResponse.bodyToMono(String.class)
                .flatMap(errorBody -> getMono(errorBody));
    }

    private Mono<? extends Throwable> getMono(String errorBody) {
        log.error("Termii Error: {}", errorBody);
        return Mono.error(
                new RuntimeException(
                        "Termii API Error: "
                                + errorBody));
    }

    public TermiiSmsResponse sendOtpSms(
            String to,
            String otp,
            String message,
            String name,
            String senderId) {

        String smsMessage = buildMessage(
                otp,
                message,
                name);

        Map<String, Object> payload = Map.of(
                "to", normalizePhone(to),
                "from", senderId,
                "sms", smsMessage,
                "type", "plain",
                "channel", "generic",
                "api_key", properties.getTermiiApiKey());

        TermiiSmsResponse response = client()
                .post()
                .uri("/api/sms/send")
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(TermiiSmsResponse.class)
                .timeout(Duration.ofSeconds(30))
                .block();

        if (response == null) {
            throw new NotificationException(
                    "Empty response from Termii");
        }

        return response;
    }

    private String buildMessage(
            String otp,
            String message,
            String name) {

        if (message != null && !message.isBlank()) {
            return message;
        }

        if (name != null && !name.isBlank()) {

            return String.format(
                    "Hello %s, your OTP is %s. "
                            + "This code expires in 5 minutes. "
                            + "Do not share it with anyone.",
                    name,
                    otp);
        }

        return String.format(
                "Your OTP is %s. "
                        + "This code expires in 5 minutes. "
                        + "Do not share it with anyone.",
                otp);
    }

    public String normalizePhone(String phone) {

        if (phone == null || phone.isBlank()) {
            throw new IllegalArgumentException(
                    "Phone number cannot be empty");
        }

        String normalized = phone
                .replaceAll("[^0-9+]", "")
                .trim();

        if (normalized.startsWith("+234")) {
            normalized = normalized.substring(1);
        } else if (normalized.startsWith("0")) {
            normalized = "234" + normalized.substring(1);
        }

        if (!normalized.matches("^234\\d{10}$")) {
            throw new IllegalArgumentException(
                    "Invalid Nigerian phone number: " + phone);
        }

        return normalized;
    }
}