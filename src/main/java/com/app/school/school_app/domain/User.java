package com.app.school.school_app.domain;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "usr")
@Data
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "user_id", updatable = false, nullable = false)
    private Long id;
    @Column(unique = true)
    @NotBlank(message = "Username can`t be empty")
    private String username;
    @NotBlank(message = "User password can`t be empty")
    private String password;

    @Email(message = "Email is incorrect")
    @NotBlank(message = "Email can`t be empty")
    private String email;
    private String activationCode;

    @Transient
    private boolean accountNonExpired = true;
    @Transient
    private boolean accountNonLocked = true;
    @Transient
    private boolean credentialsNonExpired = true;
    private boolean enabled;

    private LocalDateTime lastVisit;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name="user_role", joinColumns = @JoinColumn(name="user_id"))
    @Enumerated(EnumType.STRING)
    private Set<GrantedAuthority> authorities;
}
