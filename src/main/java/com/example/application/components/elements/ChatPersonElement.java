package com.example.application.components.elements;

import com.example.application.components.data.Chat;
import com.example.application.components.data.User;
import com.example.application.components.data.database.hibernate.ChatDAO;

public class ChatPersonElement extends Element {
    private final Chat chat;
    private final MessengerElement messengerElement;
    private static ChatPersonElement lastClickedButton = null;

    //TODO: dodanie ikonki aktywności

    //TODO: Pozycjonowanie po ostatnich wiadomościach
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

        if (ChatDAO.isHaveUnreadMessages(chat)) {
            getElement().getStyle().set("background-color", MessagesColors.notRead).set("order", "-1");
        }

        setColors();
    }

    @Override
    public void listenerAction() {
        addClickListener(e -> {
            messengerElement.removeView();
            messengerElement.setView(chat);
            messengerElement.setRead();
        });
    }

    private void setColors() {
        addClickListener(event -> {
            if (lastClickedButton != null) {
                lastClickedButton.removeClassName("currentChat");
            }
            addClassName("currentChat");
            getElement().getStyle().set("background-color", MessagesColors.standard);
            lastClickedButton = this;
        });
    }
}
