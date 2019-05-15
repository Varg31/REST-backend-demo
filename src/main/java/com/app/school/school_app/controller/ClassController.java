package com.app.school.school_app.controller;

import com.app.school.school_app.domain.ClassEntity;
import com.app.school.school_app.dto.ClassDTO;
import com.app.school.school_app.dto.DisciplineDTO;
import com.app.school.school_app.dto.StudentDTO;
import com.app.school.school_app.dto.TeacherDTO;
import com.app.school.school_app.exceptions.TeacherAlreadyExists;
import com.app.school.school_app.service.ClassService;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/class", produces = MediaType.APPLICATION_JSON_VALUE)
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

    @PostMapping("/all")
    public ResponseEntity<ClassDTO> addClass(@RequestBody @Valid ClassDTO classDTO) {
        ClassEntity entity = classDTO.toClass();
        classService.createClass(entity);
        ClassDTO responseDTO = new ClassDTO(entity);

        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<ClassDTO> getClass(@PathVariable Long id) {
        ClassEntity entity = classService.getClassById(id);
        ClassDTO classDTO = new ClassDTO(entity);

        return new ResponseEntity<>(classDTO, HttpStatus.OK);
    }

    @PutMapping("/all/update/{id}")
    public ResponseEntity<ClassDTO> updateClass(@PathVariable Long id,
                                                @RequestBody ClassDTO classDTO) {
        ClassEntity classFromRequest = classDTO.toClass();
        classService.updateClass(classFromRequest, id);

        ClassEntity classEntity = classService.getClassById(id);
        ClassDTO responseDTO = new ClassDTO(classEntity);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("all/delete/{id}")
    public ResponseEntity deleteClass(@PathVariable Long id) {
        ClassEntity entity = classService.getClassById(id);
        classService.deleteClassById(entity.getClassId());

        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/{id}/disciplines/add")
    public ResponseEntity<ClassDTO> addDisciplineToClass(@RequestBody DisciplineDTO disciplineDTO,
                                                         @PathVariable("id") Long class_id) {
        classService.addDisciplineToClass(disciplineDTO.toClass(), class_id);
        ClassEntity classEntity = classService.getClassById(class_id);

        ClassDTO classDTO = new ClassDTO(classEntity);

        return new ResponseEntity<>(classDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}/disciplines")
    public ResponseEntity<Resources<DisciplineDTO>> getDisciplinesByClassId(@PathVariable("id") Long class_id) {
        List<DisciplineDTO> dtoList = classService.getDisciplinesByClassId(class_id)
                .stream().map(DisciplineDTO::new)
                .collect(Collectors.toList());

        Resources<DisciplineDTO> resources = new Resources<>(dtoList);
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @PutMapping("/{id}/students/add")
    public ResponseEntity<ClassDTO> addStudentToClass(@RequestBody StudentDTO studentDTO,
                                                      @PathVariable("id") Long class_id) {
        classService.addStudentToClass(studentDTO.toClass(), class_id);
        ClassEntity classEntity = classService.getClassById(class_id);
        ClassDTO classDTO = new ClassDTO(classEntity);

        return new ResponseEntity<>(classDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}/students")
    public ResponseEntity<Resources<StudentDTO>> getStudentsByClassId(@PathVariable("id") Long class_id) {
        List<StudentDTO> studentList = classService.getStudentsByClassId(class_id).stream().map(StudentDTO::new)
                .collect(Collectors.toList());

        Resources<StudentDTO> resources = new Resources<>(studentList);
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @GetMapping("/{id}/teachers")
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No class with current id")
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Resources<TeacherDTO>> getTeachersByClassId(@PathVariable("id") Long class_id) {
        List<TeacherDTO> dtoList = classService.getTeachersByClassId(class_id)
                .stream().map(TeacherDTO::new)
                .collect(Collectors.toList());

        Resources<TeacherDTO> resources = new Resources<>(dtoList);
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @PutMapping("/{id}/teachers/add")
    public ResponseEntity<ClassDTO> addTeacherToClass(@RequestBody TeacherDTO teacherDTO,
                                                      @PathVariable("id") Long class_id) {
        try {
            classService.addTeacherToClass(teacherDTO.toClass(), class_id);
        } catch (TeacherAlreadyExists teacherAlreadyExists) {
            teacherAlreadyExists.printStackTrace();
        }
        ClassEntity classEntity = classService.getClassById(class_id);
        ClassDTO classDTO = new ClassDTO(classEntity);

        return new ResponseEntity<>(classDTO, HttpStatus.OK);
    }
}
