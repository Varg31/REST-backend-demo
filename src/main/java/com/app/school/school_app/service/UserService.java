package com.app.school.school_app.service;

import com.app.school.school_app.domain.Role;
import com.app.school.school_app.domain.User;
import com.app.school.school_app.exceptions.UserAlreadyExists;
import com.app.school.school_app.repository.UserDetailsRepo;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.Transient;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService implements UserDetailsService {

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
        Optional<User> user = userRepo.findByUsername(username);
        if (!user.isPresent())
            throw new NoSuchElementException("No user with username: " + username);

        userRepo.delete(user.get());
    }

    public void addUser(User user) throws UserAlreadyExists {
        Optional<User> userFromDb = userRepo.findByUsername(user.getUsername());

        if (userFromDb.isPresent()) {
            throw new UserAlreadyExists("User: " + user.getUsername() + " already exists.");
        }

        user.setEnabled(true);
        user.setAuthorities(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword())); // for testing

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
            String message = String.format("Hello, %s! \n" + "Welcome to Sweater. " +
                            "Please, visit next link to activate your account: %s%s",
                    user.getUsername(), activationDomain, user.getActivationCode());

            mailSender.send(user.getEmail(), "Activation code", message);
        }
    }

    public void saveUser(User user, String username) throws NoSuchElementException {
        Optional<User> oldUser = userRepo.findByUsername(username);
        if (!oldUser.isPresent()) {
            throw new NoSuchElementException("No user with name: " + username + " for edit.");
        }
        oldUser.get().setUsername(user.getUsername());
        oldUser.get().setAuthorities(user.getAuthorities());

        userRepo.save(user);
    }

    public void updateProfile(User user, String password, String email) {
        String userEmail = user.getEmail();

        boolean isEmailChanged = (email != null && !email.equals(userEmail)) ||
                (userEmail != null && !userEmail.equals(email));

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
}
