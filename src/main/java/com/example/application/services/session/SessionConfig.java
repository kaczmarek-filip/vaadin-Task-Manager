package com.example.application.services.session;

import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SessionConfig {

    @Bean
    public ServletListenerRegistrationBean<HttpSessionListener> servletListenerRegistrationBean() {
        ServletListenerRegistrationBean<HttpSessionListener> servletListenerRegistrationBean = new ServletListenerRegistrationBean<>(new HttpSessionListener());
        return servletListenerRegistrationBean;
    }
}
