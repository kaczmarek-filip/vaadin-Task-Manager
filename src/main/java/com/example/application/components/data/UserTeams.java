package com.example.application.components.data;


import com.example.application.components.data.TeamRoles;
import lombok.Getter;

/**
 * Sprawdza teamy do których należy poszczególny użytkownik
 */
@Getter
public class UserTeams {
    private int id;
    private String name;
    private TeamRoles role;


    public UserTeams(int id, String name, TeamRoles role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }
}
