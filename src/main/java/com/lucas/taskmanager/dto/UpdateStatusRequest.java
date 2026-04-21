package com.lucas.taskmanager.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateStatusRequest(
        @NotBlank(message="Require value for new status.")
        String status
) {
}
