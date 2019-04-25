package com.app.school.school_app.dto;


import com.app.school.school_app.controller.ClassController;
import com.app.school.school_app.domain.ClassEntity;
import com.app.school.school_app.domain.Student;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;


public class StudentDTO extends ResourceSupport {
    private Student student;

    public StudentDTO(Student student) {
        this.student = student;
    }

    public Long getStudentId() { return student.getStudentId(); }
    public String getName() { return student.getName(); }
    public String getSurname() { return student.getSurname(); }
    public String getMiddleName() { return student.getMiddleName(); }
    public String getDateOfBirth() { return student.getDateOfBirth(); }
    public String getGender() { return student.getGender(); }
    public ClassEntity getClassEntity() { return student.getClassEntity(); }


}
