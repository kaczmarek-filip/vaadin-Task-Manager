package com.example.application.components.elements;

import com.example.application.components.data.Chat;
import com.example.application.components.data.User;

public class ChatPersonElement extends Element{
    private final Chat chat;
    private final MessengerElement messengerElement;

    public ChatPersonElement(MessengerElement messengerElement, Chat chat) {
        super("chatScrollerElement");
        this.chat = chat;
        this.messengerElement = messengerElement;

        layout();
        listenerAction();
    }

    @Override
    public void layout() {
        setWidthFull();
        User user = chat.getUserTo();
        setText(user.getDisplayName());
    }

    @Override
    public void listenerAction() {
        addClickListener(e -> {
            messengerElement.removeView();
            messengerElement.setView(chat);
        });
    }
}
