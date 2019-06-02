package com.app.school.school_app.dto;

import com.app.school.school_app.controller.ClassController;
import com.app.school.school_app.controller.StudentController;
import com.app.school.school_app.domain.ClassEntity;
import com.app.school.school_app.domain.Discipline;
import com.app.school.school_app.domain.Student;
import com.app.school.school_app.domain.Teacher;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import java.util.Set;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class ClassDTO extends ResourceSupport {
    private ClassEntity entity;

    public ClassDTO() {
    }

    @JsonCreator
    public ClassDTO(@JsonProperty ClassEntity classEntity) {
        this.entity = classEntity;

        final long id = classEntity.getClassId();
        //add(selfLink);
        add(linkTo(ClassController.class).withRel("class"));
        add(linkTo(methodOn(ClassController.class).getStudentsByClassId(id)).withRel("students"));
        add(linkTo(methodOn(ClassController.class).getTeachersByClassId(id)).withRel("teachers"));
        add(linkTo(methodOn(ClassController.class).getDisciplinesByClassId(id)).withRel("disciplines"));

    }

    public ClassEntity toClass() {
        ClassEntity classEntity = new ClassEntity();

        //classEntity.setClassId(this.getClassId());
        classEntity.setTitle(this.getTitle());
//        classEntity.setStudents(this.getStudents());
//        classEntity.setTeachers(this.getTeachers());
//        classEntity.setDisciplines(this.getDisciplines());

        return classEntity;
    }

    public Long getClassId() {
        return entity.getClassId();
    }

    public void setClassId(Long id) {
        entity.setClassId(id);
    }

    @NotBlank(message = "Class title can`t be empty")
    public String getTitle() {
        return entity.getTitle();
    }

    public void setTitle(String title) {
        entity.setTitle(title);
    }

//    @Nullable
//    public Set<Student> getStudents() {
//        return entity.getStudents();
//    }
//
//    public void setStudents(Set<Student> students) {
//        entity.setStudents(students);
//    }
//
//    @Nullable
//    public Set<Discipline> getDisciplines() {
//        return entity.getDisciplines();
//    }
//
//    public void setDisciplines(Set<Discipline> disciplines) {
//        entity.setDisciplines(disciplines);
//    }
//
//    @Nullable
//    public Set<Teacher> getTeachers() {
//        return entity.getTeachers();
//    }
//
//    public void setTeachers(Set<Teacher> teachers) {
//        entity.setTeachers(teachers);
//    }
}
