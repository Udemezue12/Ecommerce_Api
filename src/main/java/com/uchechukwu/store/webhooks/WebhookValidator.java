package com.uchechukwu.store.webhooks;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class WebhookValidator {

    private static final String HMAC_SHA512 = "HmacSHA512";

    public static boolean isValid(String jsonPayload, String requestSignature, String secretKey) {
        if (requestSignature == null || jsonPayload == null) {
            return false;
        }
        try {
            var sha512Hmac = Mac.getInstance(HMAC_SHA512);
            var secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), HMAC_SHA512);
            sha512Hmac.init(secretKeySpec);

            byte[] macData = sha512Hmac.doFinal(jsonPayload.getBytes(StandardCharsets.UTF_8));

            // Convert byte array to Hex String
            var result = new StringBuilder();
            for (byte b : macData) {
                result.append(String.format("%02x", b));
            }

            // Use MessageDigest.isEqual to prevent timing attacks
            return MessageDigest.isEqual(
                    result.toString().getBytes(StandardCharsets.UTF_8),
                    requestSignature.getBytes(StandardCharsets.UTF_8)
            );
        } catch (Exception e) {
            return false;
        }
    }
}
