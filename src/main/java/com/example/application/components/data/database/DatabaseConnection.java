package com.example.application.components.data.database;

import com.vaadin.flow.component.notification.Notification;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public abstract class DatabaseConnection {
    private final static String DbUrl = "jdbc:mysql://localhost/taskmanager";
    private final static String DbUser = "root";
    private final static String DbPassword = "";
    private final static String DbDriver = "com.mysql.cj.jdbc.Driver";

    protected Connection connection;
    protected Statement statement;
    protected String query;
    protected SQLParser sqlParser = new SQLParser();

    protected final String error = "Database connection error";
    public DatabaseConnection() {
        try {
            Class.forName(DbDriver).newInstance();
            connection = DriverManager.getConnection(DbUrl, DbUser, DbPassword);
            statement = connection.createStatement();
        } catch (Exception e) {
            Notification.show(error, 5000, Notification.Position.BOTTOM_CENTER);
        }
    }
}
