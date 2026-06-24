package com.uchechukwu.store.fintech.foreignGateways;

import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionCreateParams;
import com.uchechukwu.store.dtos.request.WebhookRequest;
import com.uchechukwu.store.entities.Order;
import com.uchechukwu.store.entities.OrderItem;
import com.uchechukwu.store.enums.OrderStatus;
import com.uchechukwu.store.exceptions.PaymentException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class StripePaymentGateway implements ForeignPaymentGateway {
    @Value("${notification.frontend-url}")
    private String frontendUrl;
    @Value("${stripe.webhookSecretKey}")
    private String webhookSecretKey;

    @Override
    public CheckOutSession createCheckSession(Order order) {

        try {
            var sessionBuilder = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl(frontendUrl + "/checkout-success.html?orderId=" + order.getId())
                    .setCancelUrl(frontendUrl + "/checkout-cancel.html")
                    .setPaymentIntentData(SessionCreateParams.PaymentIntentData.builder().putMetadata("order_id", order.getId().toString()).build());
            order.getItems().forEach(item -> {
                sessionBuilder.addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(Long.valueOf(item.getQuantity()))
                                .setPriceData(
                                        getLineData(item)
                                )
                                .build()
                );
            });
            var session = Session.create(sessionBuilder.build());
            return new CheckOutSession(session.getUrl(), session.getClientReferenceId()
            );
        } catch (StripeException e) {
            log.error("Stripe checkout failed", e);
            throw new PaymentException("Stripe checkout failed", e);

        }

    }

    @Override
    public Optional<WebhookPaymentStatus> parseWebhookRequest(WebhookRequest request) {
        try {
            var payload = request.payload();
            var signature = request.signature().get("Stripe-signature");
            var event = Webhook.constructEvent(payload, signature, webhookSecretKey);

            return switch (event.getType()) {
                case "payment_intent.succeeded" ->
                        Optional.of(new WebhookPaymentStatus(extractOrderId(event), OrderStatus.PAID));
                case "payment_intent.payment_failed" ->
                        Optional.of(new WebhookPaymentStatus(extractOrderId(event), OrderStatus.FAILED));
                default -> Optional.empty();
            };

        } catch (SignatureVerificationException e) {
            throw new PaymentException("Invalid Signature");
        }
    }

    private UUID extractOrderId(Event event) {
        var stripeObject = event.getDataObjectDeserializer().getObject().orElseThrow(() -> new PaymentException("Cannot Deserialize this, check API Version"));
        var paymentIntent = (PaymentIntent) stripeObject;
        return UUID.fromString(paymentIntent.getMetadata().get("order_id"));
    }

    private SessionCreateParams.LineItem.PriceData getLineData(OrderItem item) {
        return SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency("usd")
                .setUnitAmount(
                        item.getUnitPrice()
                                .multiply(BigDecimal.valueOf(100))
                                .longValue()
                )
                .setProductData(
                        getProductData(item)
                )
                .build();
    }

    private SessionCreateParams.LineItem.PriceData.ProductData getProductData(OrderItem item) {
        return SessionCreateParams.LineItem.PriceData.ProductData.builder()
                .setName(item.getProduct().getName())
                .build();
    }
}
