package com.example.application.components.elements.messenger;

import com.example.application.components.data.User;
import com.example.application.services.session.AllSessions;
import com.example.application.services.session.SessionAttributes;
import com.vaadin.flow.server.VaadinSession;

public class MessageRealtime {

    public static void sent(User user){
        VaadinSession userSession = AllSessions.getUserSession(user);
        userSession.lock();
        MessengerElement messengerElement = (MessengerElement) userSession.getAttribute(SessionAttributes.MESSENGER_ELEMENT);


        if (messengerElement != null) {
            userSession.getUIs().forEach(ui -> {
                ui.access(messengerElement::replaceMessageList);
            });
        }

        userSession.unlock();
    }
}
