package com.app.school.dto;


import com.app.school.controller.ClassController;
import com.app.school.domain.Student;
import com.app.school.domain.enums.Gender;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;


public class StudentDTO extends ResourceSupport {
    private Student student;

    public StudentDTO() {
    }

    @JsonCreator
    public StudentDTO(@JsonProperty Student student) {
        this.student = student;

        Long classId = null;

        if (student.getClassEntity() != null) {
           classId = student.getClassEntity().getClassId();
        }
        add(ControllerLinkBuilder.linkTo(methodOn(ClassController.class).getClass(classId)).withRel("class"));
    }

    public Student toClass() {
        Student newStudent = new Student();

        newStudent.setName(getName());
        newStudent.setSurname(getSurname());
        newStudent.setMiddleName(getMiddleName());
        newStudent.setDateOfBirth(getDateOfBirth());
        newStudent.setStudentCardNumber(getStudentCardNumber());
        newStudent.setGender(getGender());

        return newStudent;
    }

    public Long getStudentId() {
        return student.getStudentId();
    }

    @NotBlank(message = "Student name can`t be empty")
    public String getName() { return student.getName(); }
    public void setName(String name) { student.setName(name);}

    @NotBlank(message = "Student surname can`t be empty")
    public String getSurname() { return student.getSurname(); }
    public void setSurname(String surname) { student.setSurname(surname);}

    @Nullable
    public String getMiddleName() { return student.getMiddleName(); }
    public void setMiddleName(String middleName) { student.setMiddleName(middleName);}

    @NotBlank(message = "Student date of birth can`t be empty")
    public String getDateOfBirth() { return student.getDateOfBirth(); }
    public void setDateOfBirth(String dateOfBirth) { student.setDateOfBirth(dateOfBirth);}

    @NotNull(message = "Student gender can`t be empty")
    public Gender getGender() { return student.getGender(); }
    public void setGender(Gender gender) { student.setGender(gender);}

    @NotNull(message = "Student card number can`t be empty")
    @Min(value = 8)
    public Integer getStudentCardNumber() {
        return student.getStudentCardNumber();
   }

   public void setStudentCardNumber(Integer number) {
        student.setStudentCardNumber(number);
   }
}
