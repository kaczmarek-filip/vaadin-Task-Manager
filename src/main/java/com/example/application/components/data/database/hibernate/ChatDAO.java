package com.example.application.components.data.database.hibernate;

import com.example.application.components.data.Chat;
import com.example.application.components.data.Message;
import com.example.application.components.data.User;
import org.hibernate.query.Query;

import java.util.List;

public class ChatDAO extends HibernateConnection {
    public static List<Chat> getChats(User user) {
        start();
        Query<Chat> query = session.createQuery("FROM Chat WHERE user1.id = :id OR user2.id = :id");
        query.setParameter("id", user.getId());
        List<Chat> chatList = query.getResultList();
        close();
        return chatList;
    }

    public static Chat addChat(User user1, User user2) {
        start();
        Chat chat = new Chat();
        chat.setUser1(user1);
        chat.setUser2(user2);
        session.persist(chat);
        close();
        return chat;
    }

    public static boolean isHaveUnreadMessages(Chat chat) {
        start();
        Query query = session.createQuery("FROM Message WHERE chat.id = :chatId AND isRead = false ");
        query.setParameter("chatId", chat.getId());
        List<Message> messageList = query.getResultList();
        close();
        for (Message message : messageList) {
            if (!message.isRead() && !message.getSender().equals(User.getLoggedInUser())) {
                return true;
            }
        }
        return false;
    }
}
