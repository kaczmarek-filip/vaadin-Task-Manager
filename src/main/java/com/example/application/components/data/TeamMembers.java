package com.example.application.components.data;

import lombok.Getter;

/**
 * Sprawdza użytkowników należących do konkretnego teamu
 */
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
