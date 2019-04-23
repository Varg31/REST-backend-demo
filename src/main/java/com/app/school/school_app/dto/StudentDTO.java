package com.app.school.school_app.dto;


import org.springframework.hateoas.ResourceSupport;

public class StudentDTO extends ResourceSupport {
    private long studentId;
    private String name;
    private String surname;
    private String middleName;
    private String dateOfBirth;
    private String gender;
}
