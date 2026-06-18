package com.uchechukwu.store.repositories;

import com.uchechukwu.store.entities.Profile;
import com.uchechukwu.store.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, UUID> {
    @EntityGraph(attributePaths = {"user"})
    Optional<Profile> findByUserId(UUID userId);

    @EntityGraph(attributePaths = {"user"})
    Optional<Profile> findByIdOrUserIdAndDeletedFalse(UUID id, UUID userId);

    @EntityGraph(attributePaths = {"user"})
    Optional<Profile> findByUserIdAndDeletedFalse(UUID userId);

    @EntityGraph(attributePaths = {"user"})
    Page<Profile> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"user"})
    Page<Profile> findAllByDeletedFalse(Pageable pageable);

    @EntityGraph(attributePaths = {"user"})
    @Query("SELECT p FROM Profile p WHERE (p.id = :profileId OR p.user.id = :userId) AND p.deleted = false")
    Optional<Profile> findActiveProfileByEitherId(@Param("profileId") UUID profileId, @Param("userId") UUID userId);

    @EntityGraph(attributePaths = {"user"})
    long countByUserIdAndDeletedAtGreaterThanEqualAndDeletedAtLessThan(
            UUID userId,
            LocalDateTime start,
            LocalDateTime end
    );

    @EntityGraph(attributePaths = {"user"})
    Optional<Profile> findByUserAndDeletedFalse(User user);
}