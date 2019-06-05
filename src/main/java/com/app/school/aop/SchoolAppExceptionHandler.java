package com.app.school.aop;

import com.app.school.dto.MessageDTO;
import com.app.school.exceptions.*;
import com.app.school.school_app.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice("com.app.school.school_app")
public class SchoolAppExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<MessageDTO> handleAllExceptions(Throwable ex) {
        return new ResponseEntity<>(new MessageDTO(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchElementException.class)
    protected ResponseEntity<MessageDTO> handleNoSuchElementException(Throwable ex) {
        return new ResponseEntity<>(new MessageDTO(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = UserAlreadyExists.class)
    protected ResponseEntity<MessageDTO> handleUserAlreadyExists(Throwable ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_MODIFIED)
                .body(new MessageDTO(ex.getMessage()));
    }

    @ExceptionHandler(value = TeacherAlreadyExists.class)
    protected ResponseEntity<MessageDTO> handleTeacherAlreadyExists(Throwable ex) {
        return new ResponseEntity<>(new MessageDTO(ex.getMessage()), HttpStatus.NOT_MODIFIED);
    }

    @ExceptionHandler(PasswordConfirmationException.class)
    protected ResponseEntity<MessageDTO> handlePasswordConfirmationException(Throwable ex) {
        return new ResponseEntity<>(new MessageDTO(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    protected ResponseEntity<MessageDTO> handleUsernameNotFoundException(Throwable ex) {
        return new ResponseEntity<>(new MessageDTO(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = StudentAlreadyExists.class)
    protected ResponseEntity<MessageDTO> handleStudentAlreadyExists(Throwable ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_MODIFIED)
                .body(new MessageDTO(ex.getMessage()));
    }

    @ExceptionHandler(value = DisciplineAlreadyExists.class)
    protected ResponseEntity<MessageDTO> handleDisciplineAlreadyExists(Throwable ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_MODIFIED)
                .body(new MessageDTO(ex.getMessage()));
    }
}
