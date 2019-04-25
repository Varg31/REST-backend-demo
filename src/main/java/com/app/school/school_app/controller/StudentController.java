package com.app.school.school_app.controller;

import com.app.school.school_app.domain.ClassEntity;
import com.app.school.school_app.domain.Student;
import com.app.school.school_app.dto.ClassDTO;
import com.app.school.school_app.dto.StudentDTO;
import com.app.school.school_app.service.StudentService;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/student")
public class StudentController {
    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/all")
    public ResponseEntity<Resources<StudentDTO>> getAllStudents() {
        List<StudentDTO> collection = studentService.findAll().stream().map(StudentDTO::new)
                .collect(Collectors.toList());

        Resources<StudentDTO> resources = new Resources<>(collection);
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @GetMapping("/class/{id}")
    public ResponseEntity<Resources<StudentDTO>> getStudentsByClassId(@PathVariable("id") Long class_id) {
        List<StudentDTO> studentList = studentService.getStudentsByClassId(class_id).stream().map(StudentDTO::new)
                .collect(Collectors.toList());

        Resources<StudentDTO> resources = new Resources<>(studentList);
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<StudentDTO> addStudent(@RequestBody Student student) {
        StudentDTO studentDTO = new StudentDTO(student);
        studentService.createStudent(student);

        return new ResponseEntity<>(studentDTO, HttpStatus.CREATED);
    }

    @PutMapping("/all/update/{id}")
    public ResponseEntity<StudentDTO> updateStudent(@PathVariable Long id,
                                                    @RequestBody Student studentFromRequest) {
        studentService.updateStudent(studentFromRequest, id);
        Student student = studentService.getStudentById(id);
        StudentDTO studentDTO = new StudentDTO(student);

        return new ResponseEntity<>(studentDTO, HttpStatus.OK);
    }
}
