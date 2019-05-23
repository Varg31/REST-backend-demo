package com.app.school.school_app.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "classes", schema = "institution")
@Data
public class ClassEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "class_id", nullable = false)
    private Long classId;

    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @OneToMany(mappedBy = "classEntity", cascade = CascadeType.ALL,
            fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Student> students = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "classes_has_disciplines",
            joinColumns = {@JoinColumn(name = "classes_class_id")},
            inverseJoinColumns = {@JoinColumn(name = "disciplines_dspl_id")})
    private Set<Discipline> disciplines = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "classes_has_teachers",
            joinColumns = {@JoinColumn(name = "classes_class_id")},
            inverseJoinColumns = {@JoinColumn(name = "teachers_teacher_id")})
    private Set<Teacher> teachers = new HashSet<>();
}
