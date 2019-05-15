package com.app.school.school_app.repository;

import com.app.school.school_app.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepo extends JpaRepository<Student, Long> {
    Optional<List<Student>> findByNameAndSurname(String name, String surname);
}
