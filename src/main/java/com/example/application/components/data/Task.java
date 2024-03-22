package com.example.application.components.data;

import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Set;

@Getter
public class Task {
    private int id;
    private String title;
    private String description;
    private LocalDate creationDate;
    private LocalDate deadline;
    private User creator;
    private Set<User> holders;
    public static int descriptionMaxLength = 200;

    public Task(String title, String description, LocalDate creationDate, LocalDate deadline, User creator, Set<User> holders) {
        this.title = title;
        this.description = description;
        this.creationDate = creationDate;
        this.deadline = deadline;
        this.creator = creator;
        this.holders = holders;
    }

}
