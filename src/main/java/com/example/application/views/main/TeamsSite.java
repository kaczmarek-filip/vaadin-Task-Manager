package com.example.application.views.main;

import com.example.application.components.DatabaseConnection;
import com.example.application.components.TeamRoles;
import com.example.application.components.Teams;
import com.example.application.components.User;
import com.example.application.components.elements.TeamsElement;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

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
    ArrayList<Teams> teamsArrayList;
    public TeamsSite() {
        super("Teams");

        teamsArrayList = databaseConnection.findTeamsByUser(User.getLoggedInUser());
//        if(teamsArrayList != null){
        if(!teamsArrayList.isEmpty()){
            for (Teams team : teamsArrayList) {
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
