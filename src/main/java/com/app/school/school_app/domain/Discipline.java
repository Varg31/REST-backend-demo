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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "classes_has_disciplines",
            joinColumns = { @JoinColumn(name = "disciplines_dspl_id") },
            inverseJoinColumns = { @JoinColumn(name = "classes_class_id") })
    private Set<ClassEntity> classes;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "disciplines_has_teachers",
            joinColumns = { @JoinColumn(name = "disciplines_dspl_id") },
            inverseJoinColumns = { @JoinColumn(name = "teachers_teacher_id") })
    private Set<Teacher> teachers;

    public Discipline() {
    }

    public long getDsplId() {
        return dsplId;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
