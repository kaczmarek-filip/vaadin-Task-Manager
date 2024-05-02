package com.example.application.components.data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
@Table(name = "tasks")
@Getter
@Setter
//TODO: Teamowe taski z podziałem na wykonane zadania
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "team_ID")
    private Team team;

    private String title;
    private String description;
    private LocalDate creationDate;
    private LocalDate deadline;

    @ManyToOne
    @JoinColumn(name = "creatorUserID")
    private User creator;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "taskholders", joinColumns = @JoinColumn(name = "taskID"), inverseJoinColumns = @JoinColumn(name = "user_ID"))
    private List<User> taskHolders;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<TaskHolder> holders;

    public static final int descriptionMaxLength = 200;
    private boolean isDone;

    @Transient
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public Task() {

    }

    public Task(String title, String description, LocalDate deadline) {
        this.title = title;
        this.description = description;
        this.deadline = deadline;
    }

    public void setAsTeamTask(Team team, List<User> taskHolders) {
        this.team = team;
        this.taskHolders = taskHolders;
    }

    public String getFormattedCreationDate() {
        return creationDate.format(formatter);
    }

    public String getFormattedDeadline() {
        return deadline.format(formatter);
    }
}
