package com.example.application.components.elements;

import com.example.application.components.data.User;

public class ChatPersonElement extends Element{
    private final User user;
    private final MessengerElement messengerElement;

    public ChatPersonElement(MessengerElement messengerElement, User user) {
        super("chatScrollerElement");
        this.user = user;
        this.messengerElement = messengerElement;

        layout();
        listenerAction();
    }

    @Override
    public void layout() {
        setWidthFull();
        setText(user.getDisplayName());
    }

    @Override
    public void listenerAction() {
        addClickListener(e -> {
            messengerElement.removeView();
            messengerElement.setView(user);
        });
    }
}
