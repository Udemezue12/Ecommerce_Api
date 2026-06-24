package com.uchechukwu.store.config;

import com.uchechukwu.store.configProperties.NotificationProperties;
import com.uchechukwu.store.configProperties.PaymentPropertiesConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
public class WebClientConfig {

    private final PaymentPropertiesConfig properties;
    private final NotificationProperties notificationProperties;


    @Bean(name = "paystackWebClient")
    public WebClient paystackWebClient() {
        return buildClient("https://api.paystack.co", properties.getPaystackSecretKey());
    }

    @Bean(name = "flutterwaveWebClient")
    public WebClient flutterwaveWebClient() {
        return buildClient("https://api.flutterwave.com/v3", properties.getFlutterwaveSecretKey());
    }

    @Bean(name = "termiiWebClient")
    public WebClient termiiWebClient() {

        return WebClient.builder()
                .baseUrl(notificationProperties.getTermiiBaseUrl())
                .defaultHeader(
                        HttpHeaders.CONTENT_TYPE,
                        MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Bean(name = "brevoWebClient")
    public WebClient client() {

        return WebClient.builder()
                .baseUrl(notificationProperties.getBrevoUrl())
                .defaultHeader(
                        "api-key",
                        notificationProperties.getBrevoApiKey())
                .defaultHeader(
                        HttpHeaders.CONTENT_TYPE,
                        MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(
                        HttpHeaders.ACCEPT,
                        MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    private WebClient buildClient(String baseUrl, String secretKey) {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + secretKey)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
