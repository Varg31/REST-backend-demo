package com.app.school.service;

import com.app.school.domain.Student;
import com.app.school.repository.StudentRepo;
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

        return studentRepo.findById(studentId).orElseThrow(() ->
                new NoSuchElementException("No student with id: " + studentId));
    }

    public long createStudent(Student student) {
        return studentRepo.save(student).getStudentId();
    }

    public void updateStudent(Student student, Long studentId) throws NoSuchElementException {
        Student newStudent = studentRepo.findById(studentId).orElseThrow(() ->
                new NoSuchElementException("No such student for updating"));

        newStudent.setName(student.getName());
        newStudent.setSurname(student.getSurname());
        newStudent.setMiddleName(student.getMiddleName());
        newStudent.setDateOfBirth(student.getDateOfBirth());
        newStudent.setStudentCardNumber(student.getStudentCardNumber());
        newStudent.setGender(student.getGender());

        studentRepo.save(newStudent);
    }

    public void deleteStudentById(Long studentId) throws NoSuchElementException {
        Student student = studentRepo.findById(studentId).orElseThrow(() ->
                new NoSuchElementException("No student with id: " + studentId));

        studentRepo.delete(student);
    }

    public List<Student> loadStudentByNameAndSurname(String name, String surname) throws NoSuchElementException {

        return studentRepo.findByNameAndSurname(name, surname).orElseThrow(() ->
                new NoSuchElementException("No students with name: " + name + " and surname: " + surname));
    }

    public Student findByStudentCardNumber(Integer number) throws NoSuchElementException{
        return studentRepo.findByStudentCardNumber(number).orElseThrow(() ->
                new NoSuchElementException("No student with such card number: " + number));
    }

    Student createIfNotPresent(Student student) {
        Optional<Student> optionalStudent = studentRepo.findByStudentCardNumber(student.getStudentCardNumber());
        Long studentId;

        if (!optionalStudent.isPresent()) {
            studentId = this.createStudent(student);
            optionalStudent = studentRepo.findById(studentId);
        }

        return optionalStudent.get();
    }
}
