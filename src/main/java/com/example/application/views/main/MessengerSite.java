package com.example.application.views.main;


import com.example.application.components.contents.MessengerSiteContent;
import com.example.application.components.data.User;
import com.example.application.components.data.database.MessengerDB;
import com.example.application.components.dialogs.AddChatDialog;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Nav;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.context.annotation.Bean;

/**
 * Messenger site
 * @see Navigation
 */
@Route("messenger")
public class MessengerSite extends Navigation {
    public MessengerSite(){
        super("Messenger");
        MessengerSiteContent messengerSiteContent = new MessengerSiteContent();
        setContent(messengerSiteContent);

        Button button = new Button("Add chat");
        button.addClickListener(e -> {
            new AddChatDialog(messengerSiteContent).open();
        });
        addTopNavButton(button);
    }


}
