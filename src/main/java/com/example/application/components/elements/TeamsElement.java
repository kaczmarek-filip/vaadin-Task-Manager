package com.example.application.components.elements;

import com.example.application.components.DatabaseConnection;
import com.example.application.components.TeamMembers;
import com.example.application.components.Teams;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;

public class TeamsElement extends Element {

    private final Teams team;
    private DatabaseConnection databaseConnection = new DatabaseConnection();

    public TeamsElement(Teams team) {
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

        for(TeamMembers teamMembers : databaseConnection.findUsersByTeam(team)){
            membersDiv.add(new TeamsMemberElement(teamMembers));
        }


//        membersDiv.add(new TeamsMemberElement());
//        membersDiv.add(new TeamsMemberElement());
//        membersDiv.add(new TeamsMemberElement());
//        membersDiv.add(new TeamsMemberElement());

        add(new TeamsUserRoleElement(team.getRole()));
        add(title);
        add(membersDiv);
    }

    @Override
    public void listenerAction() {

    }
}
