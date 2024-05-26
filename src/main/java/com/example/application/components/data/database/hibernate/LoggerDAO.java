package com.example.application.components.data.database.hibernate;

import com.example.application.components.data.Logger;
import com.example.application.components.data.User;
import org.hibernate.query.Query;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LoggerDAO extends HibernateConnection{

    private static String getString(Logger logger){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        StringBuilder sb = new StringBuilder();
        sb.append(logger.getId())
                .append(" | ")
                .append(logger.getTime().format(formatter))
                .append(" | ")
                .append(logger.getUser().getEmail())
                .append(" | ")
                .append(logger.getEvent());

        return sb.toString();
    }

    public static void log(String event){
        start();
        Logger logger = new Logger();
        logger.setTime(LocalDateTime.now());
        logger.setUser(User.getLoggedInUser());
        logger.setEvent(event);

        session.persist(logger);
        commit();
        close();

        System.out.println(getString(logger));
    }
}
