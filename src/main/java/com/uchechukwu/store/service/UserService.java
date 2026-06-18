package com.uchechukwu.store.service;

import com.uchechukwu.store.core.GetCurrentUser;
import com.uchechukwu.store.dtos.request.ChangePasswordRequestDto;
import com.uchechukwu.store.dtos.request.UserPatchRequestDto;
import com.uchechukwu.store.dtos.request.UserUpdateRequestDto;
import com.uchechukwu.store.dtos.response.UserResponseDto;
import com.uchechukwu.store.interfaces.UserSummary;
import com.uchechukwu.store.mappers.UserMapper;
import com.uchechukwu.store.repositories.UserRepository;
import com.uchechukwu.store.responses.ApiResponse;
import com.uchechukwu.store.responses.ApiResponseBuilder;
import com.uchechukwu.store.sortingAndPaginating.SortAndPaginate;
import com.uchechukwu.store.validators.EntityValidator;
import com.uchechukwu.store.validators.RequestValidators;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepo;
    private final UserRepository userRepository;

    private final SortAndPaginate sortAndPaginate;
    private final RequestValidators requestValidate;
    private final UserMapper userMapper;
    private final EntityValidator entityValidator;
    private final GetCurrentUser getCurrentUser;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var user = userRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not Found"));
        if (user.isDeleted()) {
            throw new DisabledException(
                    "Account deleted");
        }

        if (user.isSuspended()) {
            throw new LockedException(
                    "Account suspended");
        }
        return new User(
                user.getEmail(),
                user.getPassword(),
                Collections.emptyList());
    }

    @Transactional(readOnly = true)
    public Iterable<UserResponseDto> getAllUsers(String sort) {
        var sorted = requestValidate.validateRequestParams(sort, "name", "email");

        return userRepository.findAll(sortAndPaginate.getSortedData(sorted))
                .stream()
                .map(userMapper::getUserResponseDto) // Using Manual Mapping
                // .map(user-> userMapper.toDto(user)) // Using MapStruct

                .toList();

    }

    @Transactional(readOnly = true)
    public List<UserSummary> getAllUserSummaries(String sort) {
        return getSorting(sort);
    }

    private List<UserSummary> getSorting(String sort) {
        var sorting = sortAndPaginate.getSortedData(sort);
        return userRepository.findAllProjectedBy(sorting);
    }


    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse<UserResponseDto>> getUserById(UUID id) {
        return userRepository.findById(id)
                .map(user -> ApiResponseBuilder.success(
                        "User fetched successfully",
                        userMapper.getUserResponseDto(user)))
                // userMapper.toDto(user)))
                .orElse(ApiResponseBuilder.error(
                        "User not found", HttpStatus.NOT_FOUND));

    }

    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse<UserSummary>> findSummaryId(UUID id) {
        return userRepository.findProjectedById(id).map(user -> ApiResponseBuilder.success(
                        "User fetched successfully",
                        user))
                .orElse(ApiResponseBuilder.notFound(
                        "User not found"));
    }


    @Transactional
    public ResponseEntity<ApiResponse<UserResponseDto>> updateUser(UserUpdateRequestDto userUpdateRequest) {
        var user = getCurrentUser.getCurrentUser();
        requestValidate.throwIfTrue(
                userRepository.findByEmail(userUpdateRequest.email().trim().toLowerCase()).isPresent(),
                "Email already exists");
        userMapper.updateEntity(user, userUpdateRequest);
        userRepository.save(user);
        return ApiResponseBuilder.success(
                "User updated successfully",
                userMapper.getUserResponseDto(user));

    }


    @Transactional
    public ResponseEntity<ApiResponse<UserResponseDto>> partialUpdateUser(UserPatchRequestDto userPatchDto) {
        var user = getCurrentUser.getCurrentUser();

        if (userPatchDto.name() != null) {
            user.setName(userPatchDto.name());
        }
        if (userPatchDto.email() != null) {
            requestValidate.throwIfTrue(
                    userRepository.findProjectedByEmail(userPatchDto.email().trim().toLowerCase())
                            .isPresent(),
                    "Email already exists");
            user.setEmail(userPatchDto.email());
        }
        userRepository.save(user);
        return ApiResponseBuilder.success(
                "User updated successfully",
                userMapper.getUserResponseDto(user));

    }

    private com.uchechukwu.store.entities.User getUserId(UUID id) {
        return entityValidator.findByIdOrThrow(userRepository, id, "User");
    }


    @Transactional
    public ResponseEntity<ApiResponse<Void>> changePassword(ChangePasswordRequestDto request) {
        var user = getCurrentUser.getCurrentUser();
        if (!user.getPassword().equals(request.getOldPassword())) {
            return ApiResponseBuilder.unAuthorized();

        }
        user.setPassword(request.getNewPassword());
        userRepository.save(user);
        return ApiResponseBuilder.success("Password changed successfully", null);

    }


    public ResponseEntity<ApiResponse<UserResponseDto>> me() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null
                || !authentication.isAuthenticated()
                || "anonymousUser".equals(
                authentication.getPrincipal())) {

            return ApiResponseBuilder.unAuthorized();
        }

        UUID userId = UUID.fromString(authentication.getPrincipal().toString());
        var user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return ApiResponseBuilder.notFound("Not Found");
        }
        var userDto = userMapper.getUserResponseDto(user);
        return ApiResponseBuilder.success("Fetched Successfully", userDto);
    }


    public ResponseEntity<ApiResponse<Object>> getCurrentUser() {
        var user = getCurrentUser.getCurrentUserId();
        System.out.println("User Id: " + user);

        return ApiResponseBuilder.success("Current user fetched successfully", null);
    }


}
