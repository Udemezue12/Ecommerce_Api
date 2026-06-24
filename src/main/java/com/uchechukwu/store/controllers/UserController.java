package com.uchechukwu.store.controllers;

import com.uchechukwu.store.api_builder_response.ApiResponse;
import com.uchechukwu.store.dtos.request.ChangePasswordRequestDto;
import com.uchechukwu.store.dtos.request.UserPatchRequestDto;
import com.uchechukwu.store.dtos.request.UserUpdateRequestDto;
import com.uchechukwu.store.dtos.response.UserResponseDto;
import com.uchechukwu.store.interfaces.UserSummary;
import com.uchechukwu.store.service.UserService;
import com.uchechukwu.store.utilities.rateLimiter.RateLimit;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
@Tag(name = "User", description = "Endpoints for user management")
public class UserController {
    private final UserService userService;


    @GetMapping("/admin/users")
    @RateLimit
    public Iterable<UserResponseDto> getAllUsers(
            @RequestParam(required = false, defaultValue = "", name = "sort") String sort) {
        return userService.getAllUsers(sort);

    }

    @GetMapping("/admin/users/summaries")
    @RateLimit
    public List<UserSummary> getAllUserSummaries(@RequestParam String sort) {
        return userService.getAllUserSummaries(sort);
    }


    @GetMapping("/admin/user/{id}")
    @RateLimit

    public ResponseEntity<ApiResponse<UserResponseDto>> getUserById(@PathVariable UUID id) {
        return userService.getUserById(id);

    }

    @GetMapping("/admin/user/summary/{id}")
    @RateLimit
    public ResponseEntity<ApiResponse<UserSummary>> findSummaryId(@PathVariable UUID id) {
        return userService.findSummaryId(id);
    }


    @PutMapping("/user/update")
    @RateLimit
    public ResponseEntity<ApiResponse<UserResponseDto>> updateUser(
            @Valid @RequestBody UserUpdateRequestDto userUpdateRequest) {
        return userService.updateUser(userUpdateRequest);

    }

    @PatchMapping("/user/partial/update")

    public ResponseEntity<ApiResponse<UserResponseDto>> partialUpdateUser(@Valid @RequestBody UserPatchRequestDto userPatchDto) {
        return userService.partialUpdateUser(userPatchDto);

    }


    @PostMapping("/user/change-password")
    @RateLimit
    public ResponseEntity<ApiResponse<Void>> changePassword(@Valid @RequestBody ChangePasswordRequestDto request) {
        return userService.changePassword(request);
    }


}
