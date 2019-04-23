package com.app.school.school_app.service;

import com.app.school.school_app.domain.ClassEntity;
import com.app.school.school_app.domain.Discipline;
import com.app.school.school_app.repository.DisciplineRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class DisciplineService {
    private DisciplineRepo disciplineRepo;

    public DisciplineService(DisciplineRepo disciplineRepo) {
        this.disciplineRepo = disciplineRepo;
    }

    public List<Discipline> findAll() {
        return disciplineRepo.findAll();
    }

    public void createDiscipline(Discipline discipline) {
        disciplineRepo.save(discipline);
    }

    public void updateDiscipline(Discipline discipline, Long discipline_id)
            throws NoSuchElementException {
        Discipline newDiscipline = disciplineRepo.findById(discipline_id).get();
        if (newDiscipline == null) {
            throw new NoSuchElementException("No discipline with id: " + discipline_id);
        }
        newDiscipline.setTitle(discipline.getTitle());

        disciplineRepo.save(newDiscipline);
    }

    public void deleteDisciplineById(Long discipline_id) throws NoSuchElementException {
        Optional<Discipline> discipline = disciplineRepo.findById(discipline_id);
        if (!discipline.isPresent()) {
            throw new NoSuchElementException("No discipline with id: " + discipline_id);
        }
        disciplineRepo.delete(discipline.get());
    }
}
