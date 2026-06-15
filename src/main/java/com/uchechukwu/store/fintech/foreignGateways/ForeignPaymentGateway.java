package com.uchechukwu.store.fintech.foreignGateways;

import com.uchechukwu.store.entities.Order;
import com.uchechukwu.store.responses.WebhookRequest;

import java.util.Optional;

public interface ForeignPaymentGateway {
    CheckOutSession createCheckSession(Order order);

    Optional<WebhookPaymentStatus> parseWebhookRequest(WebhookRequest request);
}
