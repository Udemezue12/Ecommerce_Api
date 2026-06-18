package com.uchechukwu.store.service;

import com.uchechukwu.store.core.ComputeFileHash;
import com.uchechukwu.store.core.GetCurrentUser;
import com.uchechukwu.store.customCache.CustomCacheEvict;
import com.uchechukwu.store.customCache.CustomCacheable;
import com.uchechukwu.store.dtos.request.ProfileRequest;
import com.uchechukwu.store.dtos.response.ProfilePageResponse;
import com.uchechukwu.store.dtos.response.ProfileResponse;
import com.uchechukwu.store.entities.Profile;
import com.uchechukwu.store.entities.User;
import com.uchechukwu.store.events.SingleImageDeleteEvent;
import com.uchechukwu.store.exceptions.AccountDeletedException;
import com.uchechukwu.store.exceptions.AccountSuspendedException;
import com.uchechukwu.store.exceptions.DuplicateResourceException;
import com.uchechukwu.store.exceptions.ResourceNotFoundException;
import com.uchechukwu.store.mappers.ProfileMapper;
import com.uchechukwu.store.repositories.ProfileRepository;
import com.uchechukwu.store.responses.ApiResponse;
import com.uchechukwu.store.responses.ApiResponseBuilder;
import com.uchechukwu.store.validators.ValidatedSortedData;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepo;
    private final GetCurrentUser getCurrentUser;
    private final ValidatedSortedData validatedSortedData;
    private final ComputeFileHash computeFileHash;
    private final ApplicationEventPublisher applicationEventPublisher;


    @Transactional
    @CustomCacheEvict(cacheNames = {
            "all-profiles", "all-active-profiles"})
    public ResponseEntity<ProfileResponse> createProfile(ProfileRequest request) {
        var user = getCurrentUser.getCurrentUser();
        if (user.isDeleted()) {
            throw new AccountDeletedException(
                    "Account has been deleted");
        }

        if (user.isSuspended()) {
            throw new AccountSuspendedException(
                    "Account has been suspended");
        }

        profileRepo.findByUserAndDeletedFalse(user)
                .ifPresent(profile -> {
                    throw new DuplicateResourceException(
                            "Profile already exists");
                });


        var fileHash = computeFileHash.computeFileHashAsync(request.imageUrl());
        var profile = ProfileMapper.createEntity(request, user, fileHash.toString());

        var result = profileRepo.save(profile);
        var savedProfile = ProfileMapper.response(result);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProfile);
    }

    @Transactional
    @CustomCacheEvict(cacheNames = {
            "all-profiles", "all-active-profiles", "single-profile"})
    public ResponseEntity<ProfileResponse> updateProfile(ProfileRequest request) {
        var user = getCurrentUser.getCurrentUser();
        var profile = getActiveProfile(user);
        if (request.bio() != null) {
            profile.setBio(request.bio());
        }
        if (request.imageUrl() != null) {
            applicationEventPublisher.publishEvent(new SingleImageDeleteEvent(profile.getId(), profile.getPublicId(), profile.getResourceType()));

            var imageHash = computeFileHash.computeFileHashAsync(request.imageUrl());
            profile.setImageHash(imageHash.toString());
            profile.setImageUrl(request.imageUrl());
            profile.setPublicId(request.publicId());
            profile.setResourceType(request.resourceType());


        }
        if (request.dateOfBirth() != null) {
            profile.setDateOfBirth(request.dateOfBirth());
        }
        profileRepo.save(profile);
        return ResponseEntity.status(HttpStatus.OK).body(ProfileMapper.response(profile));
    }

    @Transactional(readOnly = true)
    @CustomCacheable(value = "single-profile", key = "@getCurrentUser.getCurrentUser().id")
    public ProfileResponse getProfile() {
        var user = getCurrentUser.getCurrentUser();
        var profile = getActiveProfile(user);
        return ProfileMapper.response(profile);
    }

    @CustomCacheable(value = "all-profiles", key = "#sort + '-' + #sortingValue1 + '-' + #page + '-' + #size")
    @Transactional(readOnly = true)
    public ProfilePageResponse getAllProfiles(String sort, String sortingValue1, int page, int size) {
        var pageable = validatedSortedData.getValidatedPageableData(sort, sortingValue1, page, size);
        var result = profileRepo.findAll(pageable)
                .map(ProfileMapper::response);
        return new ProfilePageResponse(
                result.getContent(),
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages(),
                result.isFirst(),
                result.isLast()
        );
    }

    @CustomCacheable(value = "all-active-profiles", key = "#sort + '-' + #sortingValue1 + '-' + #page + '-' + #size")
    @Transactional(readOnly = true)
    public ProfilePageResponse getAllActiveProfiles(String sort, String sortingValue1, int page, int size) {
        var pageable = validatedSortedData.getValidatedPageableData(sort, sortingValue1, page, size);
        var result = profileRepo.findAllByDeletedFalse(pageable)
                .map(ProfileMapper::response);
        return new ProfilePageResponse(
                result.getContent(),
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages(),
                result.isFirst(),
                result.isLast()
        );

    }

    @Transactional
    @CustomCacheEvict(cacheNames = {
            "all-profiles", "all-active-profiles", "single-profile"})
    public ResponseEntity<ApiResponse<Void>> adminDeleteProfile(UUID profileId, UUID userId) {
        var profile = getAdminActiveProfile(profileId, userId);
        ProfileMapper.deleteEntity(profile);
        profileRepo.save(profile);
        return ApiResponseBuilder.deletedResponse("Profile Deleted");
    }

    @Transactional
    @CustomCacheEvict(cacheNames = {
            "all-profiles", "all-active-profiles", "single-profile"})
    public ResponseEntity<ApiResponse<Void>> adminSuspendProfile(UUID profileId, UUID userId) {
        var profile = getAdminActiveProfile(profileId, userId);
        ProfileMapper.suspendEntity(profile);
        profileRepo.save(profile);
        return ApiResponseBuilder.deletedResponse("Profile Suspended");
    }

    @Transactional(readOnly = true)
    @CustomCacheable(value = "single-profile", key = "'id-' + #profileId + '-user-' + #userId")
    public ProfileResponse getSingleActiveProfile(UUID profileId, UUID userId) {
        var profile = getAdminActiveProfile(profileId, userId);
        return ProfileMapper.response(profile);
    }

    private Profile getAdminActiveProfile(UUID profileId, UUID userId) {
        return profileRepo.findByIdOrUserIdAndDeletedFalse(profileId, userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Active profile not found for the provided criteria."));
    }

    private Profile getActiveProfile(User user) {
        return profileRepo.findByUserIdAndDeletedFalse(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Not Authorized or Account has been suspended"));
    }
}