package com.app.school.school_app.domain;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "classes", schema = "institution", indexes =
        {@Index(name = "class_title_index", columnList = "title")})
@EqualsAndHashCode(exclude = {"students", "disciplines", "teachers"})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClassEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "class_id", nullable = false)
    private Long classId;

    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @OneToMany(mappedBy = "classEntity", cascade = CascadeType.DETACH)
    private Set<Student> students = new HashSet<>();

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(name = "classes_has_disciplines",
            joinColumns = @JoinColumn(name = "classes_class_id", referencedColumnName = "class_id"),
            inverseJoinColumns = @JoinColumn(name = "disciplines_dspl_id", referencedColumnName = "id"))
    private Set<Discipline> disciplines = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "classes_has_teachers",
            joinColumns = @JoinColumn(name = "classes_class_id", referencedColumnName = "class_id"),
            inverseJoinColumns = @JoinColumn(name = "teachers_teacher_id", referencedColumnName = "teacher_id"))
    private Set<Teacher> teachers = new HashSet<>();
}
