package com.example.application.components.data.database;

import com.example.application.components.data.Task;
import com.vaadin.flow.component.notification.Notification;

import java.util.ArrayList;

public class TaskDB extends DatabaseConnection {
    public void createTask(Task task){
        ArrayList<String> queryArrayList = sqlParser.createTask(task);

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
}
