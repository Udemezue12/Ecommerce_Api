package com.uchechukwu.store.fintech.africanGateways;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.uchechukwu.store.serializers.StatusDeserializer;

import lombok.Data;

@Data
public class PaymentApiResponse<T> {
    @JsonDeserialize(using = StatusDeserializer.class)
    private boolean status;
    private String message;
    private T data;
}

