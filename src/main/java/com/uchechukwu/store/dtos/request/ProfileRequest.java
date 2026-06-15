package com.uchechukwu.store.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record ProfileRequest(
        @NotBlank(message = "This cannot be blank")
        @Size(min = 2, max = 255) String bio,
        @NotNull(message = "Date of Birth is required")
        LocalDate dateOfBirth,
        String imageUrl,
        String publicId,
        String resourceType

) {
}