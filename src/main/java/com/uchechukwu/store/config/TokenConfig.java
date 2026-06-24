package com.uchechukwu.store.config;

import com.uchechukwu.store.configProperties.AuthProperties;
import com.uchechukwu.store.core.GetSecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;


@Configuration
@RequiredArgsConstructor
public class TokenConfig {
    private final AuthProperties properties;

    @Bean("verifySecretKey")
    public SecretKey verifySecretKey() {
        return GetSecretKey.getKeys(properties.getVerifyEmailSecretKey());
    }

    @Bean("resetSecretKey")
    public SecretKey resetSecretKey() {
        return GetSecretKey.getKeys(properties.getResetSecretKey());
    }


}
