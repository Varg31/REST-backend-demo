package com.app.school.repository;

import com.app.school.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDetailsRepo extends JpaRepository<User, Long > {
    Optional<User> findByUsername(String username);
    Optional<User> findByActivationCode(String code);
}
