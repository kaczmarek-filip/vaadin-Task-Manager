package com.example.application.components.elements;

import com.example.application.components.data.Chat;
import com.example.application.components.data.Message;
import com.example.application.components.data.User;
import com.example.application.components.data.database.hibernate.MessageDAO;
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
public class MessengerElement extends VerticalLayout{
    private MessageList messageList;
    private Chat chat;
    private ZoneId zoneId = ZoneId.of("Europe/Warsaw");

    public MessengerElement() {
        setHeightFull();
    }
    public void removeView(){
        removeAll();
    }

    public void setView(Chat chat){
        this.chat = chat;
        messageVerticalLayout();
    }
    private void messageVerticalLayout(){
        setHeightFull();
        add(chatWith(), messageList(), messageInput());
    }
    private Text chatWith(){
        return new Text("Chat with " + chat.getUserTo().getDisplayName());
    }
    private MessageList messageList(){
        messageList = new MessageList();
        messageList.setHeightFull();
        messageList.setWidthFull();

        ArrayList<MessageListItem> messageListItems = new ArrayList<>();

        for (Message message : MessageDAO.getMessages(chat)){
            MessageListItem item = new MessageListItem(message.getContent(), message.getLocalDateTime().atZone(zoneId).toInstant(), message.getSender().getDisplayName());
            if (message.getSender().equals(User.getLoggedInUser())) item.setUserColorIndex(2);
            messageListItems.add(item);
        }

        messageList.setItems(messageListItems);
        return messageList;
    }
    private MessageInput messageInput(){
        MessageInput messageInput = new MessageInput();
        messageInput.setWidthFull();

        messageInput.addSubmitListener(e -> {
            MessageListItem item2 = new MessageListItem(e.getValue(), LocalDateTime.now().atZone(zoneId).toInstant(), User.getInstance().getDisplayName());
            item2.setUserColorIndex(2);
            item2.addClassNames("myMessage");
            List<MessageListItem> items = new ArrayList<>(messageList.getItems());
            items.add(item2);
            messageList.setItems(items);


            Message message = new Message(chat, User.getLoggedInUser(), e.getValue(), LocalDateTime.now());
            MessageDAO.sendMessage(message);
        });

        return messageInput;
    }
}
