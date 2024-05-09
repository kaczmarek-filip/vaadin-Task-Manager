package com.example.application.components.data.database.hibernate;

import com.example.application.components.data.Chat;
import com.example.application.components.data.Message;
import com.example.application.components.data.User;
import org.hibernate.query.Query;

import java.util.List;

public class ChatDAO extends HibernateConnection {
    public static List<Chat> getChats(User user) {
        start_old();
        Query<Chat> query = session.createQuery("FROM Chat WHERE user1.id = :id OR user2.id = :id");
        query.setParameter("id", user.getId());
        List<Chat> chatList = query.getResultList();
        close_old();
        return chatList;
    }

    public static Chat addChat(User user1, User user2) {
        start_old();
        Chat chat = new Chat();
        chat.setUser1(user1);
        chat.setUser2(user2);
        session.persist(chat);
        close_old();
        return chat;
    }

    public static boolean isHaveUnreadMessages(Chat chat) {
        start_old();
        Query<Message> query = session.createQuery("FROM Message WHERE chat.id = :chatId AND isRead = false ");
        query.setParameter("chatId", chat.getId());
        List<Message> messageList = query.getResultList();
        close_old();
        for (Message message : messageList) {
            if (!message.isRead() && !message.getSender().equals(User.getLoggedInUser())) {
                return true;
            }
        }
        return false;
    }
    public static void delete(Chat chat) {
        start_old();
        session.delete(chat);
        commit();
        close_old();
    }
}
