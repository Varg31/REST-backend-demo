package com.app.school.school_app.service;

import com.app.school.school_app.domain.Student;
import com.app.school.school_app.repository.StudentRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class StudentService {
    private StudentRepo studentRepo;

    public StudentService(StudentRepo studentRepo) {
        this.studentRepo = studentRepo;
    }

    public List<Student> findAll() {
        return studentRepo.findAll();
    }

    public Student getStudentById(Long studentId) throws NoSuchElementException {
        Student student = studentRepo.findById(studentId).orElseThrow(() ->
                new NoSuchElementException("No student with id: " + studentId));

        return student;
    }

    public void createStudent(Student student) {
        studentRepo.save(student);
    }

    public void updateStudent(Student student, Long studentId) throws NoSuchElementException {
        Student newStudent = studentRepo.findById(studentId).orElseThrow(() ->
                new NoSuchElementException("No such student for updating"));

        newStudent.setName(student.getName());
        newStudent.setSurname(student.getSurname());
        newStudent.setMiddleName(student.getMiddleName());
        newStudent.setDateOfBirth(student.getDateOfBirth());
        newStudent.setGender(student.getGender());
        newStudent.setClassEntity(student.getClassEntity());

        studentRepo.save(newStudent);
    }

    public void deleteStudentById(Long studentId) throws NoSuchElementException {
        Student student = studentRepo.findById(studentId).orElseThrow(() ->
                new NoSuchElementException("No student with id: " + studentId));

        studentRepo.delete(student);
    }

    public List<Student> loadStudentByNameAndSurname(String name, String surname) throws NoSuchElementException {
        List<Student> students = studentRepo.findByNameAndSurname(name, surname).orElseThrow(() ->
                new NoSuchElementException("No students with name: " + name + " and surname: " + surname));

        return students;
    }
}
