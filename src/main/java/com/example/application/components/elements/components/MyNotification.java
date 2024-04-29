package com.example.application.components.elements.components;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;

public class MyNotification {
    public static void show(String text, NotificationVariant notificationVariant, boolean refresh){
        if (refresh) UI.getCurrent().getPage().reload();
        Notification notification = new Notification(text, 5000, Notification.Position.BOTTOM_CENTER);
        notification.addThemeVariants(notificationVariant);
        notification.open();
    }
    public static void test(String text){
        Notification notification = new Notification(text, 5000, Notification.Position.BOTTOM_CENTER);
        notification.addThemeVariants(NotificationVariant.LUMO_PRIMARY);
        notification.open();
    }
}
