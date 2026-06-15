package com.uchechukwu.store.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AddressRequest(
        @NotBlank(message = "Zip is required")
        @Size(min = 1, max = 9)
        String zip,
        @NotBlank(message = "State is required")
        String state,
        @NotBlank(message = "City is required")
        String city,
        @NotBlank(message = "Local Government is required")
        String localGovernment,
        @NotBlank(message = "Default is required")
        Boolean setDefault,
        @NotBlank(message = "Street is required")
        @Size(max = 256)
        String street


) {
}
