package com.uchechukwu.store.repositories;

import com.uchechukwu.store.entities.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<Address, UUID> {
    @EntityGraph(attributePaths = {"user"})
    Optional<Address> findByUserId(UUID userId);

    @Query("SELECT a FROM Address a WHERE (a.user.id = :userId) ")
    List<Address> findAllByUserId(@Param("userId") UUID userId);

    @EntityGraph(attributePaths = {"user"})
    Optional<Address> findByIdAndUserId(UUID addressId, UUID id);

    @EntityGraph(attributePaths = {"user"})
    long countByUserId(UUID id);

    @EntityGraph(attributePaths = {"user"})
    Page<Address> findByUserId(UUID userId, Pageable pageable);

    @EntityGraph(attributePaths = {"user"})
    Optional<Address> findByUserIdAndIsDefaultTrue(UUID userId);

    @Modifying
    @Query("""
                UPDATE Address a
                SET a.isDefault = false
                WHERE a.user.id = :userId
            """)
    void clearDefaultByUserId(UUID userId);
}
