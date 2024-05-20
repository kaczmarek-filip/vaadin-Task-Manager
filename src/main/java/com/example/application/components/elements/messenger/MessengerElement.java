package com.example.application.components.elements.messenger;

import com.example.application.components.data.Chat;
import com.example.application.components.data.Message;
import com.example.application.components.data.User;
import com.example.application.components.data.database.hibernate.MessageDAO;
import com.example.application.components.elements.components.notifications.NewMessageNotification;
import com.example.application.services.session.AllSessions;
import com.example.application.services.session.SessionAttributes;
import com.vaadin.flow.component.messages.MessageInput;
import com.vaadin.flow.component.messages.MessageList;
import com.vaadin.flow.component.messages.MessageListItem;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class MessengerElement extends VerticalLayout {

    private MessageList messageList;
    private Chat chat;

    public MessengerElement() {
        setHeightFull();
    }

    public void removeView() {
        removeAll();
    }

    public void setView(Chat chat) {
        this.chat = chat;
        messageVerticalLayout();

        AllSessions.getUserSession(User.getLoggedInUser()).setAttribute(SessionAttributes.MESSENGER_ELEMENT, this);
    }

    private void messageVerticalLayout() {
        setHeightFull();
        getStyle().set("padding", "0");
        add(messageList(), messageInput());
    }

    public void replaceMessageList(){
        removeView();
        setView(chat);
    }

    private MessageList messageList() {
        messageList = new MessageList();
        messageList.setHeightFull();
        messageList.setWidthFull();

        ArrayList<MessageListItem> messageListItems = new ArrayList<>();

        for (Message message : MessageDAO.getMessages(chat)) {

            MessageItem item = new MessageItem(message);
            messageListItems.add(item);
        }

        messageList.setItems(messageListItems);
        return messageList;
    }

    private MessageInput messageInput() {
        MessageInput messageInput = new MessageInput();
        messageInput.setWidthFull();

        messageInput.addSubmitListener(e -> {
            Message message = new Message(chat, User.getLoggedInUser(), e.getValue(), LocalDateTime.now());
            MessageItem item = new MessageItem(message);

            List<MessageListItem> items = new ArrayList<>(messageList.getItems());
            items.add(item);
            messageList.setItems(items);

            MessageDAO.sendMessage(message);
            new NewMessageNotification(message);
            MessageRealtime.sent(message.getChat().getUserTo());
        });

        return messageInput;
    }

    void setRead() {
        List<Message> messages = MessageDAO.getMessages(chat);
        for (Message message : messages) {
            if (!message.getSender().equals(User.getLoggedInUser())) {
                MessageDAO.setRead(message.getId());
            }
        }
    }
}
