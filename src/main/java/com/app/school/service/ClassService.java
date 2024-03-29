package com.app.school.service;

import com.app.school.domain.ClassEntity;
import com.app.school.domain.Discipline;
import com.app.school.domain.Student;
import com.app.school.domain.Teacher;
import com.app.school.exceptions.TeacherAlreadyExists;
import com.app.school.repository.ClassRepo;
import com.app.school.exceptions.DisciplineAlreadyExists;
import com.app.school.exceptions.StudentAlreadyExists;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class ClassService {
    private ClassRepo classRepo;
    private StudentService studentService;
    private DisciplineService disciplineService;
    private TeacherService teacherService;

    public ClassService(ClassRepo classRepo, StudentService studentService,
                        DisciplineService disciplineService, TeacherService teacherService) {
        this.disciplineService = disciplineService;
        this.teacherService = teacherService;
        this.studentService = studentService;
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

        classRepo.save(newClass);
    }

    public ClassEntity getClassById(Long classId) throws NoSuchElementException {
        return classRepo.findById(classId).orElseThrow(() ->
                new NoSuchElementException("No class with id: " + classId));
    }

    public void deleteClassById(Long classId) throws NoSuchElementException {
        ClassEntity classEntity = classRepo.findById(classId).orElseThrow(() ->
                new NoSuchElementException("No class with id: " + classId));

        classRepo.delete(classEntity);
    }

    public void addDisciplineToClass(Discipline discipline, Long classId)
            throws NoSuchElementException, DisciplineAlreadyExists {
        ClassEntity classEntity = classRepo.findById(classId).orElseThrow(() ->
                new NoSuchElementException("No class with id: " + classId));

        Discipline newDiscipline = disciplineService.createIfNotPresent(discipline);

        if (classEntity.getDisciplines().contains(discipline)) {
            throw new DisciplineAlreadyExists("Discipline with id: " + discipline.getDsplId() + " already exists");
        }

        classEntity.getDisciplines().add(newDiscipline);

        newDiscipline.getClasses().add(classEntity);
        classRepo.save(classEntity);
    }

    public Set<Discipline> getDisciplinesByClassId(Long classId) throws NoSuchElementException {
        ClassEntity classEntity = classRepo.findById(classId).orElseThrow(() ->
                new NoSuchElementException("No class with id: " + classId));

        return classEntity.getDisciplines();
    }

    public void removeDiscipline(Long classId, Long disciplineId) throws NoSuchElementException {
        ClassEntity classEntity = classRepo.findById(classId).orElseThrow(() ->
                new NoSuchElementException("No class with id: " + classId));

        Discipline discipline = disciplineService.getDisciplineById(disciplineId);
        if (!classEntity.getDisciplines().contains(discipline))
            throw new NoSuchElementException("No discipline with id: " + discipline + " in class with id: " + classId);

        classEntity.getDisciplines().remove(discipline);
        discipline.getClasses().remove(classEntity);
    }

    public void addStudentToClass(Student student, Long classId)
            throws NoSuchElementException, StudentAlreadyExists {
        ClassEntity classEntity = classRepo.findById(classId).orElseThrow(() ->
                new NoSuchElementException("No class with id: " + classId));

        Student newStudent = studentService.createIfNotPresent(student);

        if (classEntity.getStudents().contains(newStudent)) {
            throw new StudentAlreadyExists("Student with id: " + newStudent.getStudentId() + " already exists");
        }

        newStudent.setClassEntity(classEntity);

        classEntity.getStudents().add(newStudent);
        classRepo.save(classEntity);
    }

    public Set<Student> getStudentsByClassId(Long classId) throws NoSuchElementException {
        ClassEntity classEntity = classRepo.findById(classId).orElseThrow(() ->
                new NoSuchElementException("No class with id: " + classId));

        return classEntity.getStudents();
    }

    public void removeStudent(Long classId, Long studentId) throws NoSuchElementException {
        ClassEntity classEntity = classRepo.findById(classId).orElseThrow(() ->
                new NoSuchElementException("No class with id: " + classId));

        Student student = studentService.getStudentById(studentId);
        if (!classEntity.getStudents().contains(student))
            throw new NoSuchElementException("No student with id: " + studentId + " in class with id: " + classId);

        classEntity.getStudents().remove(student);
        student.setClassEntity(null);
    }

    public Set<Teacher> getTeachersByClassId(Long classId) throws NoSuchElementException {
        ClassEntity classEntity = classRepo.findById(classId).orElseThrow(() ->
                new NoSuchElementException("No class with id: " + classId));

        return classEntity.getTeachers();
    }

    /**
       This method work not in all cases. So it must be rewrite or modified
     */

    public void addTeacherToClass(Teacher teacher, Long classId)
            throws NoSuchElementException, TeacherAlreadyExists {
        ClassEntity classEntity = classRepo.findById(classId).orElseThrow(() ->
                new NoSuchElementException("No class with id: " + classId));

        Teacher newTeacher = teacherService.createIfNotPresent(teacher);

        Set<Teacher> teachers = classEntity.getTeachers();
        Set<Discipline> disciplines = classEntity.getDisciplines();

        for (Discipline discipline: newTeacher.getDisciplines()) {
            if (disciplines.contains(discipline)) {
                if (teachers.isEmpty()) {
                    teachers.add(newTeacher);
                    newTeacher.getClasses().add(classEntity);

                    classRepo.save(classEntity);

                    return;
                } else
                    for (Teacher t : teachers) {
                        if (t.getDisciplines().contains(discipline)) break;
                        else {
                            teachers.add(newTeacher);
                            newTeacher.getClasses().add(classEntity);

                            classRepo.save(classEntity);

                            break;
                        }
                    }
            }
        }
    }

    public void removeTeacher(Long classId, Long teacherId) throws NoSuchElementException {
        ClassEntity classEntity = classRepo.findById(classId).orElseThrow(() ->
                new NoSuchElementException("No class with id: " + classId));

        Teacher teacher = teacherService.getTeacherById(teacherId);
        if (!classEntity.getTeachers().contains(teacher))
            throw new NoSuchElementException("No teacher with id: " + teacher + " in class with id: " + classId);

        classEntity.getTeachers().remove(teacher);
        teacher.getClasses().remove(classEntity);
    }

    public ClassEntity findByTitle(String title) throws NoSuchElementException {
        return classRepo.findByTitle(title).orElseThrow(() ->
                new NoSuchElementException("No class with such title: " + title));
    }
}