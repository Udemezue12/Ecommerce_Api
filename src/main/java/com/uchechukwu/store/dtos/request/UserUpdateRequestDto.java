package com.uchechukwu.store.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserUpdateRequestDto(
        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid Email")
        String email,
        @NotBlank(message = "Phone number is required") @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Invalid phone number format") String phoneNumber

) {
}
