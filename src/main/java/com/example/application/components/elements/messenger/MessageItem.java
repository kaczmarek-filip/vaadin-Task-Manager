package com.example.application.components.elements.messenger;

import com.example.application.components.data.Message;
import com.example.application.components.data.User;
import com.example.application.services.BlobConverter;
import com.vaadin.flow.component.messages.MessageListItem;

import java.time.ZoneId;

public class MessageItem extends MessageListItem {

    private final ZoneId zoneId = ZoneId.of("Europe/Warsaw");

    private final Message message;

    public MessageItem(Message message) {
        super();
        this.message = message;

        addClassNames("message");

        setText(message.getContent());
        setTime(message.getLocalDateTime().atZone(zoneId).toInstant());
        setUserName(message.getSender().getDisplayName());
        setUserImageResource(BlobConverter.getAvatar(message.getSender()));

        ifSender();
        ifRead();
    }

    private void ifSender() {
        if (message.getSender().equals(User.getLoggedInUser())) {
            setUserName("You");
            addClassNames("myMessage");
        }
    }

    private void ifRead() {
        if (!message.isRead() && !message.getSender().equals(User.getLoggedInUser())) {
            addClassNames("notRead");
        }
    }
}
