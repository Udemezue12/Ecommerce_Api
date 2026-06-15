package com.uchechukwu.store.email.brevo;

import com.uchechukwu.store.config.NotificationProperties;
import com.uchechukwu.store.core.NotificationCircuitBreaker;
import com.uchechukwu.store.exceptions.NotificationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class BrevoClient {

    private final NotificationProperties properties;
    private final NotificationCircuitBreaker breaker;


    private WebClient client() {

        return WebClient.builder()
                .baseUrl(properties.getBrevoUrl())
                .defaultHeader(
                        "api-key",
                        properties.getBrevoApiKey())
                .defaultHeader(
                        HttpHeaders.CONTENT_TYPE,
                        MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(
                        HttpHeaders.ACCEPT,
                        MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    private BrevoEmailResponse sendEmail(
            BrevoEmailRequest request) {

        var response = client()
                .post()
                .uri("")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(BrevoEmailResponse.class)
                .timeout(Duration.ofSeconds(30))
                .block();

        if (response == null) {
            throw new NotificationException(
                    "Empty response from Brevo");
        }

        return response;
    }

    public void sendBrevoEmail(
            String email,
            String name,
            String subject,
            String html,
            String text) {

        breaker.execute(() -> {

            var request = BrevoEmailRequest.builder()
                    .sender(
                            BrevoEmailRequest.Sender.builder()
                                    .name("Support")
                                    .email(
                                            properties.getEmailUsername())
                                    .build())
                    .to(List.of(
                            BrevoEmailRequest.Recipient
                                    .builder()
                                    .email(email)
                                    .name(name)
                                    .build()))
                    .subject(subject)
                    .htmlContent(html)
                    .textContent(text)
                    .build();

            sendEmail(request);

            return true;
        });
    }
}
