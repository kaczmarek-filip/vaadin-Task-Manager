package com.example.application.components.data.database;

import com.example.application.components.data.Message;
import com.example.application.components.data.Message2;
import com.example.application.components.data.User;
import com.example.application.services.encryption.StaticEncrypter;
import com.vaadin.flow.component.notification.Notification;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class MessengerDB extends DatabaseConnection {
    public void addChat(User user1, User user2){
        query = sqlParser.addChat(user1, user2);

        try {
            statement.executeUpdate(query);

            statement.close();
            connection.close();
        } catch (Exception e) {
            Notification.show(error, 5000, Notification.Position.BOTTOM_CENTER);
        }
    }
    public ArrayList<User> getUserChats(User user){
        query = sqlParser.getUserChats(user);
        ArrayList<User> userArrayList = new ArrayList<>();

        try (ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int DbUser1_ID = resultSet.getInt("user1_ID");
                int DbUser2_ID = resultSet.getInt("user2_ID");
                int DbUserId;
                String DbDisplayName;
                String DbEmail;

                if (DbUser1_ID == user.getId()){
                    DbUserId = DbUser2_ID;
                    DbDisplayName = resultSet.getString("user2_displayName");
                    DbEmail = resultSet.getString("user2_email");

                } else {
                    DbUserId = DbUser1_ID;
                    DbDisplayName = resultSet.getString("user1_displayName");
                    DbEmail = resultSet.getString("user1_email");
                }

                userArrayList.add(new User(DbUserId, DbDisplayName, DbEmail));
            }
            statement.close();
            connection.close();
        } catch (Exception e) {
            Notification.show(error, 5000, Notification.Position.BOTTOM_CENTER);
        }

        return userArrayList;
    }
    @Deprecated
    //TODO: Hibernate
    public void testHibernate(){
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Message2 message2 = new Message2();
        message2.setUser1_ID(1);
        message2.setUser2_ID(2);
        message2.setContent("blabla");
        message2.setLocalDateTime(LocalDateTime.now());

        session.save(message2);

        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
    }
    public void sendMessage(User userFrom, User userTo, String content, LocalDateTime localDateTime){
        query = sqlParser.sendMessage(userFrom, userTo, content, localDateTime);

        try {
            statement.executeUpdate(query);

            statement.close();
            connection.close();
        } catch (Exception e) {
            Notification.show(e.toString(), 5000, Notification.Position.BOTTOM_CENTER);
        }
    }
    public ArrayList<Message> getMessages(User user1, User user2){
        query = sqlParser.getMessages(user1, user2);
        ArrayList<Message> messages = new ArrayList<>();

        try (ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int DbUserFromId = resultSet.getInt("user_ID");
                String DbContent = resultSet.getString("content");
//                LocalDateTime DbLocalDateTime = LocalDateTime.from(resultSet.getTime("dateTime").toLocalTime());
                LocalDateTime localDateTime = resultSet.getTimestamp("dateTime").toLocalDateTime();

                DbContent = StaticEncrypter.decryptByStaticKey(DbContent);

                User user = new UserDB().getUserById(DbUserFromId);

                messages.add(new Message(user, DbContent, localDateTime));
            }
            statement.close();
            connection.close();
        } catch (Exception e) {
            Notification.show(e.toString(), 5000, Notification.Position.BOTTOM_CENTER);
        }

        return messages;
    }
}
