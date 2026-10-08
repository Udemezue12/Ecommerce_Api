package com.uchechukwu.store.swagger;

import org.springframework.security.authentication.ProviderManager;

public record SwaggerFilterChainResponse(
        ProviderManager providerManager,
        String role
) {
}
