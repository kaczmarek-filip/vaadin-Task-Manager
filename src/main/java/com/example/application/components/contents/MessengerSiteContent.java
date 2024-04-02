package com.example.application.components.contents;

import com.example.application.components.data.User;
import com.example.application.components.elements.MessengerElement;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class MessengerSiteContent extends HorizontalLayout {
    private final MessengerElement messengerElement = new MessengerElement();
    public MessengerSiteContent() {
        setWidthFull();
        setHeightFull();

        add(chatsScroller());

        add(messengerElement);
    }
    private Scroller chatsScroller(){
        Scroller scroller = new Scroller();

        VerticalLayout verticalLayout = new VerticalLayout();

        for (int i = 0; i < 10; i++) {
            for (User user : User.getAllUsers()){
                H1 h1 = new H1(user.getDisplayName());
                h1.addClickListener(e -> {
                    messengerElement.removeView();
                    messengerElement.setView(user);
                });
                verticalLayout.add(h1);
            }
        }

        scroller.setScrollDirection(Scroller.ScrollDirection.VERTICAL);
        scroller.setContent(verticalLayout);
        return scroller;
    }
}
