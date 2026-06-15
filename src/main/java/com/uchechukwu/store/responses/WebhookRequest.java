package com.uchechukwu.store.responses;

import java.util.Map;

public record WebhookRequest(
        Map<String, String> signature,
        String payload
) {
}
