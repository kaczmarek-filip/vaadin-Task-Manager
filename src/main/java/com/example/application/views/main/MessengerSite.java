package com.example.application.views.main;


import com.example.application.views.main.contents.MessengerSiteContent;
import com.example.application.components.dialogs.AddChatDialog;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.router.Route;

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
