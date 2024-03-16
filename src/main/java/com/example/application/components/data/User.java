package com.example.application.components.data;

import com.example.application.components.data.database.DatabaseConnection;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

/**
 * Singleton
 * User class
 */
@Getter @Setter
public class User {
    private int id;
    private String displayName;
    private String email;
//    private String password;

    private static User instance;

    private User() {}

    /**
     * @param id
     * @param displayName
     * @param email
     */
    public User(int id, String displayName, String email) {
        this.id = id;
        this.displayName = displayName;
        this.email = email;
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

    public static ArrayList<Team> getLoggedInUserTeams(){
        DatabaseConnection databaseConnection = new DatabaseConnection();
        ArrayList<Team> teamsByUser = databaseConnection.findTeamsByUser(User.getLoggedInUser());

        return teamsByUser;
    }
    public static ArrayList<User> getAllUsers(){
        DatabaseConnection databaseConnection = new DatabaseConnection();
        ArrayList<User> allUsers = databaseConnection.getAllUsers();

        return allUsers;
    }
}
