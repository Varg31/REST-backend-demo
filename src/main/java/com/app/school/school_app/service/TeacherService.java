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

    public TeacherService(TeacherRepo teacherRepo) {
        this.teacherRepo = teacherRepo;
    }

    public List<Teacher> findAll() {
        return teacherRepo.findAll();
    }

    public void createTeacher(Teacher teacher) {
        teacherRepo.save(teacher);
    }

    public void updateTeacher(Teacher teacher, Long teacherId) throws NoSuchElementException {
        Teacher newTeacher = teacherRepo.findById(teacherId).orElseThrow(() ->
                new NoSuchElementException("No teacher with id: " + teacherId));

        newTeacher.setName(teacher.getName());
        newTeacher.setSurname(teacher.getSurname());
        newTeacher.setMiddleName(teacher.getMiddleName());
        newTeacher.setDateOfBirth(teacher.getDateOfBirth());
        newTeacher.setGender(teacher.getGender());
        newTeacher.setClasses(teacher.getClasses());
        newTeacher.setDisciplines(teacher.getDisciplines());

        teacherRepo.save(newTeacher);
    }

    public void deleteTeacherById(Long teacherId) throws NoSuchElementException {
        Teacher teacher = teacherRepo.findById(teacherId).orElseThrow(() ->
                new NoSuchElementException("No teacher with id: " + teacherId));

        teacherRepo.delete(teacher);
    }

    public Teacher getTeacherById(Long teacherId) throws NoSuchElementException {
        Teacher teacher = teacherRepo.findById(teacherId).orElseThrow(() ->
                new NoSuchElementException("No teacher with id: " + teacherId));

        return teacher;
    }

    public void addDisciplineForTeacher(Discipline discipline, Long teacherId) throws NoSuchElementException {
        Teacher teacher = teacherRepo.findById(teacherId).orElseThrow(() ->
                new NoSuchElementException("No teacher with id: " + teacherId));

        teacher.getDisciplines().add(discipline);
    }

    public Set<Discipline> getDisciplinesByTeacherId(Long teacher_id) throws NoSuchElementException {
        Teacher teacher = teacherRepo.findById(teacher_id).orElseThrow(() ->
                new NoSuchElementException("No teacher with id: " + teacher_id));

        return teacher.getDisciplines();
    }
}
