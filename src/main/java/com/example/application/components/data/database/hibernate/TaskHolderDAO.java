package com.example.application.components.data.database.hibernate;

import com.example.application.components.data.Task;
import com.example.application.components.data.TaskHolder;
import com.example.application.components.data.Team;
import com.example.application.components.data.User;
import org.hibernate.query.Query;

import java.util.List;

public class TaskHolderDAO extends HibernateConnection {

    public static void setIsPartDone(TaskHolder taskHolder) {
        start();
        session.update(taskHolder);
        commit();
        close();
        LoggerDAO.log("Change taskPart isDone: " + taskHolder.getId() + " | " + taskHolder.isPartDone());
    }

    public static List<Task> getTasks(User user, Team team) {
        start();
        Query<Task> query = session.createQuery("FROM Task t JOIN t.holders h WHERE h.user.id = :userId AND t.team.id = :teamId ORDER BY t.deadline", Task.class);
        query.setParameter("userId", user.getId()).setParameter("teamId", team.getId());
        List<Task> tasks = query.getResultList();
        close();
        return tasks;
    }

    public static void createHolders(List<User> users, Task task) {
        start();
        for (User user : users) {
            TaskHolder taskHolder = new TaskHolder();
            taskHolder.setUser(user);
            taskHolder.setTask(task);
            session.save(taskHolder);
        }
        commit();
        close();
        for (User user : users) {
            LoggerDAO.log("Create holders | Task: " + task.getId() + " | User: " + user.getEmail());
        }
    }

    public static void deleteTask(TaskHolder taskHolder) {
        start();
        session.delete(taskHolder);
        commit();
        close();
        LoggerDAO.log("Delete TaskPart: " + taskHolder.getId());
    }
}
