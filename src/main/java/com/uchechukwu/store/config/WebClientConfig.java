package com.uchechukwu.store.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    private final PaymentPropertiesConfig properties; 

    public WebClientConfig(PaymentPropertiesConfig properties) {
        this.properties = properties;
    }

    @Bean(name = "paystackWebClient")
    public WebClient paystackWebClient() {
        return buildClient("https://api.paystack.co", properties.getPaystackSecretKey());
    }

    @Bean(name = "flutterwaveWebClient")
    public WebClient flutterwaveWebClient() {
        return buildClient("https://api.flutterwave.com/v3", properties.getFlutterwaveSecretKey());
    }

    
    private WebClient buildClient(String baseUrl, String secretKey) {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + secretKey)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
