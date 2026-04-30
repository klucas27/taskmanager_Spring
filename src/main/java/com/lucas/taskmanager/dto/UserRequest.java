package com.lucas.taskmanager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserRequest(
        @NotBlank(message = "Name is required!")
        @Size(max = 100)
        String name,

        @NotBlank
        String email
) {
}
