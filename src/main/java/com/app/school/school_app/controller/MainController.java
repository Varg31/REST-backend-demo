package com.app.school.school_app.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class MainController {

    @GetMapping
    public String greetings() {
        return "greeting";
    }
}