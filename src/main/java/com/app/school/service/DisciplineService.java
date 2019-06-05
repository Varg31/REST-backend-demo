package com.app.school.service;

import com.app.school.domain.ClassEntity;
import com.app.school.domain.Discipline;
import com.app.school.domain.Teacher;
import com.app.school.repository.DisciplineRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

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
        return disciplineRepo.findById(disciplineId).orElseThrow(() ->
                new NoSuchElementException("No discipline with id: " + disciplineId));
    }

    public long createDiscipline(Discipline discipline) {
        return disciplineRepo.save(discipline).getDsplId();
    }

    public void updateDiscipline(Discipline discipline, Long disciplineId) throws NoSuchElementException {
        Discipline newDiscipline = disciplineRepo.findById(disciplineId).orElseThrow(() ->
                new NoSuchElementException("No discipline with id: " + disciplineId));

        newDiscipline.setTitle(discipline.getTitle());

        disciplineRepo.save(newDiscipline);
    }

    public void deleteDisciplineById(Long disciplineId) throws NoSuchElementException {
        Discipline discipline = disciplineRepo.findById(disciplineId).orElseThrow(() ->
                new NoSuchElementException("No discipline with id: " + disciplineId));

        disciplineRepo.delete(discipline);
    }

    public Discipline findByTitle(String title) throws NoSuchElementException {
        return disciplineRepo.findByTitle(title).orElseThrow(() ->
                new NoSuchElementException("No discipline with title: " + title));
    }

    public Set<ClassEntity> getClassesByDisciplineId(Long disciplineId) throws NoSuchElementException {
        Discipline discipline = disciplineRepo.findById(disciplineId).orElseThrow(() ->
                new NoSuchElementException("No discipline with id: " + disciplineId));

        return discipline.getClasses();
    }

    public Set<Teacher> getTeachersByDisciplineId(Long disciplineId) throws NoSuchElementException {
        Discipline discipline = disciplineRepo.findById(disciplineId).orElseThrow(() ->
                new NoSuchElementException("No discipline with id: " + disciplineId));

        return discipline.getTeachers();
    }

    Discipline createIfNotPresent(Discipline discipline) {
        Optional<Discipline> newDiscipline = disciplineRepo.findByTitle(discipline.getTitle());
        Long disciplineId;

        if (!newDiscipline.isPresent()) {
            disciplineId = this.createDiscipline(discipline);
            newDiscipline = disciplineRepo.findById(disciplineId);
        }

        return newDiscipline.get();
    }
}