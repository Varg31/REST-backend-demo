package com.app.school.school_app.service;

import com.app.school.school_app.domain.*;
import com.app.school.school_app.exceptions.TeacherAlreadyExists;
import com.app.school.school_app.repository.ClassRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class ClassService {
    private ClassRepo classRepo;

    public ClassService(ClassRepo classRepo) {
        this.classRepo = classRepo;
    }

    public List<ClassEntity> findAll() {
        return classRepo.findAll();
    }

    public void createClass(ClassEntity classEntity) {
        classRepo.save(classEntity);
    }

    public void updateClass(ClassEntity classEntity, Long classId) throws NoSuchElementException {
        ClassEntity newClass = classRepo.findById(classId).orElseThrow(() ->
                new NoSuchElementException("No class with id: " + classId));

        newClass.setTitle(classEntity.getTitle());
        newClass.setTeachers(classEntity.getTeachers());
        newClass.setDisciplines(classEntity.getDisciplines());
        newClass.setStudents(classEntity.getStudents());

        classRepo.save(newClass);
    }

    public ClassEntity getClassById(Long classId) throws NoSuchElementException {
        ClassEntity entity = classRepo.findById(classId).orElseThrow(() ->
                new NoSuchElementException("No class with id: " + classId));

        return entity;
    }

    public void deleteClassById(Long classId) throws NoSuchElementException {
       ClassEntity classEntity = classRepo.findById(classId).orElseThrow(() ->
                new NoSuchElementException("No class with id: " + classId));

        classRepo.delete(classEntity);
    }

    public void addDisciplineToClass(Discipline discipline, Long classId) throws NoSuchElementException {
        ClassEntity entity = classRepo.findById(classId).orElseThrow(() ->
                new NoSuchElementException("No class with id: " + classId));

        entity.getDisciplines().add(discipline);
    }

    public Set<Discipline> getDisciplinesByClassId(Long classId) throws NoSuchElementException {
        ClassEntity classEntity = classRepo.findById(classId).orElseThrow(() ->
                new NoSuchElementException("No class with id: " + classId));

        return classEntity.getDisciplines();
    }

    public void addStudentToClass(Student student, Long classId) {
        ClassEntity entity = classRepo.findById(classId).orElseThrow(() ->
                new NoSuchElementException("No class with id: " + classId));

        entity.getStudents().add(student);
    }

    public Set<Student> getStudentsByClassId(Long classId) throws NoSuchElementException    {
        ClassEntity classEntity = classRepo.findById(classId).orElseThrow(() ->
                new NoSuchElementException("No class with id: " + classId));

        return classEntity.getStudents();
    }

    public Set<Teacher> getTeachersByClassId(Long classId) throws NoSuchElementException {
        Optional<ClassEntity> classEntity = classRepo.findById(classId);
        if (!classEntity.isPresent()) {
            throw new NoSuchElementException("No class with id: " + classId);
        }
        return classEntity.get().getTeachers();
    }

    public void addTeacherToClass(Teacher teacher, Long classId)
            throws NoSuchElementException, TeacherAlreadyExists {
        ClassEntity entity = classRepo.findById(classId).orElseThrow(() ->
                new NoSuchElementException("No class with id: " + classId));

        Set<Teacher> teachers = entity.getTeachers();
        Set<Discipline> disciplines = entity.getDisciplines();

        for (Discipline discipline: disciplines) {
            for (Teacher t: teachers) {
                if (teacher.getDisciplines().contains(discipline) && !t.getDisciplines().contains(discipline)) {
                    teachers.add(teacher);
                } else {
                    throw new TeacherAlreadyExists("There is no available disciplines for this teacher");
                }
            }
        }
    }
}