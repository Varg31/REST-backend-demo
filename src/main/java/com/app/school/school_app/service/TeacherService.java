package com.app.school.school_app.service;

import com.app.school.school_app.domain.ClassEntity;
import com.app.school.school_app.domain.Discipline;
import com.app.school.school_app.domain.Teacher;
import com.app.school.school_app.exceptions.DisciplineAlreadyExists;
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

    public long createTeacher(Teacher teacher) {
        return teacherRepo.save(teacher).getTeacherId();
    }

    public void updateTeacher(Teacher teacher, Long teacherId) throws NoSuchElementException {
        Teacher newTeacher = teacherRepo.findById(teacherId).orElseThrow(() ->
                new NoSuchElementException("No teacher with id: " + teacherId));

        newTeacher.setName(teacher.getName());
        newTeacher.setSurname(teacher.getSurname());
        newTeacher.setMiddleName(teacher.getMiddleName());
        newTeacher.setDateOfBirth(teacher.getDateOfBirth());
        newTeacher.setEmploymentBookNumber(teacher.getEmploymentBookNumber());
        newTeacher.setGender(teacher.getGender());

        teacherRepo.save(newTeacher);
    }

    public void deleteTeacherById(Long teacherId) throws NoSuchElementException {
        Teacher teacher = teacherRepo.findById(teacherId).orElseThrow(() ->
                new NoSuchElementException("No teacher with id: " + teacherId));

        teacherRepo.delete(teacher);
    }

    public Teacher getTeacherById(Long teacherId) throws NoSuchElementException {

        return teacherRepo.findById(teacherId).orElseThrow(() ->
                new NoSuchElementException("No teacher with id: " + teacherId));
    }

    public void addDisciplineForTeacher(Discipline discipline, Long teacherId)
            throws NoSuchElementException, DisciplineAlreadyExists {
        Teacher teacher = teacherRepo.findById(teacherId).orElseThrow(() ->
                new NoSuchElementException("No teacher with id: " + teacherId));

        Discipline newDiscipline = disciplineService.createIfNotPresent(discipline);

        if (teacher.getDisciplines().contains(newDiscipline)) {
            throw new DisciplineAlreadyExists("Discipline with id: " + newDiscipline.getDsplId() + " already exists");
        }

        teacher.getDisciplines().add(newDiscipline);
        newDiscipline.getTeachers().add(teacher);

        teacherRepo.save(teacher);
    }

    public Set<Discipline> getDisciplinesByTeacherId(Long teacherId) throws NoSuchElementException {
        Teacher teacher = teacherRepo.findById(teacherId).orElseThrow(() ->
                new NoSuchElementException("No teacher with id: " + teacherId));

        return teacher.getDisciplines();
    }

    public void removeDiscipline(Long teacherId, Long disciplineId) throws NoSuchElementException {
        Teacher teacher = teacherRepo.findById(teacherId).orElseThrow(() ->
                new NoSuchElementException("No teacher with id: " + teacherId));

        Discipline discipline = disciplineService.getDisciplineById(disciplineId);
        if (!teacher.getDisciplines().contains(discipline)) {
            throw new NoSuchElementException("No discipline with id: " + discipline
                    + " for teacher with id: " + teacherId);
        }

        teacher.getDisciplines().remove(discipline);
        discipline.getClasses().remove(teacher);
    }

    public Set<ClassEntity> getClassesByTeacherId(Long teacherId) throws NoSuchElementException {
        Teacher teacher = teacherRepo.findById(teacherId).orElseThrow(() ->
                new NoSuchElementException("No teacher with id: " + teacherId));

        return teacher.getClasses();
    }

    public Teacher findByEmploymentBookNumber(Integer number) throws NoSuchElementException {
        return teacherRepo.findByEmploymentBookNumber(number).orElseThrow(() ->
                new NoSuchElementException("No teacher with such employment book number: " + number));
    }

    Teacher createIfNotPresent(Teacher teacher) {
        Optional<Teacher> optionalTeacher;
        optionalTeacher = teacherRepo.findByEmploymentBookNumber(teacher.getEmploymentBookNumber());
        Long teacherId;

        if (!optionalTeacher.isPresent()) {
            teacherId = this.createTeacher(teacher);
            optionalTeacher = teacherRepo.findById(teacherId);
        }

        return optionalTeacher.get();
    }
}