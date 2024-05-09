package com.example.application.components.data;

import com.example.application.services.session.SessionVaadin;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Singleton
 * User class
 */
@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String displayName;
    private String email;
    private String password;
    private boolean online;
    private byte[] avatar;

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

    public static User getLoggedInUser() {
        return SessionVaadin.getUser();
    }

    public static void logOut() {
        SessionVaadin.logout();
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
