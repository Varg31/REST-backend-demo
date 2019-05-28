package com.app.school.school_app.controller;

import com.app.school.school_app.domain.ClassEntity;
import com.app.school.school_app.dto.ClassDTO;
import com.app.school.school_app.dto.DisciplineDTO;
import com.app.school.school_app.dto.StudentDTO;
import com.app.school.school_app.dto.TeacherDTO;
import com.app.school.school_app.service.ClassService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/class", produces = MediaType.APPLICATION_JSON_VALUE)
public class ClassController {
    private ClassService classService;

    public ClassController(ClassService classService) {
        this.classService = classService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> showAll() {
        List<ClassDTO> collection = classService.findAll().stream().map(ClassDTO::new)
                .collect(Collectors.toList());

        return new ResponseEntity<>(collection, HttpStatus.OK);
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
        //Sentry.capture("Taken the class with id: " + id);

        return new ResponseEntity<>(classDTO, HttpStatus.OK);
    }

    @PutMapping("/all/{id}")
    public ResponseEntity<ClassDTO> updateClass(@PathVariable Long id,
                                                @RequestBody ClassDTO classDTO) {
        ClassEntity classFromRequest = classDTO.toClass();
        classService.updateClass(classFromRequest, id);

        ClassEntity classEntity = classService.getClassById(id);
        ClassDTO responseDTO = new ClassDTO(classEntity);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("all/{id}")
    public ResponseEntity deleteClass(@PathVariable Long id) {
        ClassEntity entity = classService.getClassById(id);
        classService.deleteClassById(entity.getClassId());

        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/{id}/disciplines")
    public ResponseEntity<ClassDTO> addDisciplineToClass(@RequestBody @Valid DisciplineDTO disciplineDTO,
                                                         @PathVariable("id") Long classId) {
        classService.addDisciplineToClass(disciplineDTO.toClass(), classId);

        ClassEntity classEntity = classService.getClassById(classId);

        //Link selfLink = linkTo(methodOn(ClassController.class).addDisciplineToClass())
        ClassDTO classDTO = new ClassDTO(classEntity);

        return new ResponseEntity<>(classDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}/disciplines")
    public ResponseEntity<?> getDisciplinesByClassId(@PathVariable("id") Long classId) {
        List<DisciplineDTO> dtoList = classService.getDisciplinesByClassId(classId)
                .stream().map(DisciplineDTO::new)
                .collect(Collectors.toList());

        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @DeleteMapping("/{id}/disciplines/{dsplId}")
    public ResponseEntity deleteDisciplineFromClass(@PathVariable("id") Long classId,
                                                    @PathVariable("dsplId") Long disciplineId) {
        classService.removeDiscipline(classId, disciplineId);

        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/{id}/students")
    public ResponseEntity<ClassDTO> addStudentToClass(@RequestBody @Valid StudentDTO studentDTO,
                                                      @PathVariable("id") Long classId) {
        classService.addStudentToClass(studentDTO.toClass(), classId);

        ClassEntity classEntity = classService.getClassById(classId);
        ClassDTO classDTO = new ClassDTO(classEntity);

        return new ResponseEntity<>(classDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}/students")
    public ResponseEntity<?> getStudentsByClassId(@PathVariable("id") Long classId) {
        List<StudentDTO> studentList = classService.getStudentsByClassId(classId).stream().map(StudentDTO::new)
                .collect(Collectors.toList());

        return new ResponseEntity<>(studentList, HttpStatus.OK);
    }

    @DeleteMapping("/{id}/students/{studentId}")
    public ResponseEntity<?> deleteStudentFromClass(@PathVariable("id") Long classId,
                                                    @PathVariable Long studentId) {
        classService.removeStudent(classId, studentId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}/teachers")
    public ResponseEntity<?> getTeachersByClassId(@PathVariable("id") Long classId) {
        List<TeacherDTO> dtoList = classService.getTeachersByClassId(classId)
                .stream().map(TeacherDTO::new)
                .collect(Collectors.toList());

        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @PutMapping("/{id}/teachers")
    public ResponseEntity<ClassDTO> addTeacherToClass(@RequestBody @Valid TeacherDTO teacherDTO,
                                                      @PathVariable("id") Long classId) {
        classService.addTeacherToClass(teacherDTO.toClass(), classId);

        ClassEntity classEntity = classService.getClassById(classId);
        ClassDTO classDTO = new ClassDTO(classEntity);

        return ResponseEntity.ok(classDTO);
    }

    @DeleteMapping("/{id}/teachers/{teacherId}")
    public ResponseEntity deleteTeacherFromClass(@PathVariable("id") Long classId,
                                                    @PathVariable Long teacherId) {
        classService.removeTeacher(classId, teacherId);

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity<ClassDTO> findClassByTitle(@RequestParam String title) {
        ClassEntity resultClass = classService.findByTitle(title);

        ClassDTO classDTO = new ClassDTO(resultClass);

        return new ResponseEntity<>(classDTO, HttpStatus.OK);
    }
}
