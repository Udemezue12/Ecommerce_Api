package com.uchechukwu.store.repositories;

import com.uchechukwu.store.entities.User;
import com.uchechukwu.store.interfaces.UserSummary;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    List<UserSummary> findAllProjectedBy(Sort sort);

    Optional<UserSummary> findProjectedById(UUID id);

    Optional<UserSummary> findProjectedByEmail(String email);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<User> findByEmailIgnoreCase(String email);

}
