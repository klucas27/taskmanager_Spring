package com.lucas.taskmanager.dto;

public record TaskResponse(
        Long id,
        String title,
        String status,
        String description

) {
}
