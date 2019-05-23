package com.app.school.school_app.controller;

import com.app.school.school_app.domain.Discipline;
import com.app.school.school_app.dto.DisciplineDTO;
import com.app.school.school_app.service.DisciplineService;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/discipline", produces = MediaType.APPLICATION_JSON_VALUE)
public class DisciplineController {
    private DisciplineService disciplineService;

    public DisciplineController(DisciplineService disciplineService) {
        this.disciplineService = disciplineService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllDisciplines() {
        List<DisciplineDTO> collection = disciplineService.findAll().stream().map(DisciplineDTO::new)
                .collect(Collectors.toList());

        return new ResponseEntity<>(collection, HttpStatus.OK);
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<DisciplineDTO> getDiscipline(@PathVariable Long id) {
        Discipline discipline = disciplineService.getDisciplineById(id);
        DisciplineDTO disciplineDTO = new DisciplineDTO(discipline);

        return new ResponseEntity<>(disciplineDTO, HttpStatus.OK);
    }

    @PostMapping("/all")
    public ResponseEntity<DisciplineDTO> addDiscipline(@RequestBody DisciplineDTO disciplineDTO) {
        Discipline disciplineFromRequest = disciplineDTO.toClass();
        disciplineService.createDiscipline(disciplineFromRequest);
        DisciplineDTO responseDTO = new DisciplineDTO(disciplineFromRequest);

        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @PutMapping("/all/{id}")
    public ResponseEntity<DisciplineDTO> updateDiscipline(@PathVariable Long id,
                                                          @RequestBody DisciplineDTO disciplineDTO) {
        Discipline disciplineFromRequest = disciplineDTO.toClass();
        disciplineService.updateDiscipline(disciplineFromRequest, id);
        Discipline discipline = disciplineService.getDisciplineById(id);
        DisciplineDTO responseDTO = new DisciplineDTO(discipline);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("all/{id}")
    public ResponseEntity deleteTeacher(@PathVariable Long id) {
        Discipline entity = disciplineService.getDisciplineById(id);
        disciplineService.deleteDisciplineById(entity.getDsplId());

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity<DisciplineDTO> findDisciplineByTitle(@RequestParam String title) {
        Discipline discipline = disciplineService.findByTitle(title);

        DisciplineDTO disciplineDTO = new DisciplineDTO(discipline);

        return new ResponseEntity<>(disciplineDTO, HttpStatus.OK);
    }
}
