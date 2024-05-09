package com.example.application.components.data.database.hibernate;

import com.example.application.components.data.Chat;
import com.example.application.components.data.Message;
import com.example.application.components.data.User;
import com.example.application.services.encryption.AesKeys;
import com.example.application.services.encryption.StaticEncrypter;
import org.hibernate.query.Query;

import java.util.List;

public class MessageDAO extends HibernateConnection {
    public static List<Message> getMessages(Chat chat) {
        start_old();
        Query<Message> query = session.createQuery("FROM Message WHERE chat.id = :id ORDER BY localDateTime");
        query.setParameter("id", chat.getId());
        List<Message> messageList = query.getResultList();
        messageList.forEach(message -> message.setContent(StaticEncrypter.decryptByStaticKey(AesKeys.MESSAGE, message.getContent())));
        close_old();
        return messageList;
    }

    public static void sendMessage(Message message) {
        start_old();
        Chat chat = session.get(Chat.class, message.getChat().getId());
        User sender = session.get(User.class, message.getSender().getId());
        message.setChat(chat);
        message.setSender(sender);
        message.setContent(StaticEncrypter.encryptByStaticKey(AesKeys.MESSAGE, message.getContent()));
        session.persist(message);
        close_old();
    }

    public static void setRead(int messageId) {
        start_old();
        Message message = session.get(Message.class, messageId);
        message.setRead(true);
        session.update(message);
        commit();
        close_old();
    }
}
