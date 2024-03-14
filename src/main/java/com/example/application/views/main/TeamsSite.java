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
//@PermitAll
public class TeamsSite extends Navigation {
    HorizontalLayout horizontalLayout = new HorizontalLayout();
    DatabaseConnection databaseConnection = new DatabaseConnection();
    ArrayList<UserTeams> userTeamsArrayList;
    public TeamsSite() {
        super("Teams");

        userTeamsArrayList = databaseConnection.findTeamsByUser(User.getLoggedInUser());
//        if(teamsArrayList != null){
        if(!userTeamsArrayList.isEmpty()){
            for (UserTeams team : userTeamsArrayList) {
//                Notification.show(team.getName() + " " + team.getRole());
                TeamsElement teamsElement = new TeamsElement(team); // wywala błąd

                horizontalLayout.add(teamsElement);

                //TODO: dodać do DB innych użytkowników danej grupy.
                // odrębny formularz logowania, który nie wpuszcza niezalogowanego użytkownika dalej
            }
        }



//        horizontalLayout.add(new TeamsElement());
//        horizontalLayout.add(new TeamsElement());
//        horizontalLayout.add(new TeamsElement());
//        horizontalLayout.add(new TeamsElement());
//        horizontalLayout.add(new TeamsElement());
        horizontalLayout.addClassName("teamsHorizontalLayout");

        setContent(horizontalLayout);
    }
}
