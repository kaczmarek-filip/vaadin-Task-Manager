package com.example.application.components;


import lombok.Getter;

@Getter
public class Teams {
    private int id;
    private String name;
    private TeamRoles role;


    public Teams(int id, String name, TeamRoles role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }
}
