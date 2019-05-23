package com.app.school.school_app.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "teachers", schema = "institution")
@Data
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private long teacherId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "date_of_birth")
    private String dateOfBirth;

    @Column(name = "gender", nullable = false)
    private String gender;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "disciplines_has_teachers",
            joinColumns = { @JoinColumn(name = "teachers_teacher_id") },
            inverseJoinColumns = { @JoinColumn(name = "disciplines_dspl_id") })
    private Set<Discipline> disciplines;

    @ManyToMany(mappedBy = "teachers")
    private Set<ClassEntity> classes;
}
