package com.example.application.services.session;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;

import java.time.Instant;

@Deprecated
@WebListener
public class HttpSessionListener implements jakarta.servlet.http.HttpSessionListener {
    private Instant startTime;
    private Instant endTime;

    @Override
    public void sessionCreated(HttpSessionEvent se) {
//        System.err.println("Sesja HTTP utworzona");
////        se.getSession().setMaxInactiveInterval(24 * 60 * 60);
//        se.getSession().setMaxInactiveInterval(5);
//
//        startTime = Instant.now();
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
//        System.err.println("HTTP session expired");
////        UserDAO.setOnline(false);
//        User.logOut();
//
//        VaadinSession.getCurrent().getSession().invalidate();
//
//        endTime = Instant.now();
//        Duration duration = Duration.between(startTime, endTime);
//        System.err.println("Session duration: " + duration.getSeconds() + "seconds");

    }
}
