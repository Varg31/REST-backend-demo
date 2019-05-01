package com.app.school.school_app.dto;

import com.app.school.school_app.domain.ClassEntity;
import com.app.school.school_app.domain.Discipline;
import com.app.school.school_app.domain.Student;
import com.app.school.school_app.domain.Teacher;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.Nullable;
import org.springframework.hateoas.ResourceSupport;

import javax.validation.constraints.NotBlank;
import java.util.Set;

public class ClassDTO extends ResourceSupport {
    private ClassEntity entity;

    public ClassDTO() {
    }

    @JsonCreator
    public ClassDTO(@JsonProperty ClassEntity entity) {
        this.entity = entity;
    }

    public ClassEntity toClass() {
        ClassEntity classEntity = new ClassEntity();

        classEntity.setTitle(this.getTitle());
        classEntity.setStudents(this.getStudents());
        classEntity.setTeachers(this.getTeachers());
        classEntity.setDisciplines(this.getDisciplines());

        return classEntity;
    }

    @NotBlank
    public String getTitle() {
        return entity.getTitle();
    }

    public void setTitle(String title) {
        entity.setTitle(title);
    }

    @Nullable
    public Set<Student> getStudents() {
        return entity.getStudents();
    }

    public void setStudents(Set<Student> students) {
        entity.setStudents(students);
    }

    @Nullable
    public Set<Discipline> getDisciplines() {
        return entity.getDisciplines();
    }

    public void setDisciplines(Set<Discipline> disciplines) {
        entity.setDisciplines(disciplines);
    }

    @Nullable
    public Set<Teacher> getTeachers() {
        return entity.getTeachers();
    }

    public void setTeachers(Set<Teacher> teachers) {
        entity.setTeachers(teachers);
    }
}
