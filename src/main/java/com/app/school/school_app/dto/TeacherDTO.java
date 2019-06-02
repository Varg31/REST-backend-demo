package com.app.school.school_app.dto;

import com.app.school.school_app.controller.ClassController;
import com.app.school.school_app.controller.TeacherController;
import com.app.school.school_app.domain.ClassEntity;
import com.app.school.school_app.domain.Discipline;
import com.app.school.school_app.domain.Teacher;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import java.util.Set;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class TeacherDTO extends ResourceSupport {
    private Teacher teacher;

    public TeacherDTO() {
    }

    @JsonCreator
    public TeacherDTO(@JsonProperty Teacher teacher) {
        this.teacher = teacher;

        Long teacherId = teacher.getTeacherId();

        add(linkTo(methodOn(TeacherController.class).getDisciplinesByTeacherId(teacherId)).withRel("disciplines"));
        add(linkTo(methodOn(TeacherController.class).getClassesByTeacherId(teacherId)).withRel("classes"));
    }

    public Teacher toClass() {
        Teacher teacher = new Teacher();

        teacher.setName(this.getName());
        teacher.setSurname(this.getSurname());
        teacher.setMiddleName(this.getMiddleName());
        teacher.setDateOfBirth(this.getDateOfBirth());
        teacher.setGender(this.getGender());
//        teacher.setDisciplines(this.getDiscipline());
//        teacher.setClasses(this.getClasses());

        return teacher;
    }

    public Long getTeacherId() {
        return this.teacher.getTeacherId();
    }

    @NotBlank(message = "Teacher name can`t be empty")
    public String getName() {
        return teacher.getName();
    }

    @NotBlank(message = "Teacher surname can`t be empty")
    public String getSurname() {
        return teacher.getSurname();
    }

    @Nullable
    public String getMiddleName() {
        return teacher.getMiddleName();
    }

    @NotBlank(message = "Teacher date of birth can`t be empty")
    public String getDateOfBirth() {
        return teacher.getDateOfBirth();
    }

    @NotBlank(message = "Teacher gender can`t be empty")
    public String getGender() {
        return teacher.getGender();
    }

//    @Nullable
//    public Set<ClassEntity> getClasses() {
//        return teacher.getClasses();
//    }

//    @Nullable//@NotBlank(message = "Teacher disciplines list can`t be empty")
//    public Set<Discipline> getDiscipline() {
//        return teacher.getDisciplines();
//    }

    public void setName(String name) {
        teacher.setName(name);
    }

    public void setSurname(String surname) {
        teacher.setSurname(surname);
    }

    public void setMiddleName(String middleName) {
        teacher.setMiddleName(middleName);
    }

    public void setDateOfBirth(String dateOfBirth) {
        teacher.setDateOfBirth(dateOfBirth);
    }

    public void setGender(String gender) {
        teacher.setGender(gender);
    }

//    public void setClasses(Set<ClassEntity> classes) {
//        teacher.setClasses(classes);
//    }

//    public void setDiscipline(Set<Discipline> disciplines) {
//        teacher.setDisciplines(disciplines);
//    }
}
