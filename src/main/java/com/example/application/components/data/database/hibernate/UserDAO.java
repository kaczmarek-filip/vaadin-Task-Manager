package com.example.application.components.data.database.hibernate;

import com.example.application.components.data.User;
import com.example.application.services.encryption.Encrypter;
import com.example.application.services.session.SessionVaadin;
import lombok.SneakyThrows;
import org.hibernate.query.Query;

import java.util.List;

public class UserDAO extends HibernateConnection {
    public static void registerUser(User user, String password) {
        start_old();

        user.setPassword(Encrypter.encrypt(password));
        session.persist(user);

        close_old();
    }

    public static boolean isEmailExists(String email) {
        start_old();
        try {
            Query query = session.createQuery("SELECT COUNT(*) FROM User WHERE email = :email");
            query.setParameter("email", email);
            Long count = (Long) query.uniqueResult();
            close_old();
            return count > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public static User loginUser(String email, String password) {
        start_old();
        Query<User> query = session.createQuery("FROM User WHERE email = :email AND password = :password");
        query.setParameter("email", email);
        query.setParameter("password", Encrypter.encrypt(password));
        User user = query.uniqueResult();
        close_old();
        return user;
    }

    public static List<User> getAllUsers() {
        start_old();
        Query<User> query = session.createQuery("FROM User ");
        List<User> users = query.list();
        close_old();
        return users;
    }

    @Deprecated
    public static User getUserById(int id) {
        start_old();
        Query<User> query = session.createQuery("FROM User WHERE id = :id");
        query.setParameter("id", id);
        User user = query.uniqueResult();
        close_old();
        return user;
    }

    public static void setOnline(boolean online) {
        start_old();
        Query query = session.createQuery("UPDATE User SET online = :online WHERE id = :id");
        query.setParameter("online", online).setParameter("id", SessionVaadin.getUser().getId());
        query.executeUpdate();
        close_old();
    }

    @SneakyThrows
    public static void updateAvatar(byte[] bytes) {
        start_old();

        User user = session.get(User.class, User.getLoggedInUser().getId());
        user.setAvatar(bytes);
        session.update(user);

        commit();
        close_old();
    }

    public static byte[] getAvatar(User user) {
        start_old();

        byte[] avatar = session.get(User.class, user.getId()).getAvatar();

        close_old();
        return avatar;
    }
}
