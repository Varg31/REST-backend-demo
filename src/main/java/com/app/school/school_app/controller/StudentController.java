package com.app.school.school_app.controller;

import com.app.school.school_app.domain.Student;
import com.app.school.school_app.dto.StudentDTO;
import com.app.school.school_app.service.StudentService;
import org.springframework.hateoas.Link;
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

@Controller
@RequestMapping
public class StudentController {
    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/")
    public String greetings() {

        return "greeting";
    }

//    @GetMapping("/student/class/{id}")
//    public ResponseEntity<List<StudentDTO>> getStudentsByClassId(@PathVariable("id") Long class_id) {
//        Set<Student> studentList = studentService.getStudentsByClassId(class_id);
//        Link link = linkTo(methodOn(StudentController.class).getAllStudents()).withSelfRel();
//
//        return getAllStudents();
//    }

    @GetMapping("/student")
    public String getAllStudents(Model model) {
        List<Student> studentList = studentService.findAll();
        //Link link = linkTo(methodOn(StudentController.class).getAllStudents()).withSelfRel();

        //List<StudentDTO> studentsDTO = new ArrayList<>();
//        for (Student entity: studentList) {
//            Link selfLink = new Link(link.getHref() + "/" + entity.getStudentId()).withSelfRel();
//            StudentDTO dto = new StudentDTO(entity, selfLink);
//            studentsDTO.add(dto);
//        }
        model.addAttribute("students", studentList);
        return "main";
    }

    @PostMapping("/student")
    public String addStudent(@RequestParam String name, String surname, String middleName, String dateOfBirth,
                             String gender, Model model) {
        Student student = new Student(name, surname, middleName, dateOfBirth, gender);
        studentService.createStudent(student);

        List<Student> students = studentService.findAll();
        model.addAttribute("students", students);

        return "main";
    }
}
