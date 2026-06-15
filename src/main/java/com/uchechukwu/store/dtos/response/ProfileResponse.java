package com.uchechukwu.store.dtos.response;

import java.time.LocalDate;
import java.util.UUID;

public record ProfileResponse(
        UUID userId,
        String name,
        String email,
        String phoneNumber,
        Boolean verified,
        UUID profileId,
        String bio,
        LocalDate dateOfBirth,
        Integer loyaltyPoints,
        String imageUrl
) {
}

