package com.lucas.taskmanager.dto;

public record LoginRequest(
        String email,
        String password
) {
}
