package com.uchechukwu.store.api_builder_response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

public final class ApiResponseBuilder {

    private ApiResponseBuilder() {
    }

    public static <T> ResponseEntity<ApiResponse<T>> success(
            String message,
            T data) {

        ApiResponse<T> response = new ApiResponse<>(
                true,
                message,
                data);

        return ResponseEntity.ok(response);
    }

    public static <T> ResponseEntity<ApiResponse<T>> created(
            String message,
            String path,
            Object id,
            T data,
            UriComponentsBuilder uriBuilder) {

        ApiResponse<T> response = new ApiResponse<>(
                true,
                message,
                data);

        var uri = uriBuilder
                .path(path)
                .buildAndExpand(id)
                .toUri();

        return ResponseEntity
                .created(uri)
                .body(response);
    }

    public static <T> ResponseEntity<ApiResponse<T>> error(
            String message,

            HttpStatus status) {

        ApiResponse<T> response = new ApiResponse<>(
                false,
                message,
                null);

        return ResponseEntity.status(status)
                .body(response);
    }

    public static <T> ResponseEntity<ApiResponse<T>> badRequest(
            String message) {
        return error(message, HttpStatus.BAD_REQUEST);
    }

    public static <T> ResponseEntity<ApiResponse<T>> deletedResponse(
            String message) {
        return error(message, HttpStatus.OK);
    }


    // public static <T> ResponseEntity<ApiResponse<T>> unauthorized(
    //                 String message) {
    //         return error(message, HttpStatus.UNAUTHORIZED);
    // }

    public static <T> ResponseEntity<ApiResponse<T>> forbidden(
            String message) {
        return error(message, HttpStatus.FORBIDDEN);
    }

    public static <T> ResponseEntity<ApiResponse<T>> notFound(
            String message) {
        return error(message, HttpStatus.NOT_FOUND);
    }

    public static <T> ResponseEntity<ApiResponse<T>> conflict(
            String message) {
        return error(message, HttpStatus.CONFLICT);
    }

    public static <T> ResponseEntity<ApiResponse<T>> unAuthorized() {
        return error("Not Authorized", HttpStatus.UNAUTHORIZED);
    }

    public static <T> ResponseEntity<ApiResponse<T>> internalServerError(
            String message) {
        return error(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}