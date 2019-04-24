package com.app.school.school_app.service;

import com.app.school.school_app.domain.*;
import com.app.school.school_app.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class StudentService {
    private StudentRepo studentRepo;
    private ClassRepo classRepo;

    public StudentService(StudentRepo studentRepo, ClassRepo classRepo) {
        this.classRepo = classRepo;
        this.studentRepo = studentRepo;
    }

    public List<Student> findAll() {
        return studentRepo.findAll();
    }

    public Student getStudentById(Long student_id) throws NoSuchElementException {
        Optional<Student> student = studentRepo.findById(student_id);
        if (!student.isPresent()) {
            throw new NoSuchElementException("No student with id: " + student_id);
        }
        return student.get();
    }

    public void createStudent(Student student) {
        studentRepo.save(student);
    }

    public void updateStudent(Student student, Long student_id)
            throws NoSuchElementException {
        Student newStudent = studentRepo.findById(student_id).get();
        if(newStudent == null) {
            throw new NoSuchElementException("No student with id: " + student_id);
        }
        newStudent.setName(student.getName());
        newStudent.setSurname(student.getSurname());
        newStudent.setMiddleName(student.getMiddleName());
        newStudent.setDateOfBirth(student.getDateOfBirth());
        newStudent.setGender(student.getGender());

        studentRepo.save(newStudent);
    }

    public Set<Student> getStudentsByClassId(Long class_id) throws NoSuchElementException {
        Optional<ClassEntity> classEntity = classRepo.findById(class_id);
        if (!classEntity.isPresent()) {
            throw new NoSuchElementException("No class with id: " + class_id);
        }

        return classEntity.get().getStudents();
    }

    public void deleteStudentById(Long student_id) throws NoSuchElementException {
        Optional<Student> student = studentRepo.findById(student_id);
        if (!student.isPresent()) {
            throw new NoSuchElementException("No student with id: " + student_id);
        }
        studentRepo.delete(student.get());
    }
}
