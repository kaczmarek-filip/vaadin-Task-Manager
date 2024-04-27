package com.example.application.components.data.database.hibernate;

import com.example.application.components.data.User;
import com.example.application.services.encryption.Encrypter;
import org.hibernate.query.Query;

import java.util.List;

public class UserDAO extends HibernateConnection {
    public static void registerUser(User user, String password) {
        start();

        user.setPassword(Encrypter.encrypt(password));
        session.persist(user);

        close();
    }

    public static boolean isEmailExists(String email) {
        start();
        try {
            Query query = session.createQuery("SELECT COUNT(*) FROM User WHERE email = :email");
            query.setParameter("email", email);
            Long count = (Long) query.uniqueResult();
            close();
            return count > 0;
        } catch (Exception e) {
            return false;
        }
    }
    public static User loginUser(String email, String password){
        start();
        Query<User> query = session.createQuery("FROM User WHERE email = :email AND password = :password");
        query.setParameter("email", email);
        query.setParameter("password", Encrypter.encrypt(password));
        User user = query.uniqueResult();
        close();
        return user;
    }
    public static List<User> getAllUsers(){
        start();
        Query<User> query = session.createQuery("FROM User ");
        List<User> users = query.list();
        close();
        return users;
    }
    @Deprecated
    public static User getUserById(int id){
        start();
        Query<User> query = session.createQuery("FROM User WHERE id = :id");
        query.setParameter("id", id);
        User user = query.uniqueResult();
        close();
        return user;
    }
}