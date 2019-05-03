package com.app.school.school_app.config;

import com.app.school.school_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@Primary
@EnableWebSecurity
//@EnableOAuth2Sso
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
            .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers("/").permitAll()
                .anyRequest().authenticated()
                .and()
                    .formLogin()
                .and()
                    .logout().permitAll()
                .and()
                    .csrf().disable();
    }

//    @Bean
//    public AffirmativeBased accessDecisionManager() {
//        List<AccessDecisionVoter> decisionVoters = Arrays.asList(new RoleVoter(), new AuthenticatedVoter());
//
//        AffirmativeBased affirmativeBased = new AffirmativeBased(decisionVoters);
//        return affirmativeBased;
//    }

//    @Bean
//    @Primary
//    public PrincipalExtractor principalExtractor(UserDetailsRepo userDetailsRepo) {
//        return map -> {
//            String id = (String) map.get("sub");
//            User user = userDetailsRepo.findById(id).orElseGet(() -> {
//                User newUser = new User();
//
//                newUser.setId(id);
//                newUser.setUsername((String)map.get("name"));
//                newUser.setEmail((String)map.get("email"));
//                newUser.setGender((String)map.get("gender"));
//                newUser.setLocale((String)map.get("locale"));
//                //newUser.setPassword(passwordEncoder().encode((String)map.get("password")));
//                newUser.setUserpic((String)map.get("picture"));
//
//                return newUser;
//            });
//
//            user.setLastVisit(LocalDateTime.now());
//
//            return userDetailsRepo.save(user);
//        };
//    }
}
