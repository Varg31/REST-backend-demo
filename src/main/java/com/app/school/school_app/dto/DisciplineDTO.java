package com.app.school.school_app.dto;

import com.app.school.school_app.domain.*;
import com.fasterxml.jackson.annotation.*;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import java.util.Set;

public class DisciplineDTO extends ResourceSupport {
    private Discipline discipline;

    public DisciplineDTO() {
    }

    @JsonCreator
    public DisciplineDTO(@JsonProperty Discipline discipline) {
        this.discipline = discipline;
    }

    public Discipline toClass() {
        Discipline discipline = new Discipline();

        discipline.setTitle(this.getTitle());
        discipline.setClasses(this.getClasses());
        discipline.setTeachers(this.getTeachers());

        return discipline;
    }

    @NotBlank
    public String getTitle() { return discipline.getTitle(); }
    public void setTitle(String title) { discipline.setTitle(title);}

    @Nullable
    public Set<Teacher> getTeachers() { return discipline.getTeachers(); }
    public void setTeachers(Set<Teacher> teachers) { discipline.setTeachers(teachers); }

    @Nullable
    public Set<ClassEntity> getClasses() { return discipline.getClasses(); }
    public void setClasses(Set<ClassEntity> classes) { discipline.setClasses(classes); }
}
