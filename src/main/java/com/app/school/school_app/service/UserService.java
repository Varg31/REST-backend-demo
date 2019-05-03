package com.app.school.school_app.service;

import com.app.school.school_app.domain.Role;
import com.app.school.school_app.domain.User;
import com.app.school.school_app.repository.UserDetailsRepo;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import org.springframework.beans.factory.annotation.Value;
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

@Service
@Transactional
public class UserService implements UserDetailsService {

    private UserDetailsRepo userRepo;
    private PasswordEncoder passwordEncoder;
    private MailSender mailSender;

    @Value("${activation.domain}")
    private String activationDomain;

    @PostConstruct
    public void init() {
        User user = new User();
        user.setUsername("user");
        user.setPassword(passwordEncoder.encode("1111"));
        user.setEnabled(true);
        user.setAuthorities(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());
        user.setEmail("ashapravsky@gmail.com");

        userRepo.save(user);

        sendMessage(user);
    }

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

    public void createUser(User user) {
        userRepo.save(user);
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public void deleteUser(String userId) throws NoSuchElementException {
        Optional<User> user = userRepo.findById(userId);
        if (!user.isPresent())
            throw new NoSuchElementException("No user with id: " + userId);

        userRepo.delete(user.get());
    }

    public boolean addUser(User user) {
        Optional<User> userFromDb = userRepo.findByUsername(user.getUsername());

        if (userFromDb.isPresent()) {
            return false;
        }

        user.setEnabled(true);
        user.setAuthorities(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepo.save(user);

        sendMessage(user);

        return true;
    }
    public boolean activateUser(String code) {
        Optional<User> user = userRepo.findByActivationCode(code);

        if (user == null)
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
}
