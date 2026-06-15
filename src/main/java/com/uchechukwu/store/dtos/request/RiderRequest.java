package com.uchechukwu.store.dtos.request;


import com.uchechukwu.store.enums.VehicleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RiderRequest(
        @NotBlank(message = "Name is required") @Size(min = 2, max = 255) String firstName,
        @NotBlank(message = "Name is required") @Size(min = 2, max = 255) String lastName,
        @NotBlank(message = "Phone number is required") @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Invalid phone number format") String phoneNumber,
        @NotNull(message = "Role is required") VehicleType vehicleType,
        @NotBlank(message = "Name is required") @Size(min = 2, max = 50) String vehiclePlateNumber,
        @NotNull(message = "Role is required") Boolean isAvailable,
        String imageUrl,
        String publicId,
        String resourceType


) {
}
