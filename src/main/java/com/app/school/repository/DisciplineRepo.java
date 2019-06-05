package com.app.school.repository;

import com.app.school.domain.Discipline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.NoSuchElementException;
import java.util.Optional;

@Repository
public interface DisciplineRepo extends JpaRepository<Discipline, Long> {
    Optional<Discipline> findByTitle(String title) throws NoSuchElementException;
}
