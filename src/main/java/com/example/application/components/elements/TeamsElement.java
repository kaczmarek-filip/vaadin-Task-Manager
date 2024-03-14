package com.example.application.components.elements;

import com.example.application.components.Teams;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;

public class TeamsElement extends Element {

    private final Teams team;

    public TeamsElement(Teams team) {
        super("teamsElement");
        this.team = team;
        layout();
        listenerAction();
    }

    @Override
    public void layout() {
//        cssSelector = "teamsElement";

        H1 title = new H1(team.getName());
        title.getStyle().set("text-align", "center");

        Div membersDiv = new Div();
        membersDiv.setClassName("teamsMembersDiv");

        membersDiv.add(new TeamsMemberElement());
        membersDiv.add(new TeamsMemberElement());
        membersDiv.add(new TeamsMemberElement());
        membersDiv.add(new TeamsMemberElement());
        membersDiv.add(new TeamsMemberElement());

        add(new TeamsUserRoleElement(team.getRole()));
        add(title);
        add(membersDiv);
    }

    @Override
    public void listenerAction() {

    }
}
