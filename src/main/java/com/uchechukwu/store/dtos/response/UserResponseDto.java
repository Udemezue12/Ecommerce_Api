package com.uchechukwu.store.dtos.response;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class UserResponseDto {
    
    UUID id;
    private String name;
    private String email;
    private Boolean isActive;
    private String role;
    private String phoneNumber;

}
