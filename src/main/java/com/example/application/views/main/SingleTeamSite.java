package com.example.application.views.main;


import com.example.application.components.data.TeamRoles;
import com.example.application.components.data.User;
import com.example.application.components.data.Team;
import com.example.application.components.data.database.hibernate.TeamDAO;
import com.example.application.components.dialogs.EditTeamDialog;
import com.example.application.components.contents.SingleTeamSiteContent;
import com.example.application.components.dialogs.makeTask.MakeTeamTaskDialog;
import com.example.application.components.elements.components.OnSaveReload;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.router.*;

import java.util.ArrayList;

@Route("teams/team_id")
public class SingleTeamSite extends Navigation implements BeforeEnterObserver, HasUrlParameter<String>, OnSaveReload {
    private int teamId;
    private Team team;
    private Button editButton = new Button("Edit");
    private Button makeTaskButton = new Button("Make task");

    private SingleTeamSiteContent singleTeamSiteContent;

    public SingleTeamSite() {
        super("Team");


        editButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        editButton.addClickListener(e -> {
            EditTeamDialog editTeamDialog = new EditTeamDialog(team);
            editTeamDialog.setParent(this);
            editTeamDialog.open();
        });
        addTopNavButton(editButton);

        makeTaskButton.addClickListener(e -> {
            MakeTeamTaskDialog dialog = new MakeTeamTaskDialog(team);
            dialog.setParent(this);
            dialog.open();
        });
        addTopNavButton(makeTaskButton);

    }


    @Override
    public void setParameter(BeforeEvent beforeEvent, String s) {
        teamId = Integer.parseInt(s);
        team = TeamDAO.findTeamByTeamId(teamId);
        siteTitle.setText(team.getName());

        editButton.setVisible(true);
        makeTaskButton.setVisible(true);

        if (Team.getUserTeamRole(User.getLoggedInUser(), teamId) != TeamRoles.OWNER &&
                Team.getUserTeamRole(User.getLoggedInUser(), teamId) != TeamRoles.ADMIN)
        {
            editButton.setVisible(false);
            makeTaskButton.setVisible(false);
        }

        singleTeamSiteContent = new SingleTeamSiteContent(team);
        setContent(singleTeamSiteContent);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        super.beforeEnter(beforeEnterEvent);

        ArrayList<Integer> teamIdsWithAccess = new ArrayList<>();

        for (Team teams : TeamDAO.getUserTeams(User.getLoggedInUser())) {
            teamIdsWithAccess.add(teams.getId());
        }

        if (!teamIdsWithAccess.contains(teamId)) {
            Notification notification = new Notification("You do not have access to this team", 5000, Notification.Position.TOP_STRETCH);
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            notification.open();
            beforeEnterEvent.rerouteTo(TeamsSite.class);
        }
    }

    @Override
    public void OnChangeReload() {
        remove(singleTeamSiteContent);
        singleTeamSiteContent = new SingleTeamSiteContent(team);
        setContent(singleTeamSiteContent);

        siteTitle.setText(team.getName());
    }
}
