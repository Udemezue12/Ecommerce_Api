package com.uchechukwu.store.mappers;

import com.uchechukwu.store.dtos.request.RiderRequest;
import com.uchechukwu.store.dtos.response.RiderResponse;
import com.uchechukwu.store.entities.Rider;

import java.time.Instant;

public class RiderMapper {

    public static Rider createRider(RiderRequest request, String imageHash) {
        return Rider.builder()
                .lastName(request.lastName())
                .vehiclePlateNumber(request.vehiclePlateNumber())
                .phone(request.phoneNumber())
                .imageUrl(request.imageUrl())
                .imageHash(imageHash)
                .firstName(request.firstName())
                .publicId(request.publicId())
                .createdAt(Instant.now())
                .resourceType(request.resourceType())
                .isAvailable(request.isAvailable())
                .vehicleType(request.vehicleType())
                .build();
    }

    public static RiderResponse response(Rider rider) {
        return new RiderResponse(
                rider.getFirstName(),
                rider.getLastName(),
                rider.getPhone(),
                rider.getVehicleType(),
                rider.getImageUrl(),
                rider.getVehiclePlateNumber(),
                rider.getIsAvailable()
        );

    }
}
