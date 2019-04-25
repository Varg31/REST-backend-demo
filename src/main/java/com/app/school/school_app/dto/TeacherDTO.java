package com.app.school.school_app.dto;

import com.app.school.school_app.domain.ClassEntity;
import com.app.school.school_app.domain.Discipline;
import com.app.school.school_app.domain.Teacher;
import org.springframework.hateoas.ResourceSupport;

import java.util.Set;

public class TeacherDTO extends ResourceSupport {
    private Teacher teacher;

    public TeacherDTO(Teacher teacher) {
        this.teacher = teacher;
    }

    public Long getTEacherId() { return teacher.getTeacherId(); }
    public String getName() { return teacher.getName(); }
    public String getSurname() { return teacher.getSurname(); }
    public String getMiddleName() { return teacher.getMiddleName(); }
    public String getDateOfBirth() { return teacher.getDateOfBirth(); }
    public String getGender() { return teacher.getGender(); }
    public Set<ClassEntity> getClasses() { return teacher.getClasses(); }
    public Set<Discipline> getDiscipline() { return teacher.getDisciplines(); }
}
