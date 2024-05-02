package com.example.application.components.elements.messenger;

import com.example.application.components.data.Chat;
import com.example.application.components.data.Message;
import com.example.application.components.data.User;
import com.example.application.components.data.database.hibernate.MessageDAO;
import com.example.application.services.BlobConverter;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.messages.MessageInput;
import com.vaadin.flow.component.messages.MessageList;
import com.vaadin.flow.component.messages.MessageListItem;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Component
public class MessengerElement extends VerticalLayout {
    private MessageList messageList;
    private Chat chat;
    private ZoneId zoneId = ZoneId.of("Europe/Warsaw");

    public MessengerElement() {
        setHeightFull();
    }

    public void removeView() {
        removeAll();
    }

    public void setView(Chat chat) {
        this.chat = chat;
        messageVerticalLayout();
    }

    private void messageVerticalLayout() {
        setHeightFull();
        add(chatWith(), messageList(), messageInput());
    }

    private Text chatWith() {
        return new Text("Chat with " + chat.getUserTo().getDisplayName());
    }

    private MessageList messageList() {
        messageList = new MessageList();
        messageList.setHeightFull();
        messageList.setWidthFull();

        ArrayList<MessageListItem> messageListItems = new ArrayList<>();

        for (Message message : MessageDAO.getMessages(chat)) {
            MessageListItem item = new MessageListItem(message.getContent(), message.getLocalDateTime().atZone(zoneId).toInstant(), message.getSender().getDisplayName());
            item.setUserImageResource(BlobConverter.getAvatar(message.getSender()));
            item.addClassNames("message");
            if (message.getSender().equals(User.getLoggedInUser())) {
                item.setUserName("You");
                item.addClassNames("myMessage");
            }
            if (!message.isRead() && !message.getSender().equals(User.getLoggedInUser())) {
                item.addClassNames("notRead");
            }
            messageListItems.add(item);
        }

        messageList.setItems(messageListItems);
        return messageList;
    }

    private MessageInput messageInput() {
        MessageInput messageInput = new MessageInput();
        messageInput.setWidthFull();

        messageInput.addSubmitListener(e -> {
            MessageListItem item2 = new MessageListItem(e.getValue(), LocalDateTime.now().atZone(zoneId).toInstant(), "You");
            item2.setUserImageResource(BlobConverter.getAvatar(User.getLoggedInUser()));
            item2.addClassNames("myMessage", "message");
            List<MessageListItem> items = new ArrayList<>(messageList.getItems());
            items.add(item2);
            messageList.setItems(items);


            Message message = new Message(chat, User.getLoggedInUser(), e.getValue(), LocalDateTime.now());
            MessageDAO.sendMessage(message);
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
