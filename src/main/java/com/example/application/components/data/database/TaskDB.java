package com.example.application.components.data.database;

import com.example.application.components.data.Task;
import com.example.application.components.data.Team;
import com.example.application.components.data.User;
import com.vaadin.flow.component.notification.Notification;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class TaskDB extends DatabaseConnection {
    public void createTask(Task task, boolean isOwn) {
        ArrayList<String> queryArrayList = new ArrayList<>();

        if (isOwn) {
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

    public ArrayList<Task> getOwnTasks(User user) {
        query = sqlParser.getOwnTasks(user);
        ArrayList<Task> taskArrayList = new ArrayList<>();

        try (ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int DbTaskId = resultSet.getInt("ID");
                String DbTitle = resultSet.getString("title");
                String DbDescription = resultSet.getString("description");
                LocalDate DbCreationDate = resultSet.getDate("creationDate").toLocalDate();
                LocalDate DbDeadline = resultSet.getDate("deadline").toLocalDate();
                boolean DbIsDone = resultSet.getBoolean("isDone");

                taskArrayList.add(new Task(DbTaskId, DbIsDone, DbTitle, DbDescription, DbCreationDate, DbDeadline, user));
            }
            statement.close();
            connection.close();
        } catch (Exception e) {
            Notification.show(e.toString(), 5000, Notification.Position.BOTTOM_CENTER);
        }
        return taskArrayList;
    }

    public void setIsDone(Task task) {
        query = sqlParser.setIsDone(task);

        try {
            statement.executeUpdate(query);

            statement.close();
            connection.close();
        } catch (Exception e) {
            Notification.show(e.toString(), 5000, Notification.Position.BOTTOM_CENTER);
        }
    }

    public void deleteTask(Task task) {
        query = sqlParser.deleteTask(task);

        try {
            statement.executeUpdate(query);

            statement.close();
            connection.close();
        } catch (Exception e) {
            Notification.show(e.toString(), 5000, Notification.Position.BOTTOM_CENTER);
        }
    }
    @Deprecated
    public ArrayList<Task> getTeamTasks(Team team) {
        query = sqlParser.getTeamTasks(team);
        ArrayList<Task> taskArrayList = new ArrayList<>();

        try (ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int DbTaskId = resultSet.getInt("ID");
                String DbTitle = resultSet.getString("title");
                String DbDescription = resultSet.getString("description");
                LocalDate DbCreationDate = resultSet.getDate("creationDate").toLocalDate();
                LocalDate DbDeadline = resultSet.getDate("deadline").toLocalDate();
                boolean DbIsDone = resultSet.getBoolean("isDone");

                taskArrayList.add(new Task(DbTaskId, DbIsDone, DbTitle, DbDescription, DbCreationDate, DbDeadline, User.getLoggedInUser())); //TODO: bez logged in user
            }
            statement.close();
            connection.close();
        } catch (Exception e) {
            Notification.show(e.toString(), 5000, Notification.Position.BOTTOM_CENTER);
        }
        return taskArrayList;
    }

    public Set<User> getTaskHolders(Task task){
        query = sqlParser.getTaskHolders(task);
        Set<User> userSet = new HashSet<>();

        try (ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int DbUserId = resultSet.getInt("ID");
                String DbUserEmail = resultSet.getString("email");
                String DbUserDisplayName = resultSet.getString("displayName");

                User user = new User(DbUserId, DbUserDisplayName, DbUserEmail);
                userSet.add(user);
            }
            statement.close();
            connection.close();
        } catch (Exception e) {
            Notification.show(e.toString(), 5000, Notification.Position.BOTTOM_CENTER);
        }


        return userSet;
    }
    public ArrayList<Task> getUserTasks(User user, Team team){
        query = sqlParser.getUserTasks(user, team);
        ArrayList<Task> taskArrayList = new ArrayList<>();

        try (ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int DbTaskId = resultSet.getInt("ID");
                String DbTitle = resultSet.getString("title");
                String DbDescription = resultSet.getString("description");
                LocalDate DbCreationDate = resultSet.getDate("creationDate").toLocalDate();
                LocalDate DbDeadline = resultSet.getDate("deadline").toLocalDate();
                boolean DbIsDone = resultSet.getBoolean("isDone");

                int DbCreatorId = resultSet.getInt("creatorUserID");
                String DbCreatorEmail = resultSet.getString("email");
                String DbCreatorDisplayName = resultSet.getString("displayName");

                User creator = new User(DbCreatorId, DbCreatorDisplayName, DbCreatorEmail);

                taskArrayList.add(new Task(DbTaskId, DbIsDone, DbTitle, DbDescription, DbCreationDate, DbDeadline, creator));
            }
            statement.close();
            connection.close();
        } catch (Exception e) {
            Notification.show(e.toString(), 5000, Notification.Position.BOTTOM_CENTER);
        }
        return taskArrayList;
    }


}
