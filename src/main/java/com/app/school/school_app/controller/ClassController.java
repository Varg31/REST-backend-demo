package com.app.school.school_app.controller;

import com.app.school.school_app.domain.ClassEntity;
import com.app.school.school_app.domain.Student;
import com.app.school.school_app.domain.Teacher;
import com.app.school.school_app.dto.ClassDTO;
import com.app.school.school_app.dto.StudentDTO;
import com.app.school.school_app.service.ClassService;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.w3c.dom.traversal.TreeWalker;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/class")
public class ClassController {
    private ClassService classService;

    public ClassController(ClassService classService) {
        this.classService = classService;
    }

    @GetMapping("/all")
    public ResponseEntity<Resources<ClassDTO>> showAll() {
        List<ClassDTO> collection = classService.findAll().stream().map(ClassDTO::new)
                .collect(Collectors.toList());

        Resources<ClassDTO> resources = new Resources<>(collection);

        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<ClassDTO> addClass(@RequestBody ClassEntity classEntity) {
        ClassDTO classDTO = new ClassDTO(classEntity);
        classService.createClass(classEntity);

        return new ResponseEntity<>(classDTO, HttpStatus.CREATED);
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<ClassDTO> getClass(@PathVariable Long id) {
        ClassEntity entity = classService.getClassById(id);
        ClassDTO classDTO = new ClassDTO(entity);

        return new ResponseEntity<>(classDTO, HttpStatus.OK);
    }

    @PutMapping("/all/update/{id}")
    public ResponseEntity <ClassDTO> updateClass(@PathVariable final long id,
                                                 @RequestBody ClassEntity classFromRequest) {
        classService.updateClass(classFromRequest, id);
        ClassEntity classEntity = classService.getClassById(id);
        ClassDTO classDTO = new ClassDTO(classEntity);

        return new ResponseEntity<>(classDTO, HttpStatus.OK);
    }

    @DeleteMapping("all/{id}")
    public ResponseEntity deleteClass(@PathVariable Long id) {
        ClassEntity entity = classService.getClassById(id);
        for (Student student: entity.getStudents()) {
            student.setClassEntity(null);
        }
        classService.deleteClassById(entity.getClassId());

        return new ResponseEntity(HttpStatus.OK);
    }
}
