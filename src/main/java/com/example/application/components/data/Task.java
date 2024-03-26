package com.example.application.components.data;

import com.example.application.components.data.database.TaskDB;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@Getter
public class Task {
    private int id;
    private Team team;
    private String title;
    //TODO: Uploading files
    private String description;
    private LocalDate creationDate;
    private LocalDate deadline;
    private User creator;
    private Set<User> holders;
    public static int descriptionMaxLength = 200;
    @Setter
    private boolean isDone;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    /**
     * Team's task
     */
    public Task(int id, Team team, boolean isDone, String title, String description, LocalDate creationDate, LocalDate deadline, User creator, Set<User> holders) {
        this.id = id;
        this.team = team;
        this.isDone = isDone;
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
    public Task(int id, boolean isDone, String title, String description, LocalDate creationDate, LocalDate deadline, User creator) {
        this.id = id;
        this.isDone = isDone;
        this.title = title;
        this.description = description;
        this.creationDate = creationDate;
        this.deadline = deadline;
        this.creator = creator;
    }

    public String getFormattedCreationDate() {
        return creationDate.format(formatter);
    }

    public String getFormattedDeadline() {
        return deadline.format(formatter);
    }

    public Set<User> getHolders() {
        if (holders == null){
            holders = new TaskDB().getTaskHolders(this);
        }

        return holders;
    }
}
