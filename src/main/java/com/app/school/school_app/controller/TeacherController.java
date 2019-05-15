package com.app.school.school_app.controller;

import com.app.school.school_app.domain.Teacher;
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
    public ResponseEntity<Resources<TeacherDTO>> showAll() {
        List<TeacherDTO> collection = teacherService.findAll().stream().map(TeacherDTO::new)
                .collect(Collectors.toList());

        Resources<TeacherDTO> resources = new Resources<>(collection);

        return new ResponseEntity<>(resources, HttpStatus.OK);
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

    @PutMapping("/all/update/{id}")
    public ResponseEntity<TeacherDTO> updateTeacher(@PathVariable final long id,
                                                @RequestBody @Valid TeacherDTO teacherDTO) {
        Teacher teacherFromRequest = teacherDTO.toClass();
        teacherService.updateTeacher(teacherFromRequest, id);

        Teacher teacher = teacherService.getTeacherById(id);
        TeacherDTO responseDTO = new TeacherDTO(teacher);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("all/delete/{id}")
    public ResponseEntity deleteTeacher(@PathVariable Long id) {
        Teacher entity = teacherService.getTeacherById(id);
        teacherService.deleteTeacherById(entity.getTeacherId());

        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/{id}/disciplines/add")
    public ResponseEntity<TeacherDTO> addDisciplineForTeacher(@RequestBody @Valid DisciplineDTO disciplineDTO,
                                                         @PathVariable("id") Long class_id) {
        teacherService.addDisciplineForTeacher(disciplineDTO.toClass(), class_id);
        Teacher teacher = teacherService.getTeacherById(class_id);

        TeacherDTO teacherDTO = new TeacherDTO(teacher);

        return new ResponseEntity<>(teacherDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}/disciplines")
    public ResponseEntity<Resources<DisciplineDTO>> getDisciplinesByTeacherId(@PathVariable("id") Long class_id) {
        List<DisciplineDTO> dtoList = teacherService.getDisciplinesByTeacherId(class_id)
                .stream().map(DisciplineDTO::new)
                .collect(Collectors.toList());

        Resources<DisciplineDTO> resources = new Resources<>(dtoList);
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }
}
