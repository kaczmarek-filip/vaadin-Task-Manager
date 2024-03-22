package com.example.application.components.data;

import com.example.application.components.data.database.TeamDB;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class Team {
    private int id;
    private String name;
    private String motto;
    private Map<User, TeamRoles> usersInTeam = new HashMap<>();
    public static int mottoCharLimit = 200;

    public Team(int id, String name, String motto, Map<User, TeamRoles> usersInTeam) {
        this.id = id;
        this.name = name;
        this.motto = motto;
        this.usersInTeam = usersInTeam;
    }

    public static Map<User, TeamRoles> getAllTeamUsers(int teamId) {
//        TeamDB teamDB = new TeamDB();
        return new TeamDB().findUsersInTeam(teamId);
    }

    public static TeamRoles getUserTeamRole(User user, int teamId) {

        for (Map.Entry<User, TeamRoles> userTeamRolesEntry : Team.getAllTeamUsers(teamId).entrySet()) {
            User entryKey = userTeamRolesEntry.getKey();
            TeamRoles entryValue = userTeamRolesEntry.getValue();

            if (entryKey.equals(user)) {
                return entryValue;
            }

        }

        return null;
    }
}
