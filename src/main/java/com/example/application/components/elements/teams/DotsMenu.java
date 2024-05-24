package com.example.application.components.elements.teams;

import com.example.application.components.data.Team;
import com.example.application.components.data.User;
import com.example.application.components.data.database.hibernate.TeamDAO;
import com.example.application.components.dialogs.DeleteConfirmDialog;
import com.example.application.components.elements.Element;
import com.example.application.components.elements.components.notifications.SimpleNotification;
import com.example.application.views.main.TeamsSite;
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.NotificationVariant;


public class DotsMenu extends Element {
    private final Team team;
    private final TeamsSite teamsSite;

    public DotsMenu(Team team, TeamsSite teamsSite) {
        super("dotsMenu");
        this.team = team;
        this.teamsSite = teamsSite;
        layout();
        listenerAction();
    }

    @Override
    public void layout() {
        Icon icon = VaadinIcon.MENU.create();

        ContextMenu contextMenu = new ContextMenu();
        contextMenu.setTarget(icon);
        contextMenu.setOpenOnClick(true);

        contextMenu.addItem("Leave team", e -> {
            DeleteConfirmDialog dialog = new DeleteConfirmDialog("Leave team", "Are you sure you want to leave team " + team.getName() + "?");
            dialog.setButtonText("Leave");
            dialog.setAction(buttonClickEvent -> {
                TeamDAO.deleteUser(team, User.getLoggedInUser());
                teamsSite.OnChangeReload();
                SimpleNotification.show("You left the team " + team.getName(), NotificationVariant.LUMO_ERROR, false);
                dialog.close();
            });
            dialog.open();
        });

        add(icon);
    }

    @Override
    public void listenerAction() {

    }
}
