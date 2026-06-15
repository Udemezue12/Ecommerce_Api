package com.uchechukwu.store.fintech.africanGateways;

import com.fasterxml.jackson.annotation.JsonProperty;

public record FlutterwaveInitializeData(
        @JsonProperty("link") String link
) {
}
