//package com.example.application.components.data.database;
//
//import com.example.application.components.data.*;
//import com.vaadin.flow.component.notification.Notification;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Set;
//
///**
// * Database connection
// *
// * @see SQLParser
// */
//@Deprecated
//public class DatabaseConnectionDeprecated {
//    private final static String DbUrl = "jdbc:mysql://localhost/taskmanager";
//    private final static String DbUser = "root";
//    private final static String DbPassword = "";
//    private final static String DbDriver = "com.mysql.cj.jdbc.Driver";
//
//    private Connection connection;
//    private Statement statement;
//    private String query;
//    private SQLParser sqlParser = new SQLParser();
//
//    private String error = "Database connection error";
//
//    public DatabaseConnectionDeprecated() {
//        try {
//            Class.forName(DbDriver).newInstance();
//            connection = DriverManager.getConnection(DbUrl, DbUser, DbPassword);
//            statement = connection.createStatement();
//        } catch (Exception e) {
//            Notification.show(error, 5000, Notification.Position.BOTTOM_CENTER);
//        }
//
//    }
//
//    /**
//     * Method to register user in database
//     *
//     * @param user User to register in database
//     */
//    public void registerUser(User user, String password) {
//        query = sqlParser.createUser(user, password);
//
//        try {
//            statement.executeUpdate(query);
//
//            statement.close();
//            connection.close();
//        } catch (Exception e) {
//            Notification.show(error, 5000, Notification.Position.BOTTOM_CENTER);
//        }
//    }
//
//    /**
//     * @param user User to check is email already exists in DB
//     * @return true if email already exists | otherwise false
//     */
//    public boolean isEmailExists(User user) {
//        query = sqlParser.isEmailExists(user);
//
//        try (ResultSet resultSet = statement.executeQuery(query)) {
//
//            if (resultSet.next()) {
//                int count = resultSet.getInt(1);
//                if (count != 0) {
//                    return true;
//                }
//            }
//            statement.close();
//            connection.close();
//        } catch (Exception e) {
//            Notification.show(error, 5000, Notification.Position.BOTTOM_CENTER);
//        }
//        return false;
//    }
//
//    public User loginUser(String email, String password) {
//        query = sqlParser.loginUser(email, password);
//
//        try (ResultSet resultSet = statement.executeQuery(query)) {
//
//            while (resultSet.next()) {
//
//                int DbId = resultSet.getInt("ID");
//                String DbEmail = resultSet.getString("email");
//                String DbPassword = resultSet.getString("password");
//                String DbDisplayName = resultSet.getString("displayName");
//                if (email.equals(DbEmail) && password.equals(DbPassword)) {
//                    return new User(DbId, DbDisplayName, DbEmail);
//                }
//            }
//            statement.close();
//            connection.close();
//        } catch (Exception e) {
//            Notification.show(error, 5000, Notification.Position.BOTTOM_CENTER);
//        }
//
//        return null;
//    }
//
//    /**
//     * @param user user for search
//     * @return ArrayList of {@link Team}
//     */
//    public ArrayList<Team> findTeamsByUser(User user) {
//        query = sqlParser.findTeamsByUser(user);
//        ArrayList<Team> userTeamsArrayList = new ArrayList<>();
//
//        try (ResultSet resultSet = statement.executeQuery(query)) {
//
//            while (resultSet.next()) {
//                int DbTeamId = resultSet.getInt("ID");
//                String DbTeamName = resultSet.getString("name");
//                String DbTeamMotto = resultSet.getString("motto");
//                TeamRoles DbRole = TeamRoles.valueOf(resultSet.getString("role"));
//
//                Map<User, TeamRoles> userTeamRolesMap = new HashMap<>();
//                userTeamRolesMap.put(user, DbRole);
//
//                userTeamsArrayList.add(new Team(DbTeamId, DbTeamName, DbTeamMotto, userTeamRolesMap));
//            }
//            statement.close();
//            connection.close();
//        } catch (Exception e) {
//            Notification.show(error, 5000, Notification.Position.BOTTOM_CENTER);
//        }
//
//        return userTeamsArrayList;
//    }
//
//    /**
//     * @param teamId team ID
//     * @return Team members with roles by ID
//     */
//    public Map<User, TeamRoles> findUsersInTeam(int teamId) {
//        query = sqlParser.findUsersInTeam(teamId);
//        Map<User, TeamRoles> userTeamRolesMap = new HashMap<>();
//
//        try (ResultSet resultSet = statement.executeQuery(query)) {
//
//            while (resultSet.next()) {
//
//                int DbId = resultSet.getInt("ID");
//                String DbEmail = resultSet.getString("email");
////                    String DbPassword = resultSet.getString("password");
//                String DbDisplayName = resultSet.getString("displayName");
//
//                TeamRoles DbRole = TeamRoles.valueOf(resultSet.getString("role"));
//
//                userTeamRolesMap.put(new User(DbId, DbDisplayName, DbEmail), DbRole);
//
//            }
//            statement.close();
//            connection.close();
//        } catch (Exception e) {
//            Notification.show(error, 5000, Notification.Position.BOTTOM_CENTER);
//        }
//
//
//        return userTeamRolesMap;
//    }
//
//    /**
//     * Searching for full team information by ID
//     *
//     * @param teamId team ID
//     * @return {@link Team} for teamId
//     */
//    public Team findTeamByTeamId(int teamId) {
//        query = sqlParser.findTeamByTeamId(teamId);
//
//        try (ResultSet resultSet = statement.executeQuery(query)) {
//            if (resultSet.next()) {
//                int DbTeamId = resultSet.getInt("ID");
//                String DbTeamName = resultSet.getString("name");
//                String DbTeamMotto = resultSet.getString("motto");
//
//                Map<User, TeamRoles> userTeamRolesMap = new HashMap<>();
//
//                do {
//                    int DbUserId = resultSet.getInt("user_ID");
//                    String DbUserEmail = resultSet.getString("email");
//                    String DbUserDisplayName = resultSet.getString("displayName");
//
//                    TeamRoles DbRole = TeamRoles.valueOf(resultSet.getString("role"));
//
//                    userTeamRolesMap.put(new User(DbUserId, DbUserDisplayName, DbUserEmail), DbRole);
//                } while ((resultSet.next()));
//
//                return new Team(DbTeamId, DbTeamName, DbTeamMotto, userTeamRolesMap);
//            }
//            statement.close();
//            connection.close();
//        } catch (Exception e) {
//            Notification.show(error, 5000, Notification.Position.BOTTOM_CENTER);
//        }
//        return null;
//    }
//
//    /**
//     * @return all registered users
//     */
//    public ArrayList<User> getAllUsers() {
//        ArrayList<User> allUsers = new ArrayList<>();
//        query = sqlParser.getAllUsers();
//        try (ResultSet resultSet = statement.executeQuery(query)) {
//            while (resultSet.next()) {
//
//                int DbId = resultSet.getInt("ID");
//                String DbEmail = resultSet.getString("email");
//                String DbDisplayName = resultSet.getString("displayName");
//
//                allUsers.add(new User(DbId, DbDisplayName, DbEmail));
//            }
//        } catch (Exception e) {
//            Notification.show(error, 5000, Notification.Position.BOTTOM_CENTER);
//        }
//
//        return allUsers;
//    }
//
//    /**
//     * Create team
//     *
//     * @param teamName    team name
//     * @param teamMotto   team
//     * @param owner       team owner
//     * @param teamMembers team members
//     */
//    public void createTeam(String teamName, String teamMotto, User owner, Set<User> teamMembers) {
//        ArrayList<String> queryArrayList = sqlParser.createTeam(teamName, teamMotto, owner, teamMembers);
//
//        try {
//            for (String query : queryArrayList) {
//                statement.executeUpdate(query);
//            }
//
//            statement.close();
//            connection.close();
//        } catch (Exception e) {
//            Notification.show(e.toString(), 5000, Notification.Position.BOTTOM_CENTER);
//        }
//    }
//
//    /**
//     * Delete team
//     *
//     * @param teamId to be removed
//     */
//    public void deleteTeam(int teamId) {
//        query = sqlParser.deleteTeam(teamId);
//        try {
//            statement.executeUpdate(query);
//
//            statement.close();
//            connection.close();
//        } catch (Exception e) {
//            Notification.show(e.toString(), 5000, Notification.Position.BOTTOM_CENTER);
//        }
//
//    }
//
//
//    public void updateTeamInfo(int teamId, String teamName, String teamMotto) {
//        query = sqlParser.updateTeamInfo(teamId, teamName, teamMotto);
//
//        try {
//            statement.executeUpdate(query);
//
//            statement.close();
//            connection.close();
//        } catch (Exception e) {
//            Notification.show(e.toString(), 5000, Notification.Position.BOTTOM_CENTER);
//        }
//    }
//
//    public void insertUsersIntoTeam(int teamId, Set<User> userSet) {
//        ArrayList<String> queryArrayList = sqlParser.insertUsersIntoTeam(teamId, userSet);
//
//        try {
//            for (String query : queryArrayList) {
//                statement.executeUpdate(query);
//            }
//
//            statement.close();
//            connection.close();
//        } catch (Exception e) {
//            Notification.show(e.toString(), 5000, Notification.Position.BOTTOM_CENTER);
//        }
//    }
//    public void deleteUserFromTeam(Team team, User user){
//        query = sqlParser.deleteUserFromTeam(team, user);
//
//        try {
//            statement.executeUpdate(query);
//
//            statement.close();
//            connection.close();
//        } catch (Exception e) {
//            Notification.show(e.toString(), 5000, Notification.Position.BOTTOM_CENTER);
//        }
//    }
//
//    public void updateUserRole(Team team, User user, TeamRoles teamRoles){
//        query = sqlParser.updateUserRole(team, user, teamRoles);
//
//        try {
//            statement.executeUpdate(query);
//
//            statement.close();
//            connection.close();
//        } catch (Exception e) {
//            Notification.show(e.toString(), 5000, Notification.Position.BOTTOM_CENTER);
//        }
//    }
//    public void createTask(Task task){
//        ArrayList<String> queryArrayList = sqlParser.createTask(task);
//
//        try {
//            for (String query : queryArrayList) {
//                statement.executeUpdate(query);
//            }
//
//            statement.close();
//            connection.close();
//        } catch (Exception e) {
//            Notification.show(e.toString(), 5000, Notification.Position.BOTTOM_CENTER);
//        }
//    }
//
//}
