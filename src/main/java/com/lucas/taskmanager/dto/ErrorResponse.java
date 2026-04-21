package com.lucas.taskmanager.dto;

public record ErrorResponse(
        int status,
        String message
) {
}
