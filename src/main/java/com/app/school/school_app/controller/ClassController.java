package com.app.school.school_app.controller;

import com.app.school.school_app.domain.ClassEntity;
import com.app.school.school_app.service.ClassService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/class")
public class ClassController {
    private ClassService classService;

    public ClassController(ClassService classService) {
        this.classService = classService;
    }

    @GetMapping
    public String showAll(Model model) {
        List<ClassEntity> classes = classService.findAll();

        model.addAttribute("classes", classes);
        return "class";
    }

    @PostMapping
    public String addClass(@RequestParam String title, Model model) {
        ClassEntity classEntity = new ClassEntity(title);
        classService.createClass(classEntity);
        List<ClassEntity> classes = classService.findAll();
        model.addAttribute("classes", classes);
        return "class";
    }
}
