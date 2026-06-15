package com.uchechukwu.store.enums;

public enum PaymentMethod {

    PAYSTACK("PAYSTACK"),
    STRIPE("STRIPE"),
    FLUTTERWAVE("FLUTTERWAVE");

    private final String prefix;

    PaymentMethod(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }
}
