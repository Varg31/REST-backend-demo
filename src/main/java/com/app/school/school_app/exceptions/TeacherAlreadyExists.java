package com.app.school.school_app.exceptions;

public class TeacherAlreadyExists extends Exception {
    public TeacherAlreadyExists() {
    }

    public TeacherAlreadyExists(String message) {
        super(message);
    }
}
