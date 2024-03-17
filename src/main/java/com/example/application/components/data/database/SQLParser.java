package com.example.application.components.data.database;

import com.example.application.components.data.Team;
import com.example.application.components.data.TeamRoles;
import com.example.application.components.data.User;

import java.util.ArrayList;
import java.util.Set;

public class SQLParser {
    /**
     * @param user {@link User} object to save in DB
     * @return query
     * @see DatabaseConnection
     */
    public String createUser(User user, String password) {
        String query = "INSERT INTO `users`(`email`, `password`, `displayName`) VALUES ('" + user.getEmail() + "','" + password + "','" + user.getDisplayName() + "')";

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
        String query = "SELECT teams.ID AS ID, teams.name AS name, teams.motto, team_member.role AS role FROM teams INNER JOIN team_member ON teams.ID = team_member.team_ID INNER JOIN users ON team_member.user_ID = users.ID WHERE users.ID = " + user.getId();
        return query;
    }
    public String findUsersInTeam(int teamId){
        String query = "SELECT team_member.team_ID as team_ID, users.*, team_member.role as role FROM users INNER JOIN team_member ON users.ID = team_member.user_ID WHERE team_member.team_ID = " + teamId;
        return query;
    }
    public String findTeamByTeamId(int teamId){
        String query = "SELECT teams.*, users.ID AS user_ID, users.email, users.displayName, team_member.role " +
                "FROM teams " +
                "INNER JOIN team_member ON team_member.team_ID = teams.ID " +
                "INNER JOIN users ON team_member.user_ID = users.ID " +
                "WHERE teams.ID = " + teamId;
        return query;
    }
    public String getAllUsers(){
        String query = "SELECT * FROM `users`";

        return query;
    }
    public ArrayList<String> createTeam(String teamName, String teamMotto, User user, Set<User> teamMembers){
        ArrayList<String> queryArrayList = new ArrayList<>();
        queryArrayList.add("INSERT INTO `teams`(`name`, `motto`) VALUES ('"+ teamName +"','"+ teamMotto +"');");
        queryArrayList.add("INSERT INTO `team_member`(`team_ID`, `user_ID`, `role`) VALUES (LAST_INSERT_ID(),'"+ user.getId() +"','"+ TeamRoles.OWNER +"')");

        for (User teamMember : teamMembers){
            queryArrayList.add("INSERT INTO `team_member`(`team_ID`, `user_ID`, `role`) VALUES (LAST_INSERT_ID(),'"+ teamMember.getId() +"','"+ TeamRoles.MEMBER +"')");
        }


        return queryArrayList;
    }
    public String deleteTeam(int teamId){
        String query = "DELETE FROM `teams` WHERE `teams`.`ID` = " + teamId;
        return query;
    }
}
