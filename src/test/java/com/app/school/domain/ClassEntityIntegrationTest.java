package com.app.school.domain;

import com.app.school.repository.ClassRepo;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ClassEntityIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ClassRepo classRepo;
}
