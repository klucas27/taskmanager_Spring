package com.lucas.taskmanager.exception;

public class DuplicateTitleException extends RuntimeException {
    public DuplicateTitleException(String title) {
        super("Duplicate Title: " + title);
    }
}
