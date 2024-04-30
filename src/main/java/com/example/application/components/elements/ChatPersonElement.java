package com.example.application.components.elements;

import com.example.application.components.data.Chat;
import com.example.application.components.data.User;
import com.example.application.components.data.database.hibernate.ChatDAO;
import com.example.application.components.elements.colors.MessagesColors;

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

//        Div div = new Div();
//        div.getElement().getStyle()
//                .set("height", "12px")
//                .set("width", "12px")
//                .set("border-radius", "50%")
//                .set("display", "inline-block")
//                .set("background-color", "black")
//                        .set("position", "absolute");
//                        .set("float", "right");
//        div.setText("Chciałbym być marynarzem");
//        add(div);
        add(ActiveDot.get(chat.getUserTo()));

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
