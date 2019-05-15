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
    private TeacherService teacherService;
    private DisciplineService disciplineService;
    private StudentService studentService;

    private ClassRepo classRepo;

    public List<ClassEntity> findAll() {
        return classRepo.findAll();
    }

    public void createClass(ClassEntity classEntity) {
        classRepo.save(classEntity);
    }

    public void updateClass(ClassEntity classEntity, Long class_id)
            throws NoSuchElementException {
        Optional<ClassEntity> newClassOptional = classRepo.findById(class_id);
        if (!newClassOptional.isPresent()) {
            throw new NoSuchElementException("No class with id: " + class_id);
        }
        ClassEntity newClass = newClassOptional.get();
        newClass.setTitle(classEntity.getTitle());
        newClass.setTeachers(classEntity.getTeachers());
        newClass.setDisciplines(classEntity.getDisciplines());
        newClass.setStudents(classEntity.getStudents());

        classRepo.save(newClass);
    }

    public ClassEntity getClassById(Long class_id) throws NoSuchElementException {
        Optional<ClassEntity> entity = classRepo.findById(class_id);
        if (!entity.isPresent()) {
            throw new NoSuchElementException("No class with id: " + class_id);
        }

        return entity.get();
    }

    public void deleteClassById(Long class_id) throws NoSuchElementException {
        Optional<ClassEntity> classEntity = classRepo.findById(class_id);
        if (!classEntity.isPresent()) {
            throw new NoSuchElementException("No class with id: " + class_id);
        }

        classRepo.delete(classEntity.get());
    }

    public void addDisciplineToClass(Discipline discipline, Long class_id) throws NoSuchElementException {
        try {
            disciplineService.getDisciplineById(discipline.getDsplId());
        } catch (NoSuchElementException ex) {
            disciplineService.createDiscipline(discipline);
        }

        Optional<ClassEntity> entity = classRepo.findById(class_id);
        if (!entity.isPresent()) {
            throw new NoSuchElementException("No class with id: " + class_id);
        }

        entity.get().getDisciplines().add(discipline);
    }

    public Set<Discipline> getDisciplinesByClassId(Long class_id) throws NoSuchElementException {
        Optional<ClassEntity> classEntity = classRepo.findById(class_id);
        if (!classEntity.isPresent()) {
            throw new NoSuchElementException("No class with id: " + class_id);
        }
        return classEntity.get().getDisciplines();
    }

    public void addStudentToClass(Student student, Long class_id) {
        try {
            studentService.getStudentById(student.getStudentId());
        } catch (NoSuchElementException ex) {
            studentService.createStudent(student);
        }

        Optional<ClassEntity> entity = classRepo.findById(class_id);
        if (!entity.isPresent()) {
            throw new NoSuchElementException("No class with id: " + class_id);
        }

        entity.get().getStudents().add(student);
    }

    public Set<Student> getStudentsByClassId(Long class_id) throws NoSuchElementException    {
        Optional<ClassEntity> classEntity = classRepo.findById(class_id);
        if (!classEntity.isPresent()) {
            throw new NoSuchElementException("No class with id: " + class_id);
        }

        return classEntity.get().getStudents();
    }

    public Set<Teacher> getTeachersByClassId(Long class_id) throws NoSuchElementException {
        Optional<ClassEntity> classEntity = classRepo.findById(class_id);
        if (!classEntity.isPresent()) {
            throw new NoSuchElementException("No class with id: " + class_id);
        }
        return classEntity.get().getTeachers();
    }

    public void addTeacherToClass(Teacher teacher, Long class_id)
            throws NoSuchElementException, TeacherAlreadyExists {
        try {
            teacherService.getTeacherById(teacher.getTeacherId());
        } catch (NoSuchElementException ex) {
            teacherService.createTeacher(teacher);
        }
        Optional<ClassEntity> entity = classRepo.findById(class_id);
        if (!entity.isPresent()) {
            throw new NoSuchElementException("No class with id: " + class_id);
        }

        Set<Teacher> teachers = entity.get().getTeachers();
        Set<Discipline> disciplines = entity.get().getDisciplines();

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

    @Autowired
    public void setClassRepo(ClassRepo classRepo) {
        this.classRepo = classRepo;
    }

    @Autowired
    public void setTeacherService(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @Autowired
    public void setDisciplineService(DisciplineService disciplineService) {
        this.disciplineService = disciplineService;
    }

    @Autowired
    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }
}