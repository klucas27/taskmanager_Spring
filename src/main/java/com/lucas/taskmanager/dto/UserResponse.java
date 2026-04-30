package com.lucas.taskmanager.dto;

public record UserResponse(
        Long id,
        String name,
        String email
) {
}
