package com.example.application.components.dialogs;

import com.example.application.components.data.Team;
import com.example.application.components.data.User;
import com.example.application.components.data.database.TeamDB;
import com.example.application.components.elements.components.CancelButton;
import com.example.application.views.main.TeamsSite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;

public class DeleteConfirmDialog extends Dialog {
    Button deleteButton = new Button("Delete");

    public DeleteConfirmDialog() {

        deleteButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY,
                ButtonVariant.LUMO_ERROR);

        deleteButton.getStyle().set("margin-right", "auto");


        getFooter().add(deleteButton);
        getFooter().add(new CancelButton(this));
    }

    public Dialog deleteTeam(Team team) {
        setHeaderTitle("Confirm deletion");
        add("Are you sure to remove " + team.getName() + "?");

        deleteButton.addClickListener(e -> {

            new TeamDB().deleteTeam(team.getId());

            UI.getCurrent().navigate(TeamsSite.class);

            Notification notification = new Notification("The team has been deleted", 5000, Notification.Position.BOTTOM_CENTER);
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            notification.open();
            close();
        });
        return this;
    }

    public Dialog deleteUser(Team team, User user) {
        setHeaderTitle("Confirm deletion");
        add("Are you sure to remove " + user.getDisplayName() + "?");

        deleteButton.addClickListener(e -> {

            new TeamDB().deleteUserFromTeam(team, user);

            Notification notification = new Notification(user.getDisplayName() + " has been deleted", 5000, Notification.Position.BOTTOM_CENTER);
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            notification.open();
            close();


            UI.getCurrent().getPage().reload();
        });

        return this;
    }
}
