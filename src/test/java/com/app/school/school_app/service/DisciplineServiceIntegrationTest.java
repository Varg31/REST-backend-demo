package com.app.school.school_app.service;

import com.app.school.school_app.domain.Discipline;
import com.app.school.school_app.repository.DisciplineRepo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class DisciplineServiceIntegrationTest {
    @Mock
    private static DisciplineRepo disciplineRepo;

    @InjectMocks
    private static DisciplineService disciplineService = new DisciplineService(disciplineRepo);

    private Discipline discipline;

    @Before
    public void init() {
        discipline = new Discipline();
        discipline.setTitle("Chemistry");

        Mockito.when(disciplineRepo.findById(discipline.getDsplId()))
                .thenReturn(java.util.Optional.ofNullable(discipline));
    }

    @Test
    public void addDisciplineTest() {
        disciplineService.createDiscipline(discipline);

        assertThat(discipline).isEqualTo(disciplineRepo.findById(discipline.getDsplId()).get());
    }
}
