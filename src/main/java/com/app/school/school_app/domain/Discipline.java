package com.app.school.school_app.domain;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "disciplines", schema = "institution", indexes =
        {@Index(name = "discipline_title_index", columnList = "title")})
@EqualsAndHashCode(exclude = {"classes", "teachers"})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Discipline {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private long dsplId;

    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @ManyToMany(mappedBy = "disciplines")
    private Set<ClassEntity> classes = new HashSet<>();

    @ManyToMany(mappedBy = "disciplines")
    private Set<Teacher> teachers = new HashSet<>();
}
