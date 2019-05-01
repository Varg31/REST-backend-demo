package com.app.school.school_app.dto;


import com.app.school.school_app.domain.*;
import com.fasterxml.jackson.annotation.*;
import org.springframework.hateoas.ResourceSupport;


public class StudentDTO extends ResourceSupport {
    private Student student;

    public StudentDTO() {
    }

    @JsonCreator
    public StudentDTO(@JsonProperty Student student) {
        this.student = student;
    }

    public Student toClass() {
        Student student = new Student();

        student.setName(this.getName());
        student.setSurname(this.getSurname());
        student.setMiddleName(this.getMiddleName());
        student.setDateOfBirth(this.getDateOfBirth());
        student.setGender(this.getGender());
        student.setClassEntity(this.getClassEntity());

        return student;
    }

    public String getName() { return student.getName(); }
    public void setName(String name) { student.setName(name);}

    public String getSurname() { return student.getSurname(); }
    public void setSurname(String surname) { student.setSurname(surname);}

    public String getMiddleName() { return student.getMiddleName(); }
    public void setMiddleName(String middleName) { student.setMiddleName(middleName);}

    public String getDateOfBirth() { return student.getDateOfBirth(); }
    public void setDateOfBirth(String dateOfBirth) { student.setDateOfBirth(dateOfBirth);}

    public String getGender() { return student.getGender(); }
    public void setGender(String gender) { student.setGender(gender);}

    public ClassEntity getClassEntity() { return student.getClassEntity(); }
    public void setClassEntity(ClassEntity classEntity) { student.setClassEntity(classEntity);}
}
