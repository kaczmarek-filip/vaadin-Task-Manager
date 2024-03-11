package com.example.application.components;

public class SQLParser {
    public String createUser(User user) {
        String query = "INSERT INTO `users`(`email`, `password`, `displayName`) VALUES ('" + user.getEmail() + "','" + user.getPassword() + "','" + user.getDisplayName() + "')";

        return query;
    }

    public String isEmailExists(User user) {
        String query = "SELECT COUNT(*) FROM `users` WHERE email = '" + user.getEmail() + "';";
        return query;
    }

    public String loginUser(String email, String password) {
        String query = "SELECT * FROM `users` WHERE email = '" + email + "' AND password = '" + password + "'";
        return query;
    }

}
