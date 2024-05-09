package com.example.application.components.data.database.hibernate;

import com.example.application.components.data.Task;
import com.example.application.components.data.TaskHolder;
import com.example.application.components.data.Team;
import com.example.application.components.data.User;
import org.hibernate.query.Query;

import java.util.List;

public class TaskHolderDAO extends HibernateConnection {

    public static void setIsPartDone(TaskHolder taskHolder) {
        start_old();
        session.update(taskHolder);
        commit();
        close_old();
    }

    public static List<Task> getTasks(User user, Team team) {
        start_old();
        Query<Task> query = session.createQuery("FROM Task t JOIN t.holders h WHERE h.user.id = :userId AND t.team.id = :teamId ORDER BY t.deadline", Task.class);
        query.setParameter("userId", user.getId()).setParameter("teamId", team.getId());
        List<Task> tasks = query.getResultList();
        close_old();
        return tasks;
    }

    public static void createHolders(List<User> users, Task task) {
        start_old();
        for (User user : users) {
            TaskHolder taskHolder = new TaskHolder();
            taskHolder.setUser(user);
            taskHolder.setTask(task);
            session.save(taskHolder);
        }
        commit();
        close_old();
    }

    public static void deleteTask(TaskHolder taskHolder) {
        start_old();
        session.delete(taskHolder);
        commit();
        close_old();
    }
}
