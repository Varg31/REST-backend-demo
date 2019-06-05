package com.app.school.dto;

import com.app.school.domain.ClassEntity;
import com.app.school.controller.ClassController;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.ResourceSupport;

import javax.validation.constraints.NotBlank;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class ClassDTO extends ResourceSupport {
    private ClassEntity entity;

    public ClassDTO() {
    }

    @JsonCreator
    public ClassDTO(@JsonProperty ClassEntity classEntity) {
        this.entity = classEntity;

        final long id = classEntity.getClassId();
        //add(selfLink);
        add(linkTo(ClassController.class).withRel("class"));
        add(linkTo(methodOn(ClassController.class).getStudentsByClassId(id)).withRel("students"));
        add(linkTo(methodOn(ClassController.class).getTeachersByClassId(id)).withRel("teachers"));
        add(linkTo(methodOn(ClassController.class).getDisciplinesByClassId(id)).withRel("disciplines"));

    }

    public ClassEntity toClass() {
        ClassEntity classEntity = new ClassEntity();

        classEntity.setTitle(this.getTitle());

        return classEntity;
    }

    public Long getClassId() {
        return entity.getClassId();
    }

    @NotBlank(message = "Class title can`t be empty")
    public String getTitle() {
        return entity.getTitle();
    }

    public void setTitle(String title) {
        entity.setTitle(title);
    }
}
