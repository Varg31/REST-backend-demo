package com.app.school.school_app.service;

import com.app.school.school_app.domain.Teacher;
import com.app.school.school_app.repository.TeacherRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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

    public void updateTeacher(Teacher student, Long teacher_id)
            throws NoSuchElementException {
        Teacher newTeacher = teacherRepo.findById(teacher_id).get();
        if(newTeacher == null) {
            throw new NoSuchElementException("No teacher with id: " + teacher_id);
        }
        newTeacher.setName(student.getName());
        newTeacher.setSurname(student.getSurname());
        newTeacher.setMiddleName(student.getMiddleName());
        newTeacher.setDateOfBirth(student.getDateOfBirth());
        newTeacher.setGender(student.getGender());

        teacherRepo.save(newTeacher);
    }

    public void deleteTeacher(Long teacher_id) throws NoSuchElementException {
        Optional<Teacher> teacher = teacherRepo.findById(teacher_id);
        if (!teacher.isPresent()) {
            throw new NoSuchElementException("No teacher with id: " + teacher_id);
        }
        teacherRepo.delete(teacher.get());
    }
}
