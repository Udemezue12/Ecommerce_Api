package com.uchechukwu.store.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "cors")
public class CorsConfig {
    private List<String> allowedOrigins;

    @Bean
    public CorsConfigurationSource corsConfigSource() {
        var config = new CorsConfiguration();
        config.setAllowedOrigins(allowedOrigins);
        config.setAllowedMethods(List.of(
                "GET",
                "POST",
                "PUT",
                "PATCH",
                "DELETE",
                "OPTIONS"));
        config.setAllowedHeaders(List.of(
                HttpHeaders.AUTHORIZATION,
                HttpHeaders.CONTENT_TYPE,
                HttpHeaders.ACCEPT,
                "X-Requested-With",
                "X-XSRF-TOKEN", "Idempotency-Key"));

        config.setExposedHeaders(List.of(
                HttpHeaders.AUTHORIZATION,
                "Set-Cookie"));

        config.setAllowCredentials(true);

        config.setMaxAge(3600L);

        var source = new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", config);

        return source;

    }

}
