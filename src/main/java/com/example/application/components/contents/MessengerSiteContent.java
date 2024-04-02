package com.example.application.components.contents;

import com.example.application.components.data.User;
import com.example.application.components.elements.ChatPersonElement;
import com.example.application.components.elements.MessengerElement;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class MessengerSiteContent extends HorizontalLayout {
    private final MessengerElement messengerElement = new MessengerElement();
    private final static int scrollerWithInPercent = 20;
    private final static int messengerWidthInPercent = 80;
    public MessengerSiteContent() {
        setWidthFull();
        setHeightFull();

        add(chatsScroller());
        messengerElement.setWidth(messengerWidthInPercent, Unit.PERCENTAGE);
        add(messengerElement);
    }
    private Scroller chatsScroller(){
        Scroller scroller = new Scroller();

        VerticalLayout verticalLayout = new VerticalLayout();

        for (int i = 0; i < 10; i++) {
            for (User user : User.getAllUsers()){

                verticalLayout.add(new ChatPersonElement(messengerElement, user));

            }
        }
        scroller.setWidth(scrollerWithInPercent, Unit.PERCENTAGE);

        scroller.setScrollDirection(Scroller.ScrollDirection.VERTICAL);
        scroller.setContent(verticalLayout);
        return scroller;
    }
}
