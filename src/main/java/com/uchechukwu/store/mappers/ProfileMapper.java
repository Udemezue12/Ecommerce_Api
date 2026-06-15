package com.uchechukwu.store.mappers;


import com.uchechukwu.store.dtos.request.ProfileRequest;
import com.uchechukwu.store.dtos.response.ProfileResponse;
import com.uchechukwu.store.entities.Profile;
import com.uchechukwu.store.entities.User;

import java.time.LocalDateTime;

public class ProfileMapper {

    public static ProfileResponse response(Profile profile) {
        var getUser = profile.getUser();
        return new ProfileResponse(getUser.getId(), getUser.getName(), getUser.getEmail(), getUser.getPhoneNumber(), getUser.getVerified(), profile.getId(), profile.getBio(), profile.getDateOfBirth(), profile.getLoyaltyPoints(), profile.getImageUrl());

    }

    public static Profile createEntity(ProfileRequest request, User user, String imageHash) {

        return Profile.builder()
                .bio(request.bio())
                .dateOfBirth(request.dateOfBirth())
                .user(user)
                .deleted(false)
                .imageHash(imageHash)
                .resourceType(request.resourceType())
                .imageUrl(request.imageUrl())
                .publicId(request.publicId())
                .loyaltyPoints(1)
                .build();

    }

    public static void updateEntity(Profile profile, ProfileRequest request) {

        profile.setBio(request.bio());
        profile.setDateOfBirth(request.dateOfBirth());

    }

    public static void deleteEntity(Profile profile) {
        profile.setDeleted(true);
        profile.setDeletedAt(LocalDateTime.now());
    }
}
