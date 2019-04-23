package com.app.school.school_app.controller;

import com.app.school.school_app.dto.StudentDTO;
import com.app.school.school_app.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {
    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/")
    public ResponseEntity<StudentDTO> greetings() {

        return null; //"greeting";
    }
}
