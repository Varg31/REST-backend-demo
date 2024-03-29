package com.app.school.service;

import com.app.school.exceptions.UserAlreadyExists;
import com.app.school.domain.enums.Role;
import com.app.school.domain.User;
import com.app.school.repository.UserDetailsRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
public class UserService implements UserDetailsService, ApplicationListener<AuthenticationSuccessEvent> {

    private UserDetailsRepo userRepo;
    private PasswordEncoder passwordEncoder;
    private MailSender mailSender;

    @Value("${activation.domain}")
    private String activationDomain;

    public UserService(UserDetailsRepo userRepo, PasswordEncoder passwordEncoder, MailSender mailSender) {
        this.mailSender = mailSender;
        this.passwordEncoder = passwordEncoder;
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("User " + username + " not found!"));
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public void deleteUserByUsername(String username) throws NoSuchElementException {
        User user = userRepo.findByUsername(username).orElseThrow(() ->
                new NoSuchElementException("No user with username: " + username));

        userRepo.delete(user);
    }

    public void addUser(User user) throws UserAlreadyExists {
        Optional<User> userFromDb = userRepo.findByUsername(user.getUsername());

        if (userFromDb.isPresent()) {
            throw new UserAlreadyExists("User: " + user.getUsername() + " already exists.");
        }

        user.setEnabled(true);
        user.setAuthorities(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepo.save(user);

        sendMessage(user);
    }

    public boolean activateUser(String code) {
        Optional<User> user = userRepo.findByActivationCode(code);

        if (!user.isPresent())
            return false;

        user.get().setActivationCode(null);

        userRepo.save(user.get());

        return true;
    }

    private void sendMessage(User user) {
        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format("Hello, %s! \n" + "Welcome to our school. " +
                            "Please, visit next link to activate your account: %s%s \n" +
                            "We hope you`ll spend your time with pleasure :)",
                    user.getUsername(), activationDomain, user.getActivationCode());

            mailSender.send(user.getEmail(), "Activation code", message);
        }
    }

    public Long saveUser(User user, String username) throws NoSuchElementException {
        User oldUser = userRepo.findByUsername(username).orElseThrow(() ->
                new NoSuchElementException("No user with name: " + username + " for edit."));

        oldUser.setUsername(user.getUsername());
        oldUser.setAuthorities(user.getAuthorities());

        return userRepo.save(user).getId();
    }

    public void updateProfile(User user, String password, String email) {
        String userEmail = user.getEmail();

        boolean isEmailChanged = !email.equals(userEmail);

        if (isEmailChanged) {
            user.setEmail(email);

            if (!StringUtils.isEmpty(email)) {
                user.setActivationCode(UUID.randomUUID().toString());
            }
        }

        if (!StringUtils.isEmpty(password)) {
            user.setPassword(password);
        }

        userRepo.save(user);

        if (isEmailChanged) {
            sendMessage(user);
        }
    }

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        String username = ((UserDetails) event.getAuthentication().getPrincipal()).getUsername();

        User user = userRepo.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("User with username " + username + " is not exists"));

        user.setLastVisit(LocalDateTime.now());
    }
}
