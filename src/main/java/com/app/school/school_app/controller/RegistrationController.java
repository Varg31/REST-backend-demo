package com.app.school.school_app.controller;

import com.app.school.school_app.domain.User;
import com.app.school.school_app.dto.CaptchaResponseDTO;
import com.app.school.school_app.dto.MessageDTO;
import com.app.school.school_app.dto.UserDetailsDTO;
import com.app.school.school_app.exceptions.UserAlreadyExists;
import com.app.school.school_app.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.Collections;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class RegistrationController {
    private static final String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";

    private RestTemplate restTemplate;
    private UserService userService;

    @Value("${recaptcha.secret}")
    private String secret;

    public RegistrationController(UserService userService, RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.userService = userService;
    }

    @GetMapping(value = "/registration")
    public ResponseEntity<MessageDTO> registration() {
        MessageDTO messageDTO = new MessageDTO("Registration form");

        return new ResponseEntity<>(messageDTO, HttpStatus.OK);
    }


    @PostMapping(value = "/registration")
    public ResponseEntity<MessageDTO> addUser(@RequestBody @Valid UserDetailsDTO userDTO,
                                              @RequestParam("password2") String passwordConfirm,
                                              @RequestParam("g-recaptcha-response") String captchaResponse)
            throws UserAlreadyExists {
        String url = String.format(CAPTCHA_URL, secret, captchaResponse);
        CaptchaResponseDTO responseDTO = restTemplate.postForObject(url, Collections.emptyList(),
                CaptchaResponseDTO.class);

        if (!responseDTO.isSuccess()) {
            return new ResponseEntity<>(new MessageDTO("Fill captcha!"), HttpStatus.BAD_REQUEST);
        }

        boolean isConfirmEmpty = StringUtils.isEmpty(passwordConfirm);

        if (isConfirmEmpty) {
            return new ResponseEntity<>(new MessageDTO("Password confirmation can`t be empty"),
                    HttpStatus.BAD_REQUEST);
        }

        User user = userDTO.toClass();
        userService.addUser(user);

        if (user.getPassword() != null && !user.getPassword().equals(passwordConfirm)) {
            return new ResponseEntity<>(new MessageDTO("Passwords can`t be different"), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(new MessageDTO("Created user with id: " + user.getId() +
                " and username: " + user.getUsername()), HttpStatus.CREATED);
    }

    @GetMapping(value = "/activate/{code}")
    public ResponseEntity<MessageDTO> activate(@PathVariable String code) {
        boolean isActivated = userService.activateUser(code);

        MessageDTO messageDTO = new MessageDTO();
        if (isActivated) {
            messageDTO.setMessage("User successfully activated");
        } else {
            messageDTO.setMessage("Activation code is not found");
        }

        return new ResponseEntity<>(messageDTO, HttpStatus.OK);
    }
}
