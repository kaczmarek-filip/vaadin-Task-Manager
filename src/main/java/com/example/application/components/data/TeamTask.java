package com.example.application.components.data;

import lombok.Getter;

import java.util.Set;

@Getter
public class TeamTask {
    private Task task;
    private Team team;
    private Set<User> holders;

    public TeamTask(Task task, Team team, Set<User> holders) {
        this.task = task;
        this.team = team;
        this.holders = holders;
    }
}
