package com.example.application.components.elements.components.notifications;

import com.example.application.components.data.Task;
import com.example.application.components.data.Team;
import com.example.application.components.data.User;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;

public class NewTeamTaskNotification extends SessionNotification{


    private final Team team;
    private final User user;

    public NewTeamTaskNotification(Team team, User user) {
        this.team = team;
        this.user = user;

        showNotification();
    }

    @Override
    protected Notification setupNotification() {
        Notification notification = new Notification("New task in " + team.getName());
        notification.setPosition(Notification.Position.TOP_START);
        notification.addThemeVariants(NotificationVariant.LUMO_PRIMARY);
        notification.setDuration(5000);
        return notification;
    }

    @Override
    protected User setupUser() {
        return user;
    }
}
