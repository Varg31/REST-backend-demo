package com.app.school.school_app.domain;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "teachers", schema = "institution", indexes =
        {@Index(name = "employment_number_index", columnList = "employment_book_number")})
@EqualsAndHashCode(exclude = {"disciplines", "classes"})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "teacher_id", nullable = false)
    private long teacherId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "date_of_birth", nullable = false)
    private String dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @Column(name = "employment_book_number", nullable = false, unique = true)
    private Integer employmentBookNumber;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "disciplines_has_teachers",
            joinColumns = @JoinColumn(name = "teachers_teacher_id", referencedColumnName = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "disciplines_dspl_id", referencedColumnName = "id"))
    private Set<Discipline> disciplines = new HashSet<>();

    @ManyToMany(mappedBy = "teachers")
    private Set<ClassEntity> classes = new HashSet<>();
}
