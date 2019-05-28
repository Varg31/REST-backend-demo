package com.app.school.school_app.dto;


import com.app.school.school_app.controller.ClassController;
import com.app.school.school_app.domain.ClassEntity;
import com.app.school.school_app.domain.Student;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;

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
        add(linkTo(methodOn(ClassController.class).getClass(classId)).withRel("class"));
    }

    public Student toClass() {
        Student student = new Student();

        student.setName(this.getName());
        student.setSurname(this.getSurname());
        student.setMiddleName(this.getMiddleName());
        student.setDateOfBirth(this.getDateOfBirth());
        student.setGender(this.getGender());
        //student.setClassEntity(this.getClassEntity());

        return student;
    }

    public Long getStudentId() {
        return this.student.getStudentId();
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

    @NotBlank(message = "Student gender can`t be empty")
    public String getGender() { return student.getGender(); }
    public void setGender(String gender) { student.setGender(gender);}

//    @Nullable
//    public ClassEntity getClassEntity() { return student.getClassEntity(); }
//    public void setClassEntity(ClassEntity classEntity) { student.setClassEntity(classEntity);}
}
