package com.uchechukwu.store.dtos.request;

import com.uchechukwu.store.enums.VehicleType;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateRiderRequest(
        String firstName,
        String lastName,
        @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Invalid phone number format") String phoneNumber,
        VehicleType vehicleType,
        @Size(min = 2, max = 50) String vehiclePlateNumber,
        Boolean isAvailable,
        String imageUrl,
        String publicId,
        String resourceType
) {
}
