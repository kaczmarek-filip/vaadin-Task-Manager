package com.example.application.components.data;


import lombok.Getter;

import java.util.ArrayList;

/**
 * Sprawdza teamy do których należy poszczególny użytkownik
 */
@Getter
@Deprecated
public class UserTeams {
//    private int teamId;
//    private String name;
    private User user;
    private Team team;
//    private ArrayList<Team> teams;
    private TeamRoles role;


//    public UserTeams(int teamId, String name, TeamRoles role) {
//        this.teamId = teamId;
//        this.name = name;
//        this.role = role;
//    }

    public UserTeams(User user, Team team, TeamRoles role) {
        this.user = user;
        this.team = team;
        this.role = role;
    }

    public UserTeams(User user, Team team) {
        this.user = user;
        this.team = team;
    }
    //    public UserTeams(Team team, TeamRoles role) {
//        this.team = team;
//        this.role = role;
//    }
//    public UserTeams(int teamId, String name) {
//        this.teamId = teamId;
//        this.name = name;
//    }
}
