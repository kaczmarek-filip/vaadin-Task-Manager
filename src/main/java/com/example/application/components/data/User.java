package com.example.application.components.data;

import lombok.Getter;
import lombok.Setter;

/**
 * Singleton
 * User class
 */
@Getter @Setter
public class User {
    private int id;
    private String displayName;
    private String email;
    private String password;

    private static User instance;

    private User() {}

    /**
     * @param id
     * @param displayName
     * @param email
     * @param password
     */
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

    /**
     * @return Singleton {@link User}
     */
    public static User getLoggedInUser(){
        return User.getInstance();
    }
    public static void logOut(){
        instance = null;
    }
}
