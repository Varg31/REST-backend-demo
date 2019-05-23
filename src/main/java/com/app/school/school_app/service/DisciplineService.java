package com.app.school.school_app.service;

import com.app.school.school_app.domain.Discipline;
import com.app.school.school_app.repository.DisciplineRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class DisciplineService {
    private DisciplineRepo disciplineRepo;

    public DisciplineService(DisciplineRepo disciplineRepo) {
        this.disciplineRepo = disciplineRepo;
    }

    public List<Discipline> findAll() {
        return disciplineRepo.findAll();
    }

    public Discipline getDisciplineById(Long disciplineId) throws NoSuchElementException {
        Discipline student = disciplineRepo.findById(disciplineId).orElseThrow(() ->
                new NoSuchElementException("No discipline with id: " + disciplineId));

        return student;
    }

    public void createDiscipline(Discipline discipline) {
        disciplineRepo.save(discipline);
    }

    public void updateDiscipline(Discipline discipline, Long disciplineId) throws NoSuchElementException {
        Discipline newDiscipline = disciplineRepo.findById(disciplineId).orElseThrow(() ->
                new NoSuchElementException("No discipline with id: " + disciplineId));

        newDiscipline.setTitle(discipline.getTitle());
        newDiscipline.setTeachers(discipline.getTeachers());
        newDiscipline.setClasses(discipline.getClasses());

        disciplineRepo.save(newDiscipline);
    }

    public void deleteDisciplineById(Long disciplineId) throws NoSuchElementException {
        Discipline discipline = disciplineRepo.findById(disciplineId).orElseThrow(() ->
                new NoSuchElementException("No discipline with id: " + disciplineId));

        disciplineRepo.delete(discipline);
    }

    public Discipline findByTitle(String title) throws NoSuchElementException {
        Discipline discipline = disciplineRepo.findByTitle(title).orElseThrow(() ->
                new NoSuchElementException("No discipline with title: " + title));

        return discipline;
    }
}