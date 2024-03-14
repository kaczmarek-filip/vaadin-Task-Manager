package com.example.application.components.data.database;

import com.example.application.components.data.*;
import com.vaadin.flow.component.notification.Notification;

import java.sql.*;
import java.util.ArrayList;

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

    public DatabaseConnection() {
        try {
            Class.forName(DbDriver).newInstance();
            connection = DriverManager.getConnection(DbUrl, DbUser, DbPassword);
            statement = connection.createStatement();
        } catch (Exception e){
            Notification.show(e.toString(), 5000, Notification.Position.BOTTOM_CENTER);
        }

    }

    /**
     * Method to register user in database
     * @param user User to register in database
     */
    public void registerUser(User user) {
        query = sqlParser.createUser(user);

        try {
            statement.executeUpdate(query);

            statement.close();
            connection.close();
        } catch (Exception e) {
            Notification.show(e.toString(), 5000, Notification.Position.BOTTOM_CENTER);
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
                Notification.show(e.toString(), 5000, Notification.Position.BOTTOM_CENTER);
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
                        return new User(DbId, DbDisplayName, DbEmail, DbPassword);
                    }
                }
                statement.close();
                connection.close();
            } catch (Exception e) {
                Notification.show(e.toString(), 5000, Notification.Position.BOTTOM_CENTER);
            }

        return null;
    }

    public ArrayList<UserTeams> findTeamsByUser(User user){
        query = sqlParser.findTeamsByUser(user);
        ArrayList<UserTeams> userTeamsArrayList = new ArrayList<>();

            try (ResultSet resultSet = statement.executeQuery(query)) {



                while (resultSet.next()){
                    int DbId = resultSet.getInt("ID");
                    String DbName = resultSet.getString("name");
                    TeamRoles DbRole = TeamRoles.valueOf(resultSet.getString("role"));

                    userTeamsArrayList.add(new UserTeams(DbId, DbName, DbRole));
                }
                statement.close();
                connection.close();
            } catch (Exception e) {
                Notification.show(e.toString(), 5000, Notification.Position.BOTTOM_CENTER);
            }

        return userTeamsArrayList;
    }

    public ArrayList<TeamMembers> findUsersByTeam(UserTeams userTeams){
        query = sqlParser.findUsersByTeam(userTeams);
        ArrayList<TeamMembers> teamMembersArrayList = new ArrayList<>();

            try (ResultSet resultSet = statement.executeQuery(query)) {

                while (resultSet.next()){

                    int DbId = resultSet.getInt("ID");
                    String DbEmail = resultSet.getString("email");
                    String DbPassword = resultSet.getString("password");
                    String DbDisplayName = resultSet.getString("displayName");

                    TeamRoles DbRole = TeamRoles.valueOf(resultSet.getString("role"));

                    TeamMembers teamMembers = new TeamMembers(userTeams.getId(), new User(DbId, DbDisplayName, DbEmail, DbPassword), DbRole);

                    teamMembersArrayList.add(teamMembers);
                }
                statement.close();
                connection.close();
            } catch (Exception e) {
                Notification.show(e.toString(), 5000, Notification.Position.BOTTOM_CENTER);
            }


        return teamMembersArrayList;
    }
    public Team findTeamNameByTeamId(int teamId){
        query = sqlParser.findTeamNameByTeamId(teamId);
        try (ResultSet resultSet = statement.executeQuery(query)){
            if(resultSet.next()){
                String teamName = resultSet.getString("name");
                return new Team(teamId, teamName);
            }
        } catch (Exception e){
            Notification.show(e.toString(), 5000, Notification.Position.BOTTOM_CENTER);
        }
        return null;
    }

}
