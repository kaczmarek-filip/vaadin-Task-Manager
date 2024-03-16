package com.example.application.views.main;


import com.example.application.components.data.User;
import com.example.application.components.data.database.DatabaseConnection;
import com.example.application.components.data.Team;
import com.example.application.components.dialogs.CreateTeamDialog;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.router.*;

import java.util.ArrayList;

@Route("teams/team_id")
public class SingleTeamSite extends Navigation implements BeforeEnterObserver, HasUrlParameter<String> {
    private int teamId;
    private Team team;
    private DatabaseConnection databaseConnection = new DatabaseConnection();
    public SingleTeamSite() {
        super("Team");
        addTopNavButton("Create team", ButtonVariant.LUMO_SUCCESS).addClickListener(e -> {
           new CreateTeamDialog().open();
        });
        addTopNavButton("Edit team", ButtonVariant.LUMO_CONTRAST);
    }


    @Override
    public void setParameter(BeforeEvent beforeEvent, String s) {
        teamId = Integer.parseInt(s);
        team = databaseConnection.findTeamNameByTeamId(teamId);
        siteTitle.setText(team.getName());
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        super.beforeEnter(beforeEnterEvent);

        ArrayList<Integer> teamIdsWithAccess = new ArrayList<>();

        for (Team teams : User.getLoggedInUserTeams()){
            teamIdsWithAccess.add(teams.getId());
        }

        if(!teamIdsWithAccess.contains(teamId)){
//            Notification.show("You do not have access to this team", 5000, Notification.Position.TOP_CENTER);
            Notification notification = new Notification("You do not have access to this team", 5000, Notification.Position.TOP_STRETCH);
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            notification.open();
            beforeEnterEvent.rerouteTo(TeamsSite.class);
        }
    }
}
