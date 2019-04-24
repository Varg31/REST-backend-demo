package com.app.school.school_app.service;

import com.app.school.school_app.domain.ClassEntity;
import com.app.school.school_app.domain.Discipline;
import com.app.school.school_app.domain.Teacher;
import com.app.school.school_app.repository.ClassRepo;
import com.app.school.school_app.repository.DisciplineRepo;
import com.app.school.school_app.repository.TeacherRepo;
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
    private TeacherRepo teacherRepo;
    private ClassRepo classRepo;

    public DisciplineService(DisciplineRepo disciplineRepo, TeacherRepo teacherRepo, ClassRepo classRepo) {
        this.teacherRepo = teacherRepo;
        this.classRepo = classRepo;
        this.disciplineRepo = disciplineRepo;
    }

    public List<Discipline> findAll() {
        return disciplineRepo.findAll();
    }

    public Discipline getDisciplineById(Long discipline_id) throws NoSuchElementException {
        Optional<Discipline> student = disciplineRepo.findById(discipline_id);
        if (!student.isPresent()) {
            throw new NoSuchElementException("No discipline with id: " + discipline_id);
        }
        return student.get();
    }

    public void createDiscipline(Discipline discipline) {
        disciplineRepo.save(discipline);
    }

    public void updateDiscipline(Discipline discipline, Long discipline_id)
            throws NoSuchElementException {
        Discipline newDiscipline = disciplineRepo.findById(discipline_id).get();
        if (newDiscipline == null) {
            throw new NoSuchElementException("No discipline with id: " + discipline_id);
        }
        newDiscipline.setTitle(discipline.getTitle());

        disciplineRepo.save(newDiscipline);
    }

    public void deleteDisciplineById(Long discipline_id) throws NoSuchElementException {
        Optional<Discipline> discipline = disciplineRepo.findById(discipline_id);
        if (!discipline.isPresent()) {
            throw new NoSuchElementException("No discipline with id: " + discipline_id);
        }
        disciplineRepo.delete(discipline.get());
    }

    public Set<Discipline> getDisciplinesByClassId(Long class_id) throws NoSuchElementException {
        Optional<ClassEntity> classEntity = classRepo.findById(class_id);
        if (!classEntity.isPresent()) {
            throw new NoSuchElementException("No class with id: " + class_id);
        }
        return classEntity.get().getDisciplines();
    }

    public Set<Discipline> getDisciplinesByTeacherId(Long teacher_id) throws NoSuchElementException {
        Optional<Teacher> teacher = teacherRepo.findById(teacher_id);
        if (!teacher.isPresent()) {
            throw new NoSuchElementException("No teacher with id: " + teacher_id);
        }
        return teacher.get().getDisciplines();
    }
}
