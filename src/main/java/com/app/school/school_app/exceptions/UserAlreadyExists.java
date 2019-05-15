package com.app.school.school_app.exceptions;

public class UserAlreadyExists extends Exception {
    public UserAlreadyExists() {

    }

    public UserAlreadyExists(String message) {
        super(message);
    }
}
