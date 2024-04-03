package com.example.application.components.elements;

import com.example.application.components.data.User;
import com.example.application.components.data.database.MessengerDB;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.messages.MessageInput;
import com.vaadin.flow.component.messages.MessageList;
import com.vaadin.flow.component.messages.MessageListItem;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MessengerElement extends VerticalLayout{
    private MessageList messageList;
    private User user;

    public MessengerElement() {
        setHeightFull();
    }
    public void removeView(){
        removeAll();
    }
    public void setView(User user){
        messageVerticalLayout(user);
        this.user = user;
    }

    private void messageVerticalLayout(User user){
        setHeightFull();
        add(chatWith(user), messageList(), messageInput());
    }
    private Text chatWith(User user){
        Text text = new Text("Chat with " + user.getDisplayName());
        return text;
    }
    private MessageList messageList(){
        messageList = new MessageList();
        messageList.setHeightFull();
        messageList.setWidthFull();

        Instant instant = LocalDate.now().atStartOfDay().toInstant(ZoneOffset.UTC);

        MessageListItem item1 = new MessageListItem("testowo", instant, User.getLoggedInUser().getDisplayName());
        MessageListItem item2 = new MessageListItem("testowo2", instant, "Janusz");

        messageList.setItems(item1, item2);
        return messageList;
    }
    private MessageInput messageInput(){
        MessageInput messageInput = new MessageInput();
        messageInput.setWidthFull();

        messageInput.addSubmitListener(e -> {
            ZoneId zoneId = ZoneId.of("Europe/Warsaw");
            MessageListItem item2 = new MessageListItem(e.getValue(), LocalDateTime.now().atZone(zoneId).toInstant(), User.getInstance().getDisplayName());
            item2.setUserColorIndex(1);
            item2.addClassNames("myMessage");
            List<MessageListItem> items = new ArrayList<>(messageList.getItems());
            items.add(item2);
            messageList.setItems(items);

            new MessengerDB().sendMessage(User.getInstance(), user, e.getValue(), LocalDateTime.now());
        });

        return messageInput;
    }
}
