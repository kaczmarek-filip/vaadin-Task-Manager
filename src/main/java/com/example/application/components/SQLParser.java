package com.example.application.components;

public class SQLParser {
    /**
     * @param user {@link User} object to save in DB
     * @return query
     * @see DatabaseConnection
     */
    public String createUser(User user) {
        String query = "INSERT INTO `users`(`email`, `password`, `displayName`) VALUES ('" + user.getEmail() + "','" + user.getPassword() + "','" + user.getDisplayName() + "')";

        return query;
    }

    /**
     * @param user {@link User} object to check if email already exists
     * @return query
     */
    public String isEmailExists(User user) {
        String query = "SELECT COUNT(*) FROM `users` WHERE email = '" + user.getEmail() + "';";
        return query;
    }

    /**
     * @param email from {@link LoginDialog}
     * @param password from {@link LoginDialog}
     * @return query
     */
    public String loginUser(String email, String password) {
        String query = "SELECT * FROM `users` WHERE email = '" + email + "' AND password = '" + password + "'";
        return query;
    }

}
