package com.example.application.components.elements.components.notifications;

import com.example.application.components.data.Message;
import com.example.application.components.data.User;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;

public class NewMessageNotification extends SessionNotification {

    private final Message message;

    public NewMessageNotification(Message message) {
        this.message = message;

        showNotification();
    }

    @Override
    protected Notification setupNotification() {
        Notification notification = new Notification("New message from " + message.getSender().getDisplayName());
        notification.setPosition(Notification.Position.TOP_START);
        notification.addThemeVariants(NotificationVariant.LUMO_PRIMARY);
        notification.setDuration(5000);
        return notification;
    }

    @Override
    protected User setupUser() {
        return message.getChat().getUserTo();
    }
}
