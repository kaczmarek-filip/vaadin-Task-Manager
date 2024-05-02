package com.example.application.components.data.database.hibernate;

import com.example.application.components.data.Task;
import com.example.application.components.data.Team;
import com.example.application.components.data.User;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TaskDAO extends HibernateConnection {
    public static void createTask(Task task) {
        start();
        task.setCreationDate(LocalDate.now());
        task.setCreator(User.getLoggedInUser());

        List<User> taskHolders = task.getTaskHolders();
        if (taskHolders != null) {
            task.setTaskHolders(new ArrayList<>());
            for (User user : taskHolders) {
                User mergedUser = (User) session.merge(user);
                task.getTaskHolders().add(mergedUser);
            }
        }
        session.persist(task);
        session.flush();
        close();
    }

    public static List<Task> getTasks(User user, Team team) {
        start();
        Query<Task> query;
        if (team == null) {
            query = session.createQuery("FROM Task WHERE creator.id = :id AND team = null ORDER BY deadline");
        } else {
            query = session.createQuery("FROM Task t JOIN t.taskHolders u WHERE u.id = :id AND t.team.id = :teamId ORDER BY t.deadline");
            query.setParameter("teamId", team.getId());
        }
        query.setParameter("id", user.getId());
        List<Task> taskList = query.getResultList();
        commit();
        close();
        return taskList;
    }

    public static void setIsDone(Task task) {
        start();
        session.update(task);
        commit();
        close();
    }

    public static void delete(Task task) {
        start();
        session.delete(task);
        commit();
        close();
    }

    public static List<User> getHolders(Task task) {
        start();
        Query<User> query = session.createQuery("SELECT t.taskHolders FROM Task t WHERE t.id = :id");
        query.setParameter("id", task.getId());
        List<User> userList = query.getResultList();
        close();
        return userList;
    }

}
