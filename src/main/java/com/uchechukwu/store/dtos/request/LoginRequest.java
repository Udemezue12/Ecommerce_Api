package com.uchechukwu.store.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record LoginRequest(
        @NotBlank(message = "Email is required") @Email(message = "Invalid email format") String email,

        @NotBlank(message = "Password is required") @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,15}$", message = "Password must be 8–15 characters and include uppercase, lowercase, number, and special character") @Size(min = 8, max = 15, message = "Password must be between 8 and 15 characters") String password) {

}
