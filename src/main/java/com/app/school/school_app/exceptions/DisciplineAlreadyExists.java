package com.app.school.school_app.exceptions;

public class DisciplineAlreadyExists extends RuntimeException {
    public DisciplineAlreadyExists() {
    }

    public DisciplineAlreadyExists(String message) {
        super(message);
    }

    public DisciplineAlreadyExists(String message, Throwable cause) {
        super(message, cause);
    }

    public DisciplineAlreadyExists(Throwable cause) {
        super(cause);
    }
}
