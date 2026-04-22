package com.lucas.taskmanager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TaskRequest(
        @NotBlank(message = "Title is required!")
        @Size(max = 100, message = "Title must be 100 characters or less")
        String title,

        @NotBlank(message = "Description is required!")
        String description

) {
}
