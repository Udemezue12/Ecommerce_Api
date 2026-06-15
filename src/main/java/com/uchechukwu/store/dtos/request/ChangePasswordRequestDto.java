package com.uchechukwu.store.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class ChangePasswordRequestDto {
    @NotBlank(message = "Password is required")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,15}$", message = "Password must be 8–15 characters and include uppercase, lowercase, number, and special character")
    @Size(min = 8, max = 15, message = "Password must be between 8 and 15 characters")
    private String newPassword;

    private String oldPassword;

}