package com.app.school.school_app.security;

import com.app.school.school_app.config.WebSecurityConfig;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

public class WebSecurityInitializer extends AbstractSecurityWebApplicationInitializer {

    public WebSecurityInitializer() {
        super(WebSecurityConfig.class);
    }

}
