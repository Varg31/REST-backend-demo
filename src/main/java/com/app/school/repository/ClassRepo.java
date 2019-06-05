package com.app.school.repository;

import com.app.school.domain.ClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.NoSuchElementException;
import java.util.Optional;

@Repository
public interface ClassRepo extends JpaRepository<ClassEntity, Long> {
    Optional<ClassEntity> findByTitle(String title) throws NoSuchElementException;
}
