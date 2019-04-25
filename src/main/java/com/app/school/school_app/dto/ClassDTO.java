package com.app.school.school_app.dto;

import com.app.school.school_app.domain.ClassEntity;
import com.app.school.school_app.domain.Discipline;
import com.app.school.school_app.domain.Student;
import com.app.school.school_app.domain.Teacher;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.hateoas.ResourceSupport;

import java.util.Set;

public class ClassDTO extends ResourceSupport {
    private final ClassEntity entity;

    @JsonCreator
    public ClassDTO(ClassEntity entity) {
        this.entity = entity;
    }

    public Long getClassId() { return entity.getClassId(); }

    public String getTitle() { return entity.getTitle(); }
    public void setTitle(String title) { entity.setTitle(title);}

    public Set<Student> getStudents() { return entity.getStudents(); }
    public void setStudents(Set<Student> students) { entity.setStudents(students); }

    public Set<Discipline> getDisciplines() { return entity.getDisciplines(); }
    public void setDiscipline(Set<Discipline> discipline) { entity.setDisciplines(discipline); }

    public Set<Teacher> getTeachers() { return entity.getTeachers(); }
    public void setTeachers(Set<Teacher> teachers) { entity.setTeachers(teachers); }
}
