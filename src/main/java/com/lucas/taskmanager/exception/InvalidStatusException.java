package com.lucas.taskmanager.exception;

public class InvalidStatusException extends RuntimeException {
    public InvalidStatusException(String status) {
        super("Requeri a Status Valid" + status);
    }
}
