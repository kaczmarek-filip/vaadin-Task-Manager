package com.example.application.views.main;

import com.example.application.components.data.Team;
import com.example.application.components.data.User;
import com.example.application.components.elements.TeamsElement;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;

/**
 * Teams management site
 * @see Navigation
 */
@Route("teams")
public class TeamsSite extends Navigation {
    HorizontalLayout horizontalLayout = new HorizontalLayout();
    ArrayList<Team> userTeamsArrayList;
    public TeamsSite() {
        super("Teams");
        userTeamsArrayList = User.getLoggedInUserTeams();
        if(!userTeamsArrayList.isEmpty()){
            for (Team team : userTeamsArrayList) {
                TeamsElement teamsElement = new TeamsElement(team);

                horizontalLayout.add(teamsElement);
            }
        }
        horizontalLayout.addClassName("teamsHorizontalLayout");

        setContent(horizontalLayout);

        addTopNavButton("Create team");
    }

    @Override
    protected Button addTopNavButton(String buttonText) {
        return super.addTopNavButton(buttonText);
        //TODO: Create listener
    }
}
