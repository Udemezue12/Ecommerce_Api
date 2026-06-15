package com.uchechukwu.store.service;


import com.uchechukwu.store.core.ComputeFileHash;
import com.uchechukwu.store.dtos.request.RiderRequest;
import com.uchechukwu.store.dtos.request.UpdateRiderRequest;
import com.uchechukwu.store.dtos.response.RiderResponse;
import com.uchechukwu.store.entities.Rider;
import com.uchechukwu.store.exceptions.ResourceNotFoundException;
import com.uchechukwu.store.mappers.RiderMapper;
import com.uchechukwu.store.repositories.RiderRepository;
import com.uchechukwu.store.responses.ApiResponse;
import com.uchechukwu.store.responses.ApiResponseBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RiderService {
    private final RiderRepository riderRepo;
    private final ComputeFileHash computeFileHash;

    @Transactional
    public ResponseEntity<ApiResponse<RiderResponse>> createRider(RiderRequest request) {
        var imageHash = computeFileHash.computeFileHashAsync(request.imageUrl());
        var rider = RiderMapper.createRider(request, imageHash.toString());
        var savedRider = riderRepo.save(rider);
        var riderMapper = RiderMapper.response(savedRider);
        return ApiResponseBuilder.success("Created Successfully", riderMapper);

    }

    @Transactional
    public ResponseEntity<ApiResponse<RiderResponse>> updateRider(UUID riderId, UpdateRiderRequest request) {
        var rider = getRider(riderId);
        if (request.firstName() != null) {
            rider.setFirstName(request.firstName());
        }
        if (request.lastName() != null) {
            rider.setLastName(request.lastName());

        }
        if (request.phoneNumber() != null) {
            rider.setPhone(request.phoneNumber());
        }
        if (request.imageUrl() != null) {
            var imageHash = computeFileHash.computeFileHashAsync(request.imageUrl());
            rider.setImageHash(imageHash.toString());
            rider.setPublicId(request.publicId());
            rider.setImageUrl(request.imageUrl());
            rider.setResourceType(request.resourceType());
        }

        if (request.vehicleType() != null) {
            rider.setVehicleType(request.vehicleType());
        }
        if (request.vehiclePlateNumber() != null) {
            rider.setVehiclePlateNumber(request.vehiclePlateNumber());
        }
        rider.setUpdatedAt(Instant.now());
        var savedRider = riderRepo.save(rider);
        var riderMapper = RiderMapper.response(savedRider);
        return ApiResponseBuilder.success("Updated Successfully", riderMapper);

    }

    @Transactional(readOnly = true)
    public Rider getRider(UUID riderId) {
        return riderRepo.findById(riderId).orElseThrow(() -> new ResourceNotFoundException(("Rider does not exist in the database")));
    }
}
