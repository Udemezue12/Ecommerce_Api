package com.uchechukwu.store.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UserPatchRequestDto(

        @Size(min = 2, max = 50)
        String name,

        @Email
        String email


) {
}