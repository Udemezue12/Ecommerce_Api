package com.uchechukwu.store.core;

import com.uchechukwu.store.entities.User;
import com.uchechukwu.store.repositories.UserRepository;
import com.uchechukwu.store.validators.EntityValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetCurrentUser {

    private final UserRepository userRepo;
    private final EntityValidator validator;

    public User getCurrentUser() {

        var userId = getCurrentUserId();

        return validator.findByIdOrThrow(userRepo, userId, "User");
    }

    public UUID getCurrentUserId() {
        var authentication = SecurityContextHolder.getContext()
                .getAuthentication();

        if (authentication == null
                || !authentication.isAuthenticated()
                || "anonymousUser".equals(
                authentication.getPrincipal())) {

            throw new IllegalStateException("User not authenticated");
        }

        return (UUID) authentication.getPrincipal();
    }

    public UUID getCurrentUserIdOrNull() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null ||
                !authentication.isAuthenticated() ||
                "anonymousUser".equals(authentication.getPrincipal())) {
            return null;
        }

        return (UUID) authentication.getPrincipal();
    }

}