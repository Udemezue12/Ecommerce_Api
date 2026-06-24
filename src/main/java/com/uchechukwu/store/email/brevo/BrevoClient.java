package com.uchechukwu.store.email.brevo;

import com.uchechukwu.store.configProperties.NotificationProperties;
import com.uchechukwu.store.core.NotificationCircuitBreaker;
import com.uchechukwu.store.exceptions.NotificationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.List;

@Slf4j
@Component

public class BrevoClient {

    private final NotificationProperties properties;
    private final NotificationCircuitBreaker breaker;
    private final WebClient client;

    public BrevoClient(NotificationProperties properties, NotificationCircuitBreaker breaker, @Qualifier("brevoWebClient") WebClient client) {
        this.properties = properties;
        this.breaker = breaker;
        this.client = client;
    }


    private void sendEmail(
            BrevoEmailRequest request) {

        var response = client
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
                                    .name(properties.getBrevoAppName())
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
