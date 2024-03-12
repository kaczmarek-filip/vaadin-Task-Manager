package com.example.application.components;

import com.vaadin.flow.component.notification.Notification;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.sql.*;
public class DatabaseConnection {
    private final static String DbUrl = "jdbc:mysql://localhost/taskmanager";
    private final static String DbUser = "root";
    private final static String DbPassword = "";
    private final static String DbDriver = "com.mysql.cj.jdbc.Driver";

    private Connection connection;
    private Statement statement;
    private String query;
    private SQLParser sqlUserParser = new SQLParser();

    public void registerUser(User user) {
        query = sqlUserParser.createUser(user);

        try {
            Class.forName(DbDriver).newInstance();
            connection = DriverManager.getConnection(DbUrl, DbUser, DbPassword);
            statement = connection.createStatement();
            statement.executeUpdate(query);

            statement.close();
            connection.close();
        } catch (Exception e) {
            Notification.show(e.toString(), 5000, Notification.Position.BOTTOM_CENTER);
        }
    }
    public boolean isEmailExists(User user){
        query = sqlUserParser.isEmailExists(user);

        try {
            Class.forName(DbDriver).newInstance();
            connection = DriverManager.getConnection(DbUrl, DbUser, DbPassword);
            statement = connection.createStatement();
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


        } catch (Exception e) {
            Notification.show(e.toString(), 5000, Notification.Position.BOTTOM_CENTER);
        }
        return false;
    }

    public User loginUser(String email, String password){
        query = sqlUserParser.loginUser(email, password);

        try {
            Class.forName(DbDriver).newInstance();
            connection = DriverManager.getConnection(DbUrl, DbUser, DbPassword);
            statement = connection.createStatement();
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
        } catch (Exception e){
            Notification.show(e.toString(), 5000, Notification.Position.BOTTOM_CENTER);
        }

        return null;
    }
}
