package com.example.application.views.main;


import com.example.application.components.data.TeamRoles;
import com.example.application.components.data.User;
import com.example.application.components.data.Team;
import com.example.application.components.data.database.TeamDB;
import com.example.application.components.dialogs.EditTeamDialog;
import com.example.application.components.dialogs.MakeTaskDialog;
import com.example.application.components.contents.SingleTeamSiteContent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.router.*;

import java.util.ArrayList;

@Route("teams/team_id")
public class SingleTeamSite extends Navigation implements BeforeEnterObserver, HasUrlParameter<String> {
    private int teamId;
    private Team team;
    private Button editButton = new Button("Edit");
    private Button makeTaskButton = new Button("Make task");

    public SingleTeamSite() {
        super("Team");


        editButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        editButton.addClickListener(e -> {
            new EditTeamDialog(team).open();
        });
        addTopNavButton(editButton);

        makeTaskButton.addClickListener(e -> {
            new MakeTaskDialog(team).open();
        });
        addTopNavButton(makeTaskButton);

    }


    @Override
    public void setParameter(BeforeEvent beforeEvent, String s) {
        teamId = Integer.parseInt(s);
        team = new TeamDB().findTeamByTeamId(teamId);
        siteTitle.setText(team.getName());

        editButton.setVisible(true);
        makeTaskButton.setVisible(true);

        if (Team.getUserTeamRole(User.getLoggedInUser(), teamId) != TeamRoles.OWNER) {
            editButton.setVisible(false);
            makeTaskButton.setVisible(false);
        }
        setContent(new SingleTeamSiteContent(team));
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        super.beforeEnter(beforeEnterEvent);

        ArrayList<Integer> teamIdsWithAccess = new ArrayList<>();

        for (Team teams : User.getLoggedInUserTeams()) {
            teamIdsWithAccess.add(teams.getId());
        }

        if (!teamIdsWithAccess.contains(teamId)) {
            Notification notification = new Notification("You do not have access to this team", 5000, Notification.Position.TOP_STRETCH);
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            notification.open();
            beforeEnterEvent.rerouteTo(TeamsSite.class);
        }
    }
}
