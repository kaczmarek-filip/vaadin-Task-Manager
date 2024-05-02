package com.example.application.components.data;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "taskholders")
@Getter @Setter
public class TaskHolder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "taskID")
    private Task task;

    @ManyToOne
    @JoinColumn(name = "user_ID")
    private User user;

    private boolean isPartDone;
}
