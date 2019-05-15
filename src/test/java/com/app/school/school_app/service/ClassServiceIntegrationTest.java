package com.app.school.school_app.service;

import com.app.school.school_app.domain.ClassEntity;
import com.app.school.school_app.domain.Discipline;
import com.app.school.school_app.repository.ClassRepo;
import com.app.school.school_app.repository.DisciplineRepo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ClassServiceIntegrationTest {
    @Mock
    private static ClassRepo classRepo;
    @Mock
    private DisciplineRepo disciplineRepo;
    @InjectMocks
    private DisciplineService disciplineService;

    @InjectMocks
    private static ClassService classService;

    private ClassEntity classEntity;
    private Discipline discipline;

    public ClassServiceIntegrationTest() {
        disciplineService = new DisciplineService(disciplineRepo);
        classService = new ClassService();
        classService.setClassRepo(classRepo);
        classService.setDisciplineService(disciplineService);
    }

    @Before
    public void init() {
        classService.setClassRepo(classRepo);
        classEntity = new ClassEntity();
        classEntity.setTitle("11-A");

        discipline = new Discipline();
        discipline.setTitle("Mathematics");

        Mockito.when(classRepo.findById(classEntity.getClassId())).
                thenReturn(java.util.Optional.ofNullable(classEntity));

        Mockito.when(disciplineRepo.findByTitle(discipline.getTitle())).
                thenReturn(java.util.Optional.ofNullable(discipline));
    }

    @Test
    public void addClassTest() {
        classService.createClass(classEntity);

        assertThat(classEntity).isEqualTo(classRepo.findById(classEntity.getClassId()).get());
    }

    @Test
    public void addDisciplineToClassTest() {
        classService.createClass(classEntity);
        classService.addDisciplineToClass(discipline, classEntity.getClassId());

        assertThat(classEntity.getDisciplines()).contains(discipline);
        assertThat(disciplineRepo.findByTitle(discipline.getTitle()).get().getTitle()).isEqualTo(discipline.getTitle());
    }
}
