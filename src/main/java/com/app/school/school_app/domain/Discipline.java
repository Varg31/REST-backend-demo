package com.app.school.school_app.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "disciplines", schema = "institution")
@Data
public class Discipline {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private long dsplId;

    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @ManyToMany(mappedBy = "disciplines")
    private Set<ClassEntity> classes;

    @ManyToMany(mappedBy = "disciplines")
    private Set<Teacher> teachers;
}
