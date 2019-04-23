package com.app.school.school_app.repository;

import com.app.school.school_app.domain.Discipline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DisciplineRepo extends JpaRepository<Discipline, Long> {
}
