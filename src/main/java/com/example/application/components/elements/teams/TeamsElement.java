package com.example.application.components.elements.teams;

import com.example.application.components.data.*;
import com.example.application.components.data.database.hibernate.TeamDAO;
import com.example.application.components.elements.Element;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.Scroller;

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
        Scroller scroller = new Scroller();
        scroller.setScrollDirection(Scroller.ScrollDirection.VERTICAL);
        scroller.setHeight("25vh");


        for(TeamMember teamMember : TeamDAO.findUsersInTeam(team.getId())){
            membersDiv.add(new TeamsMemberElement(teamMember));
        }

        scroller.setContent(membersDiv);
        add(new TeamsUserRoleElement(TeamDAO.getUserRole(team, User.getLoggedInUser())));
        add(title);
        add(scroller);
    }

    @Override
    public void listenerAction() {
        addClickListener(e -> {
            getUI().ifPresent(ui -> ui.navigate("teams/team_id/" + team.getId()));
        });
    }
}
