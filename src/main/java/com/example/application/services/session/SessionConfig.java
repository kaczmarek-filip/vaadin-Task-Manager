package com.example.application.services.session;

import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SessionConfig {

    @Bean
    public ServletListenerRegistrationBean<SessionListener> servletListenerRegistrationBean() {
        ServletListenerRegistrationBean<SessionListener> servletListenerRegistrationBean = new ServletListenerRegistrationBean<>(new SessionListener());
        return servletListenerRegistrationBean;
    }
}
