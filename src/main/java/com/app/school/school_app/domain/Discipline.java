package com.app.school.school_app.domain;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "disciplines", schema = "institution")
public class Discipline {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private long dsplId;
    private String title;

    @ManyToMany(mappedBy = "disciplines")
    private Set<ClassEntity> classes;

    @ManyToMany(mappedBy = "disciplines")
    private Set<Teacher> teachers;

    public Discipline() {
    }

    public long getDsplId() {
        return dsplId;
    }

    @Basic
    @Column(name = "title", nullable = false, unique = true)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<ClassEntity> getClasses() {
        return classes;
    }

    public void setClasses(Set<ClassEntity> classes) {
        this.classes = classes;
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
        Discipline that = (Discipline) o;
        return dsplId == that.dsplId &&
                Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dsplId, title);
    }

    @Override
    public String toString() {
        return "Discipline{" +
                "dsplId=" + dsplId +
                ", title='" + title + '\'' +
                '}';
    }
}
