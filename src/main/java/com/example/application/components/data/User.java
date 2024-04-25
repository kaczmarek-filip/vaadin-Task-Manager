package com.example.application.components.data;

import com.example.application.components.data.database.HibernateTeam;
import com.example.application.components.data.database.sql.SQLTeamDB;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Singleton
 * User class
 */
@Entity
@Getter @Setter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String displayName;
    private String email;
    private String password;

    private static User instance;

    public User() {
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

//    @Deprecated
//    public static List<Team> getLoggedInUserTeams() {
////        SQLTeamDB SQLTeamDB = new SQLTeamDB();
////        ArrayList<Team> teamsByUser = SQLTeamDB.findTeamsByUser(User.getLoggedInUser());
//        List<Team> teamsByUser = HibernateTeam.getUserTeams(User.getLoggedInUser());
//
//        return teamsByUser;
//    }

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
