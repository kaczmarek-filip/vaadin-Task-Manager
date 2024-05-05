package com.example.application.components.contents;

import com.example.application.components.data.Chat;
import com.example.application.components.data.User;
import com.example.application.components.data.database.hibernate.ChatDAO;
import com.example.application.components.elements.messenger.ChatPersonElement;
import com.example.application.components.elements.messenger.MessengerElement;
import com.example.application.components.elements.components.ChatCreateCallback;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class MessengerSiteContent extends HorizontalLayout implements ChatCreateCallback {
    private final MessengerElement messengerElement = new MessengerElement();
    private final static int scrollerWithInPercent = 20;
    private final static int messengerWidthInPercent = 80;
    private VerticalLayout verticalLayout = new VerticalLayout();

    public MessengerSiteContent() {
        setWidthFull();
        setHeightFull();
        getStyle().set("gap", "0");


        add(chatsScroller());
        messengerElement.setWidth(messengerWidthInPercent, Unit.PERCENTAGE);
        add(messengerElement);
    }

    private Scroller chatsScroller() {
        Scroller scroller = new Scroller();

        for (Chat chat : ChatDAO.getChats(User.getLoggedInUser())) {
            verticalLayout.add(new ChatPersonElement(messengerElement, chat));

        }
        scroller.setWidth(scrollerWithInPercent, Unit.PERCENTAGE);

        scroller.setScrollDirection(Scroller.ScrollDirection.VERTICAL);
        scroller.setContent(verticalLayout);
        return scroller;
    }

    @Override
    public void onSave(Chat chat) {
        verticalLayout.add(new ChatPersonElement(messengerElement, chat));
    }
}
