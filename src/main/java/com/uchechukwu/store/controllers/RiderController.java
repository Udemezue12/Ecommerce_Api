package com.uchechukwu.store.controllers;

import com.uchechukwu.store.dtos.request.RiderRequest;
import com.uchechukwu.store.dtos.request.UpdateRiderRequest;
import com.uchechukwu.store.dtos.response.RiderResponse;
import com.uchechukwu.store.responses.ApiResponse;
import com.uchechukwu.store.service.RiderService;
import com.uchechukwu.store.utilities.rateLimiter.RateLimit;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Tag(name = "Rider", description = "Endpoints for riders")
@RequestMapping("/api/v1")
public class RiderController {


    private final RiderService riderService;

    @PostMapping("/rider/create")
    @RateLimit
    public ResponseEntity<ApiResponse<RiderResponse>> createRider(@Valid @RequestBody RiderRequest request) {
        return riderService.createRider(request);
    }

    @PatchMapping("/rider/{riderId}/update")
    @RateLimit
    public ResponseEntity<ApiResponse<RiderResponse>> updateRider(@PathVariable UUID riderId, UpdateRiderRequest request) {
        return riderService.updateRider(riderId, request);
    }

}
