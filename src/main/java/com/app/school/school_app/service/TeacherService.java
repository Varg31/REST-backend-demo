package com.app.school.school_app.service;

import com.app.school.school_app.domain.Discipline;
import com.app.school.school_app.domain.Teacher;
import com.app.school.school_app.repository.TeacherRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class TeacherService {
    private TeacherRepo teacherRepo;
    private DisciplineService disciplineService;

    public TeacherService(TeacherRepo teacherRepo, DisciplineService disciplineService) {
        this.disciplineService = disciplineService;
        this.teacherRepo = teacherRepo;
    }

    public List<Teacher> findAll() {
        return teacherRepo.findAll();
    }

    public void createTeacher(Teacher teacher) {
        teacherRepo.save(teacher);
    }

    public void updateTeacher(Teacher teacher, Long teacher_id)
            throws NoSuchElementException {
        Teacher newTeacher = teacherRepo.findById(teacher_id).get();
        if (newTeacher == null) {
            throw new NoSuchElementException("No teacher with id: " + teacher_id);
        }
        newTeacher.setName(teacher.getName());
        newTeacher.setSurname(teacher.getSurname());
        newTeacher.setMiddleName(teacher.getMiddleName());
        newTeacher.setDateOfBirth(teacher.getDateOfBirth());
        newTeacher.setGender(teacher.getGender());
        newTeacher.setClasses(teacher.getClasses());
        newTeacher.setDisciplines(teacher.getDisciplines());

        teacherRepo.save(newTeacher);
    }

    public void deleteTeacherById(Long teacher_id) throws NoSuchElementException {
        Optional<Teacher> teacher = teacherRepo.findById(teacher_id);
        if (!teacher.isPresent()) {
            throw new NoSuchElementException("No teacher with id: " + teacher_id);
        }
        teacherRepo.delete(teacher.get());
    }

    public Teacher getTeacherById(Long teacher_id) throws NoSuchElementException {
        Optional<Teacher> teacher = teacherRepo.findById(teacher_id);
        if (!teacher.isPresent()) {
            throw new NoSuchElementException("No teacher with id: " + teacher_id);
        }
        return teacher.get();
    }

    public void addDisciplineForTeacher(Discipline discipline, Long teacher_id) throws NoSuchElementException {
        try {
            disciplineService.getDisciplineById(discipline.getDsplId());
        } catch (NoSuchElementException ex) {
            disciplineService.createDiscipline(discipline);
        }

        Optional<Teacher> teacher = teacherRepo.findById(teacher_id);
        if (!teacher.isPresent()) {
            throw new NoSuchElementException("No teacher with id: " + teacher_id);
        }

        teacher.get().getDisciplines().add(discipline);
    }

    public Set<Discipline> getDisciplinesByTeacherId(Long teacher_id) throws NoSuchElementException {
        Optional<Teacher> teacher = teacherRepo.findById(teacher_id);
        if (!teacher.isPresent()) {
            throw new NoSuchElementException("No teacher with id: " + teacher_id);
        }
        return teacher.get().getDisciplines();
    }
}
