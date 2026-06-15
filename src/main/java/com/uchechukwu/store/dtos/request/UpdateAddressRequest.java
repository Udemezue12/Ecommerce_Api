package com.uchechukwu.store.dtos.request;


import jakarta.validation.constraints.Size;

public record UpdateAddressRequest(
        @Size(min = 1, max = 9)
        String zip,

        String state,

        String city,

        String localGovernment,

        @Size(max = 256)
        String street,
        Boolean isDefault


) {

}

