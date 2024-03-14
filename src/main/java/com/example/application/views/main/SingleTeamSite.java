package com.example.application.views.main;


import com.example.application.components.data.database.DatabaseConnection;
import com.example.application.components.data.Team;
import com.vaadin.flow.router.*;

@Route("teams/team_id")
public class SingleTeamSite extends Navigation implements HasUrlParameter<String> {
    private String teamId;
    private Team team;
    private DatabaseConnection databaseConnection = new DatabaseConnection();
    public SingleTeamSite() {
        super("Team");
    }


    @Override
    public void setParameter(BeforeEvent beforeEvent, String s) {
        teamId = s;
        team = databaseConnection.findTeamNameByTeamId(Integer.parseInt(teamId));
        siteTitle.setText(team.getName());
    }

}
