package com.example.application.components.data;

import lombok.Getter;

@Getter
public class Team {
    private int Id;
    private String name;

    public Team(int id, String name) {
        Id = id;
        this.name = name;
    }
}
