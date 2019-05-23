package com.app.school.school_app.dto;

import com.app.school.school_app.domain.User;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.security.core.GrantedAuthority;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserDetailsDTO extends ResourceSupport {

    @NotBlank(message = "Username can`t be blank")
    private String username;

    @NotBlank(message = "Password can`t be blank")
    @Size(min = 4, max = 64, message = "Password must be between 4 and 64 characters")
    private String password;
    private String activationCode;

    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;

    @Email(message  = "Email not valid")
    @NotBlank(message = "Email can`y be blank")
    private String email;
    private LocalDateTime lastVisit;

    @NotBlank(message = "Authorities must be present")
    private Set<GrantedAuthority> authorities;

    public UserDetailsDTO() {

    }

    @JsonCreator
    public UserDetailsDTO(@JsonProperty User user) {
        setUsername(user.getUsername());
        setPassword(user.getPassword());
        setActivationCode(user.getActivationCode());
        setAccountNonExpired(user.isAccountNonExpired());
        setAccountNonLocked(user.isAccountNonLocked());
        setCredentialsNonExpired(user.isCredentialsNonExpired());
        setEnabled(user.isEnabled());
        setEmail(user.getEmail());
        setLastVisit(user.getLastVisit());
        setAuthorities(user.getAuthorities());
    }

    public User toClass() {
        User user = new User();

        user.setUsername(this.username);
        user.setPassword(this.password);
        user.setActivationCode(this.activationCode);
        user.setAccountNonExpired(this.accountNonExpired);
        user.setAccountNonLocked(this.accountNonLocked);
        user.setCredentialsNonExpired(this.credentialsNonExpired);
        user.setEnabled(this.enabled);
        user.setEmail(this.email);
        user.setLastVisit(this.lastVisit);
        user.setAuthorities(this.authorities);

        return user;
    }
}
