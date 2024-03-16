package com.example.application.components.data.database;

import com.example.application.components.data.*;
import com.vaadin.flow.component.notification.Notification;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Database connection
 * @see SQLParser
 */
public class DatabaseConnection {
    private final static String DbUrl = "jdbc:mysql://localhost/taskmanager";
    private final static String DbUser = "root";
    private final static String DbPassword = "";
    private final static String DbDriver = "com.mysql.cj.jdbc.Driver";

    private Connection connection;
    private Statement statement;
    private String query;
    private SQLParser sqlParser = new SQLParser();

    private String error = "Database connection error";

    public DatabaseConnection() {
        try {
            Class.forName(DbDriver).newInstance();
            connection = DriverManager.getConnection(DbUrl, DbUser, DbPassword);
            statement = connection.createStatement();
        } catch (Exception e){
            Notification.show(error, 5000, Notification.Position.BOTTOM_CENTER);
        }

    }

    /**
     * Method to register user in database
     * @param user User to register in database
     */
    public void registerUser(User user, String password) {
        query = sqlParser.createUser(user, password);

        try {
            statement.executeUpdate(query);

            statement.close();
            connection.close();
        } catch (Exception e) {
            Notification.show(error, 5000, Notification.Position.BOTTOM_CENTER);
        }
    }

    /**
     * @param user User to check is email already exists in DB
     * @return true if email already exists | otherwise false
     */
    public boolean isEmailExists(User user){
        query = sqlParser.isEmailExists(user);

            try (ResultSet resultSet = statement.executeQuery(query)) {

                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    if (count != 0){
                        return true;
                    }
                }
                statement.close();
                connection.close();
            } catch (Exception e) {
                Notification.show(error, 5000, Notification.Position.BOTTOM_CENTER);
            }
        return false;
    }

    /**
     * @param email Email from {@link LoginDialog}
     * @param password Password from {@link LoginDialog}
     * @return {@link User} object
     */
    public User loginUser(String email, String password){
        query = sqlParser.loginUser(email, password);

            try (ResultSet resultSet = statement.executeQuery(query)) {

                while (resultSet.next()){

                    int DbId = resultSet.getInt("ID");
                    String DbEmail = resultSet.getString("email");
                    String DbPassword = resultSet.getString("password");
                    String DbDisplayName = resultSet.getString("displayName");
                    if (email.equals(DbEmail) && password.equals(DbPassword)){
                        return new User(DbId, DbDisplayName, DbEmail);
                    }
                }
                statement.close();
                connection.close();
            } catch (Exception e) {
                Notification.show(error, 5000, Notification.Position.BOTTOM_CENTER);
            }

        return null;
    }

    public ArrayList<Team> findTeamsByUser(User user){
        query = sqlParser.findTeamsByUser(user);
        ArrayList<Team> userTeamsArrayList = new ArrayList<>();

            try (ResultSet resultSet = statement.executeQuery(query)) {

                while (resultSet.next()){
                    int DbTeamId = resultSet.getInt("ID");
                    String DbTeamName = resultSet.getString("name");
                    TeamRoles DbRole = TeamRoles.valueOf(resultSet.getString("role"));

                    Map<User, TeamRoles> userTeamRolesMap = new HashMap<>();
                    userTeamRolesMap.put(user, DbRole);

                    userTeamsArrayList.add(new Team(DbTeamId, DbTeamName, userTeamRolesMap));
                }
                statement.close();
                connection.close();
            } catch (Exception e) {
                Notification.show(error, 5000, Notification.Position.BOTTOM_CENTER);
            }

        return userTeamsArrayList;
    }

    public Map<User, TeamRoles> findUsersInTeam(Team team){
        query = sqlParser.findUsersInTeam(team);
        Map<User, TeamRoles> userTeamRolesMap = new HashMap<>();

            try (ResultSet resultSet = statement.executeQuery(query)) {

                while (resultSet.next()){

                    int DbId = resultSet.getInt("ID");
                    String DbEmail = resultSet.getString("email");
//                    String DbPassword = resultSet.getString("password");
                    String DbDisplayName = resultSet.getString("displayName");

                    TeamRoles DbRole = TeamRoles.valueOf(resultSet.getString("role"));

                    userTeamRolesMap.put(new User(DbId, DbDisplayName, DbEmail), DbRole);

                }
                statement.close();
                connection.close();
            } catch (Exception e) {
                Notification.show(error, 5000, Notification.Position.BOTTOM_CENTER);
            }


        return userTeamRolesMap;
    }
    public Team findTeamNameByTeamId(int teamId){
        query = sqlParser.findTeamNameByTeamId(teamId);
        try (ResultSet resultSet = statement.executeQuery(query)){
            if(resultSet.next()){
                String teamName = resultSet.getString("name");
                return new Team(teamId, teamName);
            }
            statement.close();
            connection.close();
        } catch (Exception e){
            Notification.show(error, 5000, Notification.Position.BOTTOM_CENTER);
        }
        return null;
    }
    public ArrayList<User> getAllUsers(){
        ArrayList<User> allUsers = new ArrayList<>();
        query = sqlParser.getAllUsers();
        try (ResultSet resultSet = statement.executeQuery(query)){
            while (resultSet.next()) {

                    int DbId = resultSet.getInt("ID");
                    String DbEmail = resultSet.getString("email");
                    String DbDisplayName = resultSet.getString("displayName");

                    allUsers.add(new User(DbId, DbDisplayName, DbEmail));
            }
        } catch (Exception e){
            Notification.show(error, 5000, Notification.Position.BOTTOM_CENTER);
        }

        return allUsers;
    }
    public void createTeam(String teamName, String teamMotto, User user, Set<User> teamMembers){
        ArrayList<String> queryArrayList = sqlParser.createTeam(teamName, teamMotto, user, teamMembers);

        try {
            for (String query : queryArrayList){
                statement.executeUpdate(query);
            }

            statement.close();
            connection.close();
        } catch (Exception e) {
            Notification.show(e.toString(), 5000, Notification.Position.BOTTOM_CENTER);
        }
    }

}
