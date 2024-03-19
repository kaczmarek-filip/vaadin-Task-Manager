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

    public Team(int id, String name, String motto, Map<User, TeamRoles> usersInTeam) {
        this.id = id;
        this.name = name;
        this.motto = motto;
        this.usersInTeam = usersInTeam;
    }

    @Deprecated
    public static Map<User, TeamRoles> getAllTeamUsers(int teamId) {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        return databaseConnection.findUsersInTeam(teamId);
    }

    //TODO: przekminić metodę, która zwaraca rolę użytkownika w danym teamie i na tej podstawie zrobić klasę uprawnień
}
