package com.app.school.school_app.domain;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "classes", schema = "institution")
public class ClassEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "class_id")
    private long classId;
    private String title;

    @OneToMany(mappedBy = "classEntity", cascade = CascadeType.ALL,
            fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Student> students;

    @ManyToMany
    @JoinTable(name = "classes_has_disciplines",
            joinColumns = { @JoinColumn(name = "classes_class_id") },
            inverseJoinColumns = { @JoinColumn(name = "disciplines_dspl_id") })
    private Set<Discipline> disciplines;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "classes_has_teachers",
            joinColumns = { @JoinColumn(name = "classes_class_id") },
            inverseJoinColumns = { @JoinColumn(name = "teachers_teacher_id") })
    private Set<Teacher> teachers;

    public ClassEntity() {
    }

    public ClassEntity(String title) {
        this.title = title;
    }

    public long getClassId() {
        return classId;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public Set<Discipline> getDisciplines() {
        return disciplines;
    }

    public void setDisciplines(Set<Discipline> disciplines) {
        this.disciplines = disciplines;
    }

    public Set<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(Set<Teacher> teachers) {
        this.teachers = teachers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassEntity that = (ClassEntity) o;
        return classId == that.classId &&
                Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(classId, title);
    }

    @Override
    public String toString() {
        return "ClassEntity{" +
                "classId=" + classId +
                ", title='" + title + '\'' +
                ", students=" + students +
                '}';
    }
}
