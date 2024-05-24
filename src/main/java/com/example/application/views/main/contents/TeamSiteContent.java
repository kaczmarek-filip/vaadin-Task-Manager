package com.example.application.views.main.contents;

import com.example.application.components.data.Team;
import com.example.application.components.data.User;
import com.example.application.components.data.database.hibernate.TeamDAO;
import com.example.application.components.elements.teams.TeamsElement;
import com.example.application.views.main.TeamsSite;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import java.util.List;

public class TeamSiteContent extends HorizontalLayout {

    public TeamSiteContent(TeamsSite  teamsSite) {

        List<Team> userTeamsArrayList = TeamDAO.getUserTeams(User.getLoggedInUser());

        if(!userTeamsArrayList.isEmpty()){
            for (Team team : userTeamsArrayList) {
                TeamsElement teamsElement = new TeamsElement(team, teamsSite);

                add(teamsElement);
            }
        }

        addClassName("teamsHorizontalLayout");
    }
}
