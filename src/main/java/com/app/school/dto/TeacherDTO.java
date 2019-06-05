package com.app.school.dto;

import com.app.school.domain.Teacher;
import com.app.school.controller.TeacherController;
import com.app.school.domain.enums.Gender;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
        Teacher newTeacher = new Teacher();

        newTeacher.setName(getName());
        newTeacher.setSurname(getSurname());
        newTeacher.setMiddleName(getMiddleName());
        newTeacher.setDateOfBirth(getDateOfBirth());
        newTeacher.setEmploymentBookNumber(getEmploymentBookNumber());
        newTeacher.setGender(getGender());

        return newTeacher;
    }

    public Long getTeacherId() {
        return teacher.getTeacherId();
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

    @NotNull(message = "Teacher gender can`t be empty")
    public Gender getGender() {
        return teacher.getGender();
    }

    @NotNull(message = "Employment book number can`t be empty")
    public Integer getEmploymentBookNumber() {
        return teacher.getEmploymentBookNumber();
    }

    public void setEmploymentBookNumber(Integer number) {
        teacher.setEmploymentBookNumber(number);
    }

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

    public void setGender(Gender gender) {
        teacher.setGender(gender);
    }
}
