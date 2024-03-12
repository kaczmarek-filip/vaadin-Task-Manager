package com.example.application.components;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class User {
    private int id;
    private String displayName;
    private String email;
    private String password;

    private static User instance;

    private User() {}

    public User(int id, String displayName, String email, String password) {
        this.id = id;
        this.displayName = displayName;
        this.email = email;
        this.password = password;
    }

    public static synchronized User getInstance() {
        if (instance == null) {
            instance = new User();
        }
        return instance;
    }
    public static User getLoggedInUser(){
        return User.getInstance();
    }
}
