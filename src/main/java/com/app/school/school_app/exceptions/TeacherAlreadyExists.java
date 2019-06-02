package com.app.school.school_app.exceptions;

public class TeacherAlreadyExists extends RuntimeException {
    public TeacherAlreadyExists() {
    }

    public TeacherAlreadyExists(String message) {
        super(message);
    }

    public TeacherAlreadyExists(String message, Throwable cause) {
        super(message, cause);
    }

    public TeacherAlreadyExists(Throwable cause) {
        super(cause);
    }
}
