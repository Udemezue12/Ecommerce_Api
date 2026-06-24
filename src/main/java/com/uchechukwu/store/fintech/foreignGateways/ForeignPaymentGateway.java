package com.uchechukwu.store.fintech.foreignGateways;

import com.uchechukwu.store.dtos.request.WebhookRequest;
import com.uchechukwu.store.entities.Order;

import java.util.Optional;

public interface ForeignPaymentGateway {
    CheckOutSession createCheckSession(Order order);

    Optional<WebhookPaymentStatus> parseWebhookRequest(WebhookRequest request);
}
