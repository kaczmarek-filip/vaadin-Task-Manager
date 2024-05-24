package com.example.application.components.elements.teams;

import com.example.application.components.data.*;
import com.example.application.components.data.database.hibernate.TeamDAO;
import com.example.application.components.elements.Element;
import com.example.application.views.main.TeamsSite;
import com.vaadin.flow.component.html.H1;

public class TeamsElement extends Element {

    private final Team team;
    private final TeamsSite teamsSite;

    public TeamsElement(Team team, TeamsSite teamsSite) {
        super("teamsElement");
        this.team = team;
        this.teamsSite = teamsSite;
        layout();
        listenerAction();
    }

    @Override
    public void layout() {

        H1 title = new H1(team.getName());
        title.addClassName("teamsTitle");
        add(new UnDoneTasksCounter(team));
        add(new TeamsUserRoleElement(TeamDAO.getUserRole(team, User.getLoggedInUser())));
        add(title);

        if (!TeamDAO.getUserRole(team, User.getLoggedInUser()).equals(TeamRoles.OWNER)){
            add(new DotsMenu(team, teamsSite));
        }
    }

    @Override
    public void listenerAction() {
        addClickListener(e -> {
            getUI().ifPresent(ui -> ui.navigate("teams/team_id/" + team.getId()));
        });
    }
}
