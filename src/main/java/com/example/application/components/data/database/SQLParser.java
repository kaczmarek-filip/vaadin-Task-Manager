package com.example.application.components.data.database;

import com.example.application.components.data.User;
import com.example.application.components.data.UserTeams;
import com.example.application.components.data.database.DatabaseConnection;

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
     * @param email from {@link com.example.application.components.dialogs.LoginDialog}
     * @param password from {@link com.example.application.components.dialogs.LoginDialog}
     * @return query
     */
    public String loginUser(String email, String password) {
        String query = "SELECT * FROM `users` WHERE email = '" + email + "' AND password = '" + password + "'";
        return query;
    }

    public String findTeamsByUser(User user){
        String query = "SELECT teams.ID AS ID, teams.name AS name, team_member.role AS role FROM teams INNER JOIN team_member ON teams.ID = team_member.team_ID INNER JOIN users ON team_member.user_ID = users.ID WHERE users.ID = " + user.getId();
        return query;
    }
    public String findUsersByTeam(UserTeams userTeams){
        String query = "SELECT team_member.team_ID as team_ID, users.*, team_member.role as role FROM users INNER JOIN team_member ON users.ID = team_member.user_ID WHERE team_member.team_ID = " + userTeams.getId();
        return query;
    }
    public String findTeamNameByTeamId(int teamId){
        String query = "SELECT * FROM teams WHERE ID = " + teamId;
        return query;
    }
}
