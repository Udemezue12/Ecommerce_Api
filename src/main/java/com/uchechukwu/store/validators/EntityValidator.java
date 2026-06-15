package com.uchechukwu.store.validators;

import com.uchechukwu.store.exceptions.BadRequestException;
import com.uchechukwu.store.exceptions.DuplicateResourceException;
import com.uchechukwu.store.exceptions.ResourceNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Function;

@Component
public class EntityValidator {
    public <ID> void validateExists(JpaRepository<?, ID> repository, ID Id, String entityName) {
        if (!repository.existsById(Id)) {
            throw new ResourceNotFoundException(
                    entityName + " not found"

            );
        }
    }


    public <T, ID> T findByIdOrThrow(
            JpaRepository<T, ID> repository,
            ID id,
            String entityName) {

        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        entityName + " not found"));
    }

    public <T, ID> T findByIdOrThrowBadRequest(
            JpaRepository<T, ID> repository,
            ID id,
            String entityName) {

        return repository.findById(id)
                .orElseThrow(() -> new BadRequestException(
                        entityName + " Not Found"));
    }

    public <T, ID> T findByIdOrReturnNull(
            JpaRepository<T, ID> repository,
            ID id,
            String entityName) {

        T result = repository.findById(id).orElse(null);

        if (result == null) {
            throw new BadRequestException(entityName + " Bad Request");
        }

        return result;
    }

    public <T> void validateUniqueField(
            String newValue, String oldValue, Function<String, Optional<T>> finder, String errorMessage) {
        if (newValue == null) {
            return;
        }

        String normalized = newValue.trim().toLowerCase();

        if (!oldValue.equalsIgnoreCase(normalized)
                && finder.apply(normalized).isPresent()) {

            throw new DuplicateResourceException(errorMessage);
        }
    }
}