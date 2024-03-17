package com.example.application.components.data;

import com.example.application.components.data.database.DatabaseConnection;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class Team {
    private int id;
    private String name;
    private String motto;
    private Map<User, TeamRoles> usersInTeam = new HashMap<>();

//    public Team(int id, String name) {
//        this.id = id;
//        this.name = name;
//    }

    public Team(int id, String name, String motto, Map<User, TeamRoles> usersInTeam) {
        this.id = id;
        this.name = name;
        this.motto = motto;
        this.usersInTeam = usersInTeam;
    }

    @Deprecated
    public static Map<User, TeamRoles> getAllTeamUsers(Team team) {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        return databaseConnection.findUsersInTeam(team.getId());
    }
}
