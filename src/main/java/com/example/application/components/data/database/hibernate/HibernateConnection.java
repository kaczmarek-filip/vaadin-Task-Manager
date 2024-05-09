package com.example.application.components.data.database.hibernate;

import com.example.application.components.elements.components.MyNotification;
import com.vaadin.flow.component.notification.NotificationVariant;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public abstract class HibernateConnection {
    private static SessionFactory sessionFactory;
    protected static Session session;

    protected static void start_old() {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
            session = sessionFactory.openSession();
            session.beginTransaction();
        } catch (Exception e) {
            MyNotification.show("Database connection error", NotificationVariant.LUMO_ERROR, false);
        }

    }
    public static void start(){

    }

    protected static void commit() {
        session.getTransaction().commit();
    }

    protected static void close_old() {
        session.close();
        sessionFactory.close();
    }
    public static void flush(){
        start();
        session.flush();
        close();
    }
    public static void refresh(Object object){
        start_old();
        session.refresh(object);
        close_old();
    }
    public static void close(){

    }
}
