package com.example.application.views.main;

import com.example.application.components.data.database.DatabaseConnection;
import com.example.application.components.data.UserTeams;
import com.example.application.components.data.User;
import com.example.application.components.elements.TeamsElement;
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
    DatabaseConnection databaseConnection = new DatabaseConnection();
    ArrayList<UserTeams> userTeamsArrayList;
    public TeamsSite() {
        super("Teams");

        userTeamsArrayList = databaseConnection.findTeamsByUser(User.getLoggedInUser());
        if(!userTeamsArrayList.isEmpty()){
            for (UserTeams team : userTeamsArrayList) {
                TeamsElement teamsElement = new TeamsElement(team);

                horizontalLayout.add(teamsElement);

                //TODO: scrollowany panel
            }
        }
        horizontalLayout.addClassName("teamsHorizontalLayout");

        setContent(horizontalLayout);
    }
}
