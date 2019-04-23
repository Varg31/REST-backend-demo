package com.app.school.school_app.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "disciplines", schema = "institution")
public class Discipline {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private long dsplId;
    private String title;

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
