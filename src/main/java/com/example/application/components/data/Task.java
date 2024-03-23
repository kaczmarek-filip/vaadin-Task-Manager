package com.example.application.components.data;

import lombok.Getter;

import java.time.LocalDate;
import java.util.Set;

@Getter
public class Task {
    private int id;
    private Team team;
    private String title;
    private String description;
    private LocalDate creationDate;
    private LocalDate deadline;
    private User creator;
    private Set<User> holders;
    public static int descriptionMaxLength = 200;

    /**
     * Team's task
     */
    public Task(int id, Team team, String title, String description, LocalDate creationDate, LocalDate deadline, User creator, Set<User> holders) {
        this.id = id;
        this.team = team;
        this.title = title;
        this.description = description;
        this.creationDate = creationDate;
        this.deadline = deadline;
        this.creator = creator;
        this.holders = holders;
    }

    /**
     * User's own task
     */
    public Task(int id, String title, String description, LocalDate creationDate, LocalDate deadline, User creator) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.creationDate = creationDate;
        this.deadline = deadline;
        this.creator = creator;
    }
}
