package com.app.school.school_app.service;

import com.app.school.school_app.domain.ClassEntity;
import com.app.school.school_app.repository.ClassRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class ClassService {
    private ClassRepo classRepo;

    public ClassService(ClassRepo classRepo) {
        this.classRepo = classRepo;
    }

    public List<ClassEntity> findAll() {
        return classRepo.findAll();
    }

    public void createClass(ClassEntity classEntity) {
        classRepo.save(classEntity);
    }

    public void updateClass(ClassEntity classEntity, Long class_id)
            throws NoSuchElementException {
        ClassEntity newClass = classRepo.findById(class_id).get();
        if (newClass == null) {
            throw new NoSuchElementException("No class with id: " + class_id);
        }
        newClass.setTitle(classEntity.getTitle());

        classRepo.save(newClass);
    }

    public void deleteClassById(Long class_id) throws NoSuchElementException {
        Optional<ClassEntity> classEntity = classRepo.findById(class_id);
        if (!classEntity.isPresent()) {
            throw new NoSuchElementException("No class with id: " + class_id);
        }
        classRepo.delete(classEntity.get());
    }
}
