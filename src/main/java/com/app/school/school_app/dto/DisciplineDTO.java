package com.app.school.school_app.dto;

import com.app.school.school_app.domain.ClassEntity;
import com.app.school.school_app.domain.Discipline;
import com.app.school.school_app.domain.Teacher;
import org.springframework.hateoas.ResourceSupport;

import java.util.Set;

public class DisciplineDTO extends ResourceSupport {
    private Discipline discipline;

    public DisciplineDTO(Discipline discipline) {
        this.discipline = discipline;
    }

    public Long getDisciplineId() { return discipline.getDsplId(); }

    public String getTitle() { return discipline.getTitle(); }

    public Set<Teacher> getTeachers() { return discipline.getTeachers(); }

    public Set<ClassEntity> getClasses() { return discipline.getClasses(); }
}
