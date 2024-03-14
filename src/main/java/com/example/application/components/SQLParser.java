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
     * @param email from {@link com.example.application.components.dialogs.LoginDialog}
     * @param password from {@link com.example.application.components.dialogs.LoginDialog}
     * @return query
     */
    public String loginUser(String email, String password) {
        String query = "SELECT * FROM `users` WHERE email = '" + email + "' AND password = '" + password + "'";
        return query;
    }

    public String findTeamsByUser(User user){
//        String query = "SELECT * FROM `teams` JOIN team_member JOIN users ON users.ID = team_member.user_ID WHERE users.ID = " + user.getId();
//        String query = "SELECT name, role FROM `teams` JOIN team_member JOIN users ON users.ID = team_member.user_ID WHERE users.ID = 4";
        String query = "SELECT teams.name AS name, team_member.role AS role FROM teams INNER JOIN team_member ON teams.ID = team_member.team_ID INNER JOIN users ON team_member.user_ID = users.ID WHERE users.ID = " + user.getId();
//        SELECT teams.name AS team_name, team_member.role AS user_role FROM teams INNER JOIN team_member ON teams.ID = team_member.team_ID INNER JOIN users ON team_member.user_ID = users.ID WHERE users.ID = 4;
        return query;
    }


//    SELECT name, team_member.role FROM `teams`  JOIN team_member JOIN users ON users.ID = team_member.user_ID WHERE users.ID = 4
//
//    SELECT teams.name, role FROM team_member JOIN team_member.user_ID ON
}
