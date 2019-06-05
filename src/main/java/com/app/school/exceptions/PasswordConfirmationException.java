package com.app.school.exceptions;

public class PasswordConfirmationException extends Exception {
    public PasswordConfirmationException() {

    }

    public PasswordConfirmationException(String message) {
        super(message);
    }

    public PasswordConfirmationException(Throwable ex) {
        super(ex);
    }
}
