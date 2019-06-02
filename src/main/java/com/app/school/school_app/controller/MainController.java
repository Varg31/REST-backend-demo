package com.app.school.school_app.controller;

import com.app.school.school_app.dto.MessageDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
public class MainController {

    @GetMapping
    public ResponseEntity<MessageDTO> greetings() {
        MessageDTO messageDTO = new MessageDTO("Greeting");
        return new ResponseEntity<>(messageDTO, HttpStatus.OK);
    }
}