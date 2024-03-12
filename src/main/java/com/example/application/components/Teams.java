package com.example.application.components;


import lombok.Getter;

@Getter
public class Teams {
    private String name;
    private TeamRoles role;

    public Teams(String name, TeamRoles role) {
        this.name = name;
        this.role = role;
    }
}
