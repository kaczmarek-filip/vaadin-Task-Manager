package com.example.application.views.main;

import com.example.application.components.data.Team;
import com.example.application.components.data.User;
import com.example.application.components.data.database.hibernate.TeamDAO;
import com.example.application.components.dialogs.CreateTeamDialog;
import com.example.application.components.elements.teams.TeamsElement;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;

import java.util.List;

/**
 * Teams management site
 * @see Navigation
 */
@Route("teams")
public class TeamsSite extends Navigation {
    HorizontalLayout horizontalLayout = new HorizontalLayout();
    List<Team> userTeamsArrayList;
    private Button createTeamButton = new Button("Create team");
    public TeamsSite() {
        super("Teams");
        userTeamsArrayList = TeamDAO.getUserTeams(User.getLoggedInUser());
        if(!userTeamsArrayList.isEmpty()){
            for (Team team : userTeamsArrayList) {
                TeamsElement teamsElement = new TeamsElement(team);

                horizontalLayout.add(teamsElement);
            }
        }
        horizontalLayout.addClassName("teamsHorizontalLayout");

        setContent(horizontalLayout);

        createTeamButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        createTeamButton.addClickListener(e -> {
            new CreateTeamDialog().open();
        });

        addTopNavButton(createTeamButton);
    }
}
