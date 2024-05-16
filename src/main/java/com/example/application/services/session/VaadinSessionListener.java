package com.example.application.services.session;

import com.vaadin.flow.server.*;
import org.springframework.stereotype.Component;


@Component
public class VaadinSessionListener implements VaadinServiceInitListener {

    @Override
    public void serviceInit(ServiceInitEvent serviceInitEvent) {
        serviceInitEvent.getSource().addSessionInitListener(this::onInit);
        serviceInitEvent.getSource().addSessionDestroyListener(this::onDestroy);

        serviceInitEvent.getSource().setSystemMessagesProvider(this::sessionExpiredMessage);
    }

    private SystemMessages sessionExpiredMessage(SystemMessagesInfo systemMessagesInfo) {
        CustomizedSystemMessages messages = new CustomizedSystemMessages();
        messages.setSessionExpiredCaption("Session expired");
        messages.setSessionExpiredMessage("Take note of any unsaved data, and click here or press ESC key to continue.");
        messages.setSessionExpiredURL("/login");
        messages.setSessionExpiredNotificationEnabled(true);
        return messages;
    }

    private void onInit(SessionInitEvent sessionInitEvent) {
        System.err.println("Sesja Vaadin stworzona");
        System.err.println("Session ID: " + sessionInitEvent.getSession().getSession().getId());

        sessionInitEvent.getSession().getSession().setMaxInactiveInterval(24 * 60 * 60);
    }

    private void onDestroy(SessionDestroyEvent sessionDestroyEvent) {
        System.err.println("Sesja Vaadin zamknięta");
        System.err.println("Session ID: " + sessionDestroyEvent.getSession().getSession().getId());

    }


}
