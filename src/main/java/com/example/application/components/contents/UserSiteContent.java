package com.example.application.components.contents;

import com.example.application.components.data.User;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class UserSiteContent extends HorizontalLayout {

    public UserSiteContent() {
        setAlignItems(Alignment.CENTER);
        add(leftSide(), rightSide());
    }
    private VerticalLayout leftSide(){
        VerticalLayout leftSide = new VerticalLayout();
        Span displayName = new Span(User.getLoggedInUser().getDisplayName());
//        Span email = new Span(User.getLoggedInUser().getEmail());
        Span email = new Span("example.example@example.com");
        VerticalLayout userInfo = new VerticalLayout(displayName, email);

        Details details = new Details("User Details", userInfo);
        details.setOpened(true);



        leftSide.add(details);
        return leftSide;
    }
    private VerticalLayout rightSide(){
        VerticalLayout rightSide = new VerticalLayout();
        MenuBar menuBar = new MenuBar();
        menuBar.addItem("Light");
        menuBar.addItem("Dark");

        rightSide.add(menuBar);
        return rightSide;
    }

}
