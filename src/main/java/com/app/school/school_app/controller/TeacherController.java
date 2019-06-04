package com.app.school.school_app.controller;

import com.app.school.school_app.domain.Teacher;
import com.app.school.school_app.dto.ClassDTO;
import com.app.school.school_app.dto.DisciplineDTO;
import com.app.school.school_app.dto.TeacherDTO;
import com.app.school.school_app.service.TeacherService;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/teacher", produces = MediaType.APPLICATION_JSON_VALUE)
public class TeacherController {
    private TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> showAll() {
        List<TeacherDTO> collection = teacherService.findAll().stream().map(TeacherDTO::new)
                .collect(Collectors.toList());

        return new ResponseEntity<>(collection, HttpStatus.OK);
    }

    @PostMapping("/all")
    public ResponseEntity<TeacherDTO> addTeacher(@RequestBody @Valid TeacherDTO teacherDTO) {
        Teacher entity = teacherDTO.toClass();
        teacherService.createTeacher(entity);
        TeacherDTO responseDTO = new TeacherDTO(entity);

        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<TeacherDTO> getTeacher(@PathVariable Long id) {
        Teacher entity = teacherService.getTeacherById(id);
        TeacherDTO teacherDTO = new TeacherDTO(entity);

        return new ResponseEntity<>(teacherDTO, HttpStatus.OK);
    }

    @PutMapping("/all/{id}")
    public ResponseEntity<TeacherDTO> updateTeacher(@PathVariable Long id,
                                                    @RequestBody @Valid TeacherDTO teacherDTO) {
        Teacher teacherFromRequest = teacherDTO.toClass();
        teacherService.updateTeacher(teacherFromRequest, id);

        Teacher teacher = teacherService.getTeacherById(id);
        TeacherDTO responseDTO = new TeacherDTO(teacher);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("all/{id}")
    public ResponseEntity deleteTeacher(@PathVariable Long id) {
        Teacher entity = teacherService.getTeacherById(id);
        teacherService.deleteTeacherById(entity.getTeacherId());

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/{id}/disciplines")
    public ResponseEntity<TeacherDTO> addDisciplineForTeacher(@RequestBody @Valid DisciplineDTO disciplineDTO,
                                                              @PathVariable("id") Long classId) {
        teacherService.addDisciplineForTeacher(disciplineDTO.toClass(), classId);
        Teacher teacher = teacherService.getTeacherById(classId);

        TeacherDTO teacherDTO = new TeacherDTO(teacher);

        return new ResponseEntity<>(teacherDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}/disciplines")
    public ResponseEntity<?> getDisciplinesByTeacherId(@PathVariable("id") Long classId) {
        List<DisciplineDTO> dtoList = teacherService.getDisciplinesByTeacherId(classId)
                .stream().map(DisciplineDTO::new)
                .collect(Collectors.toList());

        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @DeleteMapping("/{id}/disciplines/{dsplId}")
    public ResponseEntity deleteDisciplineForTeacher(@PathVariable("id") Long teacherId,
                                                    @PathVariable("dsplId") Long disciplineId) {
        teacherService.removeDiscipline(teacherId, disciplineId);

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/{id}/classes")
    public ResponseEntity<?> getClassesByTeacherId(@PathVariable Long id) {
        List<ClassDTO> dtoList = teacherService.getClassesByTeacherId(id)
                .stream().map(ClassDTO::new)
                .collect(Collectors.toList());

        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity<TeacherDTO> findTeacherByEmploymentBook(@RequestParam Integer number) {
        Teacher teacher = teacherService.findByEmploymentBookNumber(number);

        TeacherDTO resultDTO = new TeacherDTO(teacher);

        return new ResponseEntity<>(resultDTO, HttpStatus.OK);
    }
}
