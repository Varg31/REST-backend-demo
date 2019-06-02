package com.app.school.school_app.exceptions;

public class StudentAlreadyExists extends RuntimeException {

    public StudentAlreadyExists() {
    }

    public StudentAlreadyExists(String message) {
        super(message);
    }

    public StudentAlreadyExists(String message, Throwable cause) {
        super(message, cause);
    }

    public StudentAlreadyExists(Throwable cause) {
        super(cause);
    }
}
