package com.uchechukwu.store.mappers;

import com.uchechukwu.store.dtos.request.UserRequestDto;
import com.uchechukwu.store.dtos.request.UserUpdateRequestDto;
import com.uchechukwu.store.dtos.response.UserResponseDto;
import com.uchechukwu.store.entities.User;
import org.springframework.stereotype.Component;

// import org.mapstruct.Mapper;

@Component
public class UserMapper {

    public UserResponseDto getUserResponseDto(User user) {

        return new UserResponseDto(user.getId(), user.getName(), user.getEmail(), user.getIsActive(), user.getRole().name(), user.getPhoneNumber());
    }

    public User toEntity(UserRequestDto dto) {

        return User.builder()
                .email(dto.email().trim().toLowerCase())
                .name(dto.name())
                .phoneNumber(dto.phoneNumber())
                .password(dto.password())
                .isActive(true)
                .role(dto.role())
                .build();
    }

    public void updateEntity(User user, UserUpdateRequestDto dto) {

        user.setName(dto.name());
        user.setEmail(dto.email().trim().toLowerCase());
        user.setPhoneNumber(dto.phoneNumber());
    }

}
