package com.example.application.components.data;

import com.example.application.components.data.database.TeamDB;
import com.example.application.components.data.database.UserDB;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

/**
 * Singleton
 * User class
 */
@Getter
@Setter
public class User {
    private int id;
    private String displayName;
    private String email;
//    private String password;

    private static User instance;

    private User() {
    }

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
    public static User getLoggedInUser() {
        return User.getInstance();
    }

    public static void logOut() {
        instance = null;
    }

    public static ArrayList<Team> getLoggedInUserTeams() {
        TeamDB teamDB = new TeamDB();
        ArrayList<Team> teamsByUser = teamDB.findTeamsByUser(User.getLoggedInUser());

        return teamsByUser;
    }

    public static ArrayList<User> getAllUsers() {
        ArrayList<User> allUsers = new UserDB().getAllUsers();

        return allUsers;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;

        return result;
    }
}
