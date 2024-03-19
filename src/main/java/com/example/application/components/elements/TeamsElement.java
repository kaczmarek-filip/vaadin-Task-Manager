package com.example.application.components.elements;

import com.example.application.components.data.*;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;

import java.util.Map;

public class TeamsElement extends Element {

    private final Team team;

    public TeamsElement(Team team) {
        super("teamsElement");
        this.team = team;
        layout();
        listenerAction();
    }

    @Override
    public void layout() {

        H1 title = new H1(team.getName());
        title.getStyle().set("text-align", "center");

        Div membersDiv = new Div();
        membersDiv.setClassName("teamsMembersDiv");


        for(Map.Entry<User, TeamRoles> entry : Team.getAllTeamUsers(team.getId()).entrySet()){
            membersDiv.add(new TeamsMemberElement(entry.getKey(), entry.getValue()));
        }

        add(new TeamsUserRoleElement(team.getUsersInTeam().get(User.getLoggedInUser())));
        add(title);
        add(membersDiv);
    }

    @Override
    public void listenerAction() {
        addClickListener(e -> {
            getUI().ifPresent(ui -> ui.navigate("teams/team_id/" + team.getId()));
        });
    }
}
