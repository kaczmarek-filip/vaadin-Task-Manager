package com.example.application.components.elements.components.notifications;

import com.example.application.components.data.User;
import com.example.application.services.session.AllSessions;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.server.VaadinSession;


public abstract class SessionNotification {

    protected void showNotification() {
        VaadinSession vaadinSession = AllSessions.getUserSession(setupUser());

        if (vaadinSession != null) {
            vaadinSession.lock();

            vaadinSession.getUIs().forEach(ui -> {
                ui.access(() -> {
                    ui.setPollInterval(100);
                    setupNotification().open();
                });
            });

            vaadinSession.unlock();
        }
    }

    protected abstract Notification setupNotification();

    protected abstract User setupUser();
}
