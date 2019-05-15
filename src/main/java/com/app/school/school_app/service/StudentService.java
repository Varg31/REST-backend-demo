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
        if (newStudent == null) {
            throw new NoSuchElementException("No student with id: " + student_id);
        }
        newStudent.setName(student.getName());
        newStudent.setSurname(student.getSurname());
        newStudent.setMiddleName(student.getMiddleName());
        newStudent.setDateOfBirth(student.getDateOfBirth());
        newStudent.setGender(student.getGender());
        newStudent.setClassEntity(student.getClassEntity());

        studentRepo.save(newStudent);
    }

    public void deleteStudentById(Long student_id) throws NoSuchElementException {
        Optional<Student> student = studentRepo.findById(student_id);
        if (!student.isPresent()) {
            throw new NoSuchElementException("No student with id: " + student_id);
        }
        studentRepo.delete(student.get());
    }

    public List<Student> loadStudentByNameAndSurname(String name, String surname) throws NoSuchElementException {
        Optional<List<Student>> student = studentRepo.findByNameAndSurname(name, surname);
        if (!student.isPresent()) {
            throw new NoSuchElementException("No students with name: " + name + " and surname: " + surname);
        }

        return student.get();
    }
}
