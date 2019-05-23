package com.app.school.school_app.controller;

import com.app.school.school_app.domain.Student;
import com.app.school.school_app.dto.StudentDTO;
import com.app.school.school_app.service.StudentService;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/student", produces = MediaType.APPLICATION_JSON_VALUE)
public class StudentController {
    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllStudents() {
        List<StudentDTO> collection = studentService.findAll().stream().map(StudentDTO::new)
                .collect(Collectors.toList());

        return new ResponseEntity<>(collection, HttpStatus.OK);
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<StudentDTO> getStudent(@PathVariable Long id) {
        Student student = studentService.getStudentById(id);
        StudentDTO studentDTO = new StudentDTO(student);

        return new ResponseEntity<>(studentDTO, HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity<Resources<StudentDTO>> findStudentByNameAndSurname(
            @RequestParam(required = false, defaultValue = "") String name,
            @RequestParam(required = false, defaultValue = "") String surname) {
        List<StudentDTO> collection;

        if (!name.isEmpty() && !surname.isEmpty()) {
            collection = studentService.loadStudentByNameAndSurname(name, surname)
                    .stream().map(StudentDTO::new)
                    .collect(Collectors.toList());
        } else collection = studentService.findAll().stream().map(StudentDTO::new)
                .collect(Collectors.toList());

        Resources<StudentDTO> resources = new Resources<>(collection);

        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @PostMapping("/all")
    public ResponseEntity<StudentDTO> addStudent(@RequestBody StudentDTO studentDTO) {
        Student student = studentDTO.toClass();
        studentService.createStudent(student);
        StudentDTO responseDTO = new StudentDTO(student);

        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @PutMapping("/all/{id}")
    public ResponseEntity<StudentDTO> updateStudent(@PathVariable Long id,
                                                    @RequestBody StudentDTO studentDTO) {
        Student studentFromRequest = studentDTO.toClass();
        studentService.updateStudent(studentFromRequest, id);
        Student student = studentService.getStudentById(id);
        StudentDTO responseDTO = new StudentDTO(student);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("all/{id}")
    public ResponseEntity deleteStudent(@PathVariable Long id) {
        Student entity = studentService.getStudentById(id);
        studentService.deleteStudentById(entity.getStudentId());

        return new ResponseEntity(HttpStatus.OK);
    }
}
