package com.app.school.school_app.domain;

import com.app.school.school_app.repository.UserDetailsRepo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.*;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@DataJpaTest
public class UserDetailsIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private UserDetailsRepo userRepo;
    private User user;

    @Before
    public void init() {
        user = new User();
        user.setUsername("Alex");
        user.setPassword("password");
        user.setEmail("some@mail.com");
    }

    @Test
    public void whenFindByUsername_thenReturnUser() {
        entityManager.persist(user);
        entityManager.flush();

        Optional<User> found = userRepo.findByUsername(user.getUsername());

        assertThat(found.get().getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    public void whenFindByActivationCode_thenReturnUser() {
        user.setActivationCode(UUID.randomUUID().toString());

        entityManager.persist(user);
        entityManager.flush();

        Optional<User> found = userRepo.findByActivationCode(user.getActivationCode());

        assertThat(found.get().getActivationCode()).isEqualTo(user.getActivationCode());
    }
}
