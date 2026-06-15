package com.uchechukwu.store.dtos.response;

import com.uchechukwu.store.enums.VehicleType;

public record RiderResponse(
        String firstName,
        String lastName,
        String phoneNumber,
        VehicleType vehicleType,
        String imageUrl,
        String vehiclePlateNumber,
        Boolean isAvailable
) {
}
