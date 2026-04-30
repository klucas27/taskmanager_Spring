package com.lucas.taskmanager.exception;

public class DuplicateEmailUserException extends RuntimeException {
    public DuplicateEmailUserException(String email) {
        super("Email Already Exist: " + email);
    }
}
