package com.example.application.components.data.database;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public abstract class HibernateConnection {
    private static SessionFactory sessionFactory;
    protected static Session session;

    protected static void start() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
        session = sessionFactory.openSession();
        session.beginTransaction();
    }

    protected static void commit() {
        session.getTransaction().commit();
    }

    protected static void close() {
        session.close();
        sessionFactory.close();
    }
}
