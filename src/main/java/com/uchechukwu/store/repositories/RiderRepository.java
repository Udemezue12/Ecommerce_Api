package com.uchechukwu.store.repositories;

import com.uchechukwu.store.entities.Rider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RiderRepository extends JpaRepository<Rider, UUID> {
}
