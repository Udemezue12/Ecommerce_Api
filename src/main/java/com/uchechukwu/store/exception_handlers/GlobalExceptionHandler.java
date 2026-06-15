package com.uchechukwu.store.exception_handlers;

import com.uchechukwu.store.exceptions.*;
import com.uchechukwu.store.responses.ApiResponse;
import com.uchechukwu.store.responses.ApiResponseBuilder;
import jakarta.persistence.PessimisticLockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleNotFound(
            ResourceNotFoundException ex) {

        return ApiResponseBuilder.notFound(
                ex.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse<Object>> handleBadCredentials(
            BadCredentialsException ex) {

        return ApiResponseBuilder.unAuthorized();
    }

    @ExceptionHandler({
            PessimisticLockException.class,
            CannotAcquireLockException.class
    })
    public ResponseEntity<?> handleLockExceptions(
            Exception e
    ) {

        return ResponseEntity.status(409).body(
                Map.of(
                        "message",
                        "Product is currently being purchased. Please retry."
                )
        );
    }


    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse<Object>> handleBadRequest(
            BadRequestException ex) {

        return ApiResponseBuilder.badRequest(
                ex.getMessage());
    }

    @ExceptionHandler(AppException.class)
    public ResponseEntity<?> handleAppException(AppException ex) {
        return ResponseEntity
                .badRequest()
                .body(Map.of(
                        "success", false,
                        "message", ex.getMessage()));
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ApiResponse<Void>> handleDuplicate(
            DuplicateResourceException ex) {

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(
                        new ApiResponse<>(
                                false,
                                ex.getMessage(),
                                null));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException exception) {
        var errors = new HashMap<String, String>();
        exception.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleUnReadableMessage() {

        return ResponseEntity.badRequest().body(Map.of("message", "Invalid request body"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleException(Exception ex) {

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        new ApiResponse<>(
                                false,
                                ex.getMessage(),
                                null));
    }

    @ExceptionHandler(RateLimitExceededException.class)
    public ResponseEntity<?> handleRateLimit(
            RateLimitExceededException ex) {

        return ResponseEntity
                .status(HttpStatus.TOO_MANY_REQUESTS)
                .body(
                        Map.of(
                                "detail",
                                ex.getMessage()));
    }

    @ExceptionHandler(PaymentException.class)
    public ResponseEntity<?> handlePayment(PaymentException ex) {

        Throwable root = ex.getCause();

        if (root != null) {
            log.error("Underlying payment provider error", root);
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of(
                        "error", "Payment processing failed"
                ));
    }
}
