package com.uchechukwu.store.dtos.response;

public record AddressResponse(
        String name,
        String email,
        String phoneNumber,
        String street,
        String city,
        String localGovernment,
        String zip,
        String state

) {
}