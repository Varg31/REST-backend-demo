package com.app.school.exceptions;

public class UserAlreadyExists extends Exception {
    public UserAlreadyExists() {
    }

    public UserAlreadyExists(String message) {
        super(message);
    }

    public UserAlreadyExists(String message, Throwable cause) {
        super(message, cause);
    }

    public UserAlreadyExists(Throwable cause) {
        super(cause);
    }
}
