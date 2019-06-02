package com.app.school.school_app.repository;

import com.app.school.school_app.domain.Discipline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.NoSuchElementException;
import java.util.Optional;

@Repository
public interface DisciplineRepo extends JpaRepository<Discipline, Long> {
    Optional<Discipline> findByTitle(String title) throws NoSuchElementException;
}
