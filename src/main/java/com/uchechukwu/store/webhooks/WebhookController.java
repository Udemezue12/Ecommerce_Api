package com.uchechukwu.store.webhooks;

import com.uchechukwu.store.Idempotency.Idempotent;
import com.uchechukwu.store.responses.WebhookRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/webhooks")
@Tag(name = "Payment Webhooks")
@RequiredArgsConstructor
public class WebhookController {

    private final WebhookService webhookService;
    

    @PostMapping("/paystack")
    @Idempotent(ttl = 120)
    public ResponseEntity<Void> handlePaystack(
            @RequestHeader("x-paystack-signature") String signature,
            @RequestBody String rawJson) {

        return webhookService.verifyWebhook("PAYSTACK", signature, rawJson, null);
    }

    @PostMapping("/flutterwave")
    @Idempotent(ttl = 120)
    public ResponseEntity<Void> handleFlutterwave(
            @RequestHeader(value = "verif-hash", required = false) String verifHash,
            @RequestBody String rawJson) {

        return webhookService.verifyWebhook("FLUTTERWAVE", null, rawJson, verifHash);
    }

    @PostMapping("/stripe")
    @Idempotent(ttl = 120)
    public void handleWebhook(
            @RequestHeader Map<String, String> headers, @RequestBody String payload) {

        webhookService.handleWebhookEvent(new WebhookRequest(headers, payload));
    }
}
