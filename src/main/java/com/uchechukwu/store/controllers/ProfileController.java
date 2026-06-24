package com.uchechukwu.store.controllers;

import com.uchechukwu.store.api_builder_response.ApiResponse;
import com.uchechukwu.store.dtos.request.ProfileRequest;
import com.uchechukwu.store.dtos.response.ProfilePageResponse;
import com.uchechukwu.store.dtos.response.ProfileResponse;
import com.uchechukwu.store.service.ProfileService;
import com.uchechukwu.store.utilities.rateLimiter.RateLimit;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/api/v1")
@Tag(name = "Profile", description = "Endpoints for profile creation")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService profileService;

    @PostMapping("/profile/create")
    @RateLimit
    public ResponseEntity<ProfileResponse> create(@Valid @RequestBody ProfileRequest request) {
        return profileService.createProfile(request);

    }

    @PatchMapping("/profile/update")
    @RateLimit
    public ResponseEntity<ProfileResponse> update(@Valid @RequestBody ProfileRequest request) {
        return profileService.updateProfile(request);

    }

    @PostMapping("/admin/profile/{userId}/{profileId}/delete")
    @RateLimit
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable UUID userId, @PathVariable UUID profileId) {
        return profileService.adminDeleteProfile(profileId, userId);
    }

    @PostMapping("/admin/profile/{userId}/{profileId}/suspend")
    @RateLimit
    public ResponseEntity<ApiResponse<Void>> suspendUser(@PathVariable UUID userId, @PathVariable UUID profileId) {
        return profileService.adminSuspendProfile(profileId, userId);
    }

    @GetMapping("/profile/get")
    @RateLimit
    public ProfileResponse getProfile() {
        return profileService.getProfile();
    }

    @GetMapping("/admin/profiles/all")
    @RateLimit
    public ProfilePageResponse getAllProfile(@RequestParam(required = false, defaultValue = "", name = "sort") String sort,
                                             @RequestParam(required = false, defaultValue = "0", name = "page") int page,
                                             @RequestParam(required = false, defaultValue = "10", name = "size") int size) {
        return profileService.getAllProfiles(sort, "loyalty_points", page, size);
    }

    @GetMapping("/admin/active/profiles/all")
    @RateLimit
    public ProfilePageResponse getAllActiveProfile(@RequestParam(required = false, defaultValue = "", name = "sort") String sort,
                                                   @RequestParam(required = false, defaultValue = "0", name = "page") int page,
                                                   @RequestParam(required = false, defaultValue = "10", name = "size") int size) {
        return profileService.getAllActiveProfiles(sort, "loyalty_points", page, size);
    }

    @GetMapping("/admin/profile/{userId}/{profileId}/get")
    @RateLimit
    public ProfileResponse getUserProfileForAdmin(@PathVariable UUID userId, @PathVariable UUID profileId) {
        return profileService.getSingleActiveProfile(profileId, userId);
    }


}
