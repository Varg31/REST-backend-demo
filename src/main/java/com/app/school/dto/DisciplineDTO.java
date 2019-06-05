package com.app.school.dto;

import com.app.school.domain.Discipline;
import com.app.school.controller.DisciplineController;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.ResourceSupport;

import javax.validation.constraints.NotBlank;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class DisciplineDTO extends ResourceSupport {
    private Discipline discipline;

    public DisciplineDTO() {
    }

    @JsonCreator
    public DisciplineDTO(@JsonProperty Discipline discipline) {
        this.discipline = discipline;

        final long id = discipline.getDsplId();
        add(linkTo(methodOn(DisciplineController.class).getClassesByDisciplineId(id)).withRel("classes"));
        add(linkTo(methodOn(DisciplineController.class).getTeachersByDisciplineId(id)).withRel("teachers"));
    }

    public Discipline toClass() {
        Discipline discipline = new Discipline();

        discipline.setTitle(this.getTitle());

        return discipline;
    }

    public Long getDisciplineId() {
        return this.discipline.getDsplId();
    }

    @NotBlank(message = "Discipline title can`t be empty")
    public String getTitle() { return discipline.getTitle(); }
    public void setTitle(String title) { discipline.setTitle(title);}
}
