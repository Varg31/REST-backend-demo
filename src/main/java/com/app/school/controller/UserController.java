package com.app.school.controller;

import com.app.school.domain.User;
import com.app.school.dto.UserDetailsDTO;
import com.app.school.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> userList() {
        List<UserDetailsDTO> collection = userService.getAllUsers().stream().map(UserDetailsDTO::new)
                .collect(Collectors.toList());

        return new ResponseEntity<>(collection, HttpStatus.OK);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity deleteUser(@PathVariable String username) {
        User user = (User) userService.loadUserByUsername(username);
        userService.deleteUserByUsername(user.getUsername());

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/user"));
        return new ResponseEntity(headers, HttpStatus.OK);
    }

    @GetMapping("/edit/{username}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserDetailsDTO> userEditForm(@PathVariable String username) {
        User editableUser = (User) userService.loadUserByUsername(username);
        UserDetailsDTO userDTO = new UserDetailsDTO(editableUser);

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PutMapping("/edit/{username}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity userSave(
            @PathVariable String username,
            @RequestBody @Valid UserDetailsDTO userDTO) {
        User user = userDTO.toClass();
        userService.saveUser(user, username);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/user"));

        return new ResponseEntity(headers, HttpStatus.MOVED_PERMANENTLY);
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDetailsDTO> getProfile(@AuthenticationPrincipal User user) {
        UserDetailsDTO userDetailsDTO = new UserDetailsDTO(user);

        return new ResponseEntity<>(userDetailsDTO, HttpStatus.OK);
    }

    @PostMapping("/profile")
    public ResponseEntity<HttpHeaders> updateProfile(
            @AuthenticationPrincipal User user,
            @RequestParam String password,
            @RequestParam String email
    ) {
        userService.updateProfile(user, password, email);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("user/profile"));

        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }
}