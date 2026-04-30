package com.lucas.taskmanager.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long userId) {
        super("UserId no found!" + userId);
    }
}
