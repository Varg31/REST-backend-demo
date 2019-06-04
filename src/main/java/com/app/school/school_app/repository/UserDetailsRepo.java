package com.app.school.school_app.repository;

import com.app.school.school_app.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.management.openmbean.OpenDataException;
import java.util.Optional;

@Repository
public interface UserDetailsRepo extends JpaRepository<User, Long > {
    Optional<User> findByUsername(String username);
    Optional<User> findByActivationCode(String code);
}
