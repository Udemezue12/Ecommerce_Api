package com.uchechukwu.store.dtos.request;

import java.time.LocalDate;


public record UpdateProfileRequest(
        String bio,
        LocalDate dateOfBirth,
        String imageUrl,
        String publicId,
        String resourceType

) {
}
