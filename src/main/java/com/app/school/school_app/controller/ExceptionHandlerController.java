package com.app.school.school_app.controller;

import com.app.school.school_app.dto.MessageDTO;
import com.app.school.school_app.exceptions.PasswordConfirmationException;
import com.app.school.school_app.exceptions.TeacherAlreadyExists;
import com.app.school.school_app.exceptions.UserAlreadyExists;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    protected ResponseEntity<MessageDTO> handleNoSuchElementException(Throwable ex) {
        return new ResponseEntity<>(new MessageDTO(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = UserAlreadyExists.class)
    protected ResponseEntity<MessageDTO> handleUserAlreadyExists(Throwable ex) {
        return new ResponseEntity<>(new MessageDTO(ex.getMessage()), HttpStatus.NOT_MODIFIED);
    }

    @ExceptionHandler(value = TeacherAlreadyExists.class)
    protected ResponseEntity<MessageDTO> handleTeacherAlreadyExists(Throwable ex) {
        return new ResponseEntity<>(new MessageDTO(ex.getMessage()), HttpStatus.NOT_MODIFIED);
    }

    @ExceptionHandler(PasswordConfirmationException.class)
    protected ResponseEntity<MessageDTO> handlePasswordConfirmationException(Throwable ex) {
        return new ResponseEntity<>(new MessageDTO(ex.getMessage()), HttpStatus.NOT_FOUND);
    }
}
