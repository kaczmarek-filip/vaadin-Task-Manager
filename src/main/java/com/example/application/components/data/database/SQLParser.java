package com.example.application.components.data.database;

import com.example.application.components.data.Task;
import com.example.application.components.data.Team;
import com.example.application.components.data.TeamRoles;
import com.example.application.components.data.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Set;

public class SQLParser {
    /**
     * @param user {@link User} object to save in DB
     * @return query
     * @see DatabaseConnectionDeprecated
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
     * @param email    user email
     * @param password user password
     * @return query
     */
    public String loginUser(String email, String password) {
        String query = "SELECT * FROM `users` WHERE email = '" + email + "' AND password = '" + password + "'";
        return query;
    }

    public String findTeamsByUser(User user) {
        String query = "SELECT teams.ID AS ID, teams.name AS name, teams.motto, team_member.role AS role FROM teams INNER JOIN team_member ON teams.ID = team_member.team_ID INNER JOIN users ON team_member.user_ID = users.ID WHERE users.ID = " + user.getId();
        return query;
    }

    public String findUsersInTeam(int teamId) {
        String query = "SELECT team_member.team_ID as team_ID, users.*, team_member.role as role FROM users INNER JOIN team_member ON users.ID = team_member.user_ID WHERE team_member.team_ID = " + teamId;
        return query;
    }

    public String findTeamByTeamId(int teamId) {
        String query = "SELECT teams.*, users.ID AS user_ID, users.email, users.displayName, team_member.role " +
                "FROM teams " +
                "INNER JOIN team_member ON team_member.team_ID = teams.ID " +
                "INNER JOIN users ON team_member.user_ID = users.ID " +
                "WHERE teams.ID = " + teamId;
        return query;
    }

    public String getAllUsers() {
        String query = "SELECT * FROM `users`";

        return query;
    }

    public ArrayList<String> createTeam(String teamName, String teamMotto, User user, Set<User> teamMembers) {
        ArrayList<String> queryArrayList = new ArrayList<>();
        queryArrayList.add("INSERT INTO `teams`(`name`, `motto`) VALUES ('" + teamName + "','" + teamMotto + "');");
        queryArrayList.add("INSERT INTO `team_member`(`team_ID`, `user_ID`, `role`) VALUES (LAST_INSERT_ID(),'" + user.getId() + "','" + TeamRoles.OWNER + "')");

        for (User teamMember : teamMembers) {
            queryArrayList.add("INSERT INTO `team_member`(`team_ID`, `user_ID`, `role`) VALUES (LAST_INSERT_ID(),'" + teamMember.getId() + "','" + TeamRoles.MEMBER + "')");
        }


        return queryArrayList;
    }

    public String deleteTeam(int teamId) {
        String query = "DELETE FROM `teams` WHERE `teams`.`ID` = " + teamId;
        return query;
    }

    public String updateTeamInfo(int teamId, String teamName, String teamMotto) {
        String query = "UPDATE teams SET name='" + teamName + "', motto='" + teamMotto + "' WHERE ID = " + teamId;

        return query;
    }

    public ArrayList<String> insertUsersIntoTeam(int teamId, Set<User> userSet) {
        ArrayList<String> queryArrayList = new ArrayList<>();

        for (User user : userSet) {
            queryArrayList.add("INSERT INTO `team_member`(`team_ID`, `user_ID`, `role`) VALUES (" + teamId + ",'" + user.getId() + "','" + TeamRoles.MEMBER + "')");
        }

        return queryArrayList;
    }

    public String deleteUserFromTeam(Team team, User user) {
        String query = "DELETE FROM `team_member` WHERE team_ID = " + team.getId() + " AND user_ID = " + user.getId();

        return query;
    }

    public String updateUserRole(Team team, User user, TeamRoles teamRoles) {
        String query = "UPDATE `team_member` SET `role`='" + teamRoles + "' WHERE team_ID = " + team.getId() + " AND user_ID = " + user.getId();

        return query;
    }

    public ArrayList<String> createTeamTask(Task task) {
        ArrayList<String> queryArrayList = new ArrayList<>();

        queryArrayList.add("INSERT INTO `tasks`(`title`, `description`, `creationDate`, `deadline`, `creatorUserID`, `team_ID`) VALUES " +
                "('" + task.getTitle() + "','" + task.getDescription() + "','" + task.getCreationDate() + "','" + task.getDeadline() + "','" + task.getCreator().getId() + "', '" + task.getTeam().getId() + "');");

        for (User taskHolder : task.getHolders()) {
            queryArrayList.add("INSERT INTO `taskholders`(`taskID`, `user_ID`) VALUES (LAST_INSERT_ID(),'" + taskHolder.getId() + "');");
        }

        return queryArrayList;
    }

    public String createOwnTask(Task task) {
        return "INSERT INTO `tasks`(`title`, `description`, `creationDate`, `deadline`, `creatorUserID`, `team_ID`) VALUES " +
                "('" + task.getTitle() + "','" + task.getDescription() + "','" + task.getCreationDate() + "','" + task.getDeadline() + "','" + task.getCreator().getId() + "',NULL)";
    }

    public String getOwnTasks(User user) {
        return "SELECT * FROM `tasks` WHERE creatorUserID = " + user.getId() + " AND team_ID IS NULL ORDER BY deadline";
    }

    public String setIsDone(Task task) {
        return "UPDATE `tasks` SET `isDone`= " + task.isDone() + " WHERE ID = " + task.getId();
    }

    public String deleteTask(Task task) {
        return "DELETE FROM `tasks` WHERE ID = " + task.getId();
    }

    @Deprecated
    public String getTeamTasks(Team team) {
//        return "SELECT tasks.*, taskholders.user_ID, users.email, users.displayName FROM `tasks` " +
//                "INNER JOIN taskholders ON tasks.ID = taskholders.taskID " +
//                "INNER JOIN users ON creatorUserID = users.ID " +
//                "WHERE team_ID = " + team.getId();

        // Alternatywa
//        SELECT tasks.*, taskholders.user_ID, users.email, users.displayName FROM `tasks` INNER JOIN taskholders ON tasks.ID = taskholders.taskID INNER JOIN users ON taskholders.user_ID = users.ID WHERE team_ID = 42;
        return "SELECT tasks.*, users.email, users.displayName FROM `tasks` " +
                "INNER JOIN users ON creatorUserID = users.ID " +
                "WHERE team_ID = " + team.getId();
    }

    public String getTaskHolders(Task task) {
        return "SELECT taskholders.taskID, users.ID, users.email, users.displayName FROM `taskholders` INNER JOIN users ON user_ID = users.ID WHERE taskholders.taskID =" + task.getId();
    }

    public String getUserTasks(User user, Team team) {
        return "SELECT tasks.ID, tasks.title, tasks.description, tasks.creationDate, tasks.deadline, tasks.isDone, tasks.creatorUserID,users.email, users.displayName " +
                "FROM `taskholders` INNER JOIN tasks ON taskID = tasks.ID " +
                "INNER JOIN users ON tasks.creatorUserID = users.ID " +
                "WHERE user_ID = " + user.getId() + " AND team_ID = " + team.getId();
    }

    public String addChat(User user1, User user2) {
        return "INSERT INTO `chats`(`user1_ID`, `user2_ID`) VALUES ('" + user1.getId() + "','" + user2.getId() + "')";
    }

    public String getUserChats(User user) {
//        return "SELECT * FROM `chats` WHERE user1_ID = " + user.getId() + " OR user2_ID = " + user.getId();
        return "SELECT chats.*, user1.email AS user1_email, user1.displayName AS user1_displayName, " +
                "user2.email AS user2_email, user2.displayName AS user2_displayName " +
                "FROM `chats` " +
                "INNER JOIN users AS user1 ON user1.ID = chats.user1_ID " +
                "INNER JOIN users AS user2 ON user2.ID = chats.user2_ID " +
                "WHERE user1.ID = " + user.getId() + " OR user2.ID = " + user.getId();
    }

    public String sendMessage(User userFrom, User userTo, String content, LocalDateTime localDateTime) {
        String dateTimeFormatter = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        return "INSERT INTO messages (chatID, user_ID, content, dateTime) " +
                "SELECT c.chatID, " + userFrom.getId() + ", '" + content + "', '" + dateTimeFormatter + "' " +
                "FROM chats c " +
                "WHERE (c.user1_ID = " + userFrom.getId() + " AND c.user2_ID = " + userTo.getId() + ") " +
                "OR (c.user1_ID = " + userTo.getId() + " AND c.user2_ID = " + userFrom.getId() + ")";
    }
}
