package com.example.application.components.data.database;

import com.example.application.components.data.Task;
import com.example.application.components.data.User;
import com.vaadin.flow.component.notification.Notification;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;

public class TaskDB extends DatabaseConnection {
    public void createTask(Task task, boolean isOwn){
        ArrayList<String> queryArrayList = new ArrayList<>();

        if (isOwn){
            queryArrayList.add(sqlParser.createOwnTask(task));
        } else {
            queryArrayList = sqlParser.createTeamTask(task);
        }


        try {
            for (String query : queryArrayList) {
                statement.executeUpdate(query);
            }

            statement.close();
            connection.close();
        } catch (Exception e) {
            Notification.show(e.toString(), 5000, Notification.Position.BOTTOM_CENTER);
        }
    }
    public ArrayList<Task> getOwnTasks(User user){
        query = sqlParser.getOwnTasks(user);
        ArrayList<Task> taskArrayList = new ArrayList<>();

        try (ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int DbTaskId = resultSet.getInt("ID");
                String DbTitle = resultSet.getString("title");
                String DbDescription = resultSet.getString("description");
                LocalDate DbCreationDate = resultSet.getDate("creationDate").toLocalDate();
                LocalDate DbDeadline = resultSet.getDate("deadline").toLocalDate();

                taskArrayList.add(new Task(DbTaskId, DbTitle, DbDescription, DbCreationDate, DbDeadline, user));
            }
            statement.close();
            connection.close();
        } catch (Exception e) {
            Notification.show(e.toString(), 5000, Notification.Position.BOTTOM_CENTER);
        }
        return taskArrayList;
    }
}
