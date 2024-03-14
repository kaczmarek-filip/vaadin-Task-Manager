package com.example.application.components;

import lombok.Getter;

@Getter
public class TeamMembers {
    private int teamId;
    private User user;
    private TeamRoles role;


    public TeamMembers(int teamId, User user, TeamRoles role) {
        this.teamId = teamId;
        this.user = user;
        this.role = role;
    }
}
