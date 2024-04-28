package com.example.application.services.session;

import com.example.application.components.data.User;
import com.example.application.components.data.database.hibernate.UserDAO;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

import java.time.Duration;
import java.time.Instant;

public class SessionListener implements HttpSessionListener {
    private Instant startTime;
    private Instant endTime;

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        System.err.println("Sesja utworzona");
        se.getSession().setMaxInactiveInterval(24 * 60 * 60);

        startTime = Instant.now();
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        System.err.println("Session expired");
        UserDAO.setOnline(false);
        User.logOut();

        endTime = Instant.now();
        Duration duration = Duration.between(startTime, endTime);
        System.err.println("Session duration: " + duration.getSeconds() + "seconds");

    }
}
