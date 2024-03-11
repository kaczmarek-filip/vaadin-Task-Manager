package com.example.application.components;

import lombok.Getter;

import java.security.KeyStore;

@Getter
public class User {
    private int id;
    private String displayName;
    private String email;
    private String password;

    public User(int id, String displayName, String email, String password) {
        this.id = id;
        this.displayName = displayName;
        this.email = email;
        this.password = password;
    }
}
