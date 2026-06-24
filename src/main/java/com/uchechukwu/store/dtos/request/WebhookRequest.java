package com.uchechukwu.store.dtos.request;

import java.util.Map;

public record WebhookRequest(
        Map<String, String> signature,
        String payload
) {
}
