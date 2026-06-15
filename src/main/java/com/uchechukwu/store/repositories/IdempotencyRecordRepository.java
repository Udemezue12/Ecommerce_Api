package com.uchechukwu.store.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.uchechukwu.store.entities.IdempotencyRecord;

@Repository
public interface IdempotencyRecordRepository extends JpaRepository<IdempotencyRecord, UUID> {
     @Query("SELECT d FROM IdempotencyRecord d WHERE d.idempotencyKey = :idempotencyKey")
     Optional<IdempotencyRecord> findByIdempotencyKey(@Param("idempotencyKey") UUID idempotencyKey);

}
