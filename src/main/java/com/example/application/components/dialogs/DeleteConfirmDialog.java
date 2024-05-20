package com.example.application.components.dialogs;

import com.example.application.components.data.Team;
import com.example.application.components.data.TeamMember;
import com.example.application.components.data.User;
import com.example.application.components.data.database.hibernate.HibernateConnection;
import com.example.application.components.data.database.hibernate.TeamDAO;
import com.example.application.components.elements.components.CancelButton;
import com.example.application.components.elements.components.notifications.RemoveFromTeamNotification;
import com.example.application.components.elements.components.notifications.SimpleNotification;
import com.example.application.views.main.TeamsSite;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import lombok.Setter;

public class DeleteConfirmDialog extends Dialog {
    Button deleteButton = new Button("Delete");

    @Setter
    private EditTeamDialog parent;

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

            TeamDAO.deleteTeam(team);

            UI.getCurrent().navigate(TeamsSite.class);

            SimpleNotification.show("Team has been deleted", NotificationVariant.LUMO_ERROR, false);

            for (TeamMember teamMember : team.getTeamMembers()){
                new RemoveFromTeamNotification(team, teamMember.getUser());
            }


            close();
        });
        return this;
    }

    public Dialog deleteUser(Team team, User user) {
        setHeaderTitle("Confirm deletion");
        add("Are you sure to remove " + user.getDisplayName() + "?");

        deleteButton.addClickListener(e -> {

            TeamDAO.deleteUser(team, user);

            SimpleNotification.show(user.getDisplayName() + " has been deleted", NotificationVariant.LUMO_ERROR, false);
            close();

            new RemoveFromTeamNotification(team, user);


            HibernateConnection.refresh(team);
            parent.OnChangeReload();
        });

        return this;
    }

    public DeleteConfirmDialog(String header, String content) {
        setHeaderTitle(header);
        add(content);
        getFooter().add(new CancelButton(this));
    }
    public void setAction(ComponentEventListener<ClickEvent<Button>> componentEventListener){
        Button deleteButton = new Button("Delete");
        deleteButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);

        deleteButton.addClickListener(componentEventListener);
        getFooter().add(deleteButton);
    }
}
