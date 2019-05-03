package com.app.school.school_app.controller;

import com.app.school.school_app.domain.Student;
import com.app.school.school_app.dto.StudentDTO;
import com.app.school.school_app.service.StudentService;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @PostMapping("/all")
    public ResponseEntity<StudentDTO> addStudent(@RequestBody StudentDTO studentDTO) {
        Student student = studentDTO.toClass();
        studentService.createStudent(student);
        StudentDTO responseDTO = new StudentDTO(student);

        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @PutMapping("/all/update/{id}")
    public ResponseEntity<StudentDTO> updateStudent(@PathVariable Long id,
                                                    @RequestBody StudentDTO studentDTO) {
        Student studentFromRequest = studentDTO.toClass();
        studentService.updateStudent(studentFromRequest, id);
        Student student = studentService.getStudentById(id);
        StudentDTO responseDTO = new StudentDTO(student);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("all/delete/{id}")
    public ResponseEntity deleteStudent(@PathVariable Long id) {
        Student entity = studentService.getStudentById(id);
        studentService.deleteStudentById(entity.getStudentId());

        return new ResponseEntity(HttpStatus.OK);
    }
}
