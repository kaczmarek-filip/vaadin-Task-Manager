package com.example.application.components.dialogs;

import com.example.application.components.data.Team;
import com.example.application.components.data.database.DatabaseConnection;
import com.example.application.views.main.TeamsSite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;

public class DeleteConfirmDialog extends Dialog {
    public DeleteConfirmDialog(Team team) {
        Button deleteButton = new Button("Delete");

        setHeaderTitle("Confirm deletion");
        add("Are you sure to remove " + team.getName() + "?");

        deleteButton.addClickListener(e -> {

            DatabaseConnection databaseConnection = new DatabaseConnection();
            databaseConnection.deleteTeam(team.getId());

            UI.getCurrent().navigate(TeamsSite.class);

            Notification notification = new Notification("The team has been deleted", 5000, Notification.Position.BOTTOM_CENTER);
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            notification.open();
            close();
        });

        deleteButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY,
                ButtonVariant.LUMO_ERROR);

        deleteButton.getStyle().set("margin-right", "auto");

        getFooter().add(deleteButton);

        Button cancelButton = new Button("Cancel", (e) -> close());

        cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        getFooter().add(cancelButton);
    }
}
