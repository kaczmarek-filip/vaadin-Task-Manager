package com.example.application.components.elements;

import com.example.application.components.data.Message;
import com.example.application.components.data.User;
import com.example.application.components.data.database.MessengerDB;
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
    private User user;
    private ZoneId zoneId = ZoneId.of("Europe/Warsaw");

    public MessengerElement() {
        setHeightFull();
    }
    public void removeView(){
        removeAll();
    }

    public void setView(User user){
        this.user = user;
        messageVerticalLayout();
    }
    private void messageVerticalLayout(){
        setHeightFull();
        add(chatWith(), messageList(), messageInput());
    }
    private Text chatWith(){
        return new Text("Chat with " + user.getDisplayName());
    }
    private MessageList messageList(){
        messageList = new MessageList();
        messageList.setHeightFull();
        messageList.setWidthFull();

        ArrayList<MessageListItem> messageListItems = new ArrayList<>();

        for(Message message : new MessengerDB().getMessages(User.getLoggedInUser(), user)){
            MessageListItem item = new MessageListItem(message.content(), message.localDateTime().atZone(zoneId).toInstant(), message.userFrom().getDisplayName());
            if (message.userFrom().equals(User.getLoggedInUser())){
                item.setUserColorIndex(2);
            }
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

            new MessengerDB().sendMessage(User.getInstance(), user, e.getValue(), LocalDateTime.now());
        });

        return messageInput;
    }
}
