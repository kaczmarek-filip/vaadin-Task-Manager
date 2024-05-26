package com.example.application.components.data.database.hibernate;

import com.example.application.components.data.Task;
import com.example.application.components.data.User;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.util.List;

public class TaskDAO extends HibernateConnection {
    public static void createTask(Task task) {
        start();
        task.setCreationDate(LocalDate.now());
        task.setCreator(User.getLoggedInUser());

        session.persist(task);
        session.flush();
        close();
        LoggerDAO.log("Create task: " + task.getId());
    }

    public static List<Task> getTasks(User user) {
        start();
        Query<Task> query = session.createQuery("FROM Task WHERE creator.id = :id AND team = null ORDER BY deadline");
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
        LoggerDAO.log("Change task isDone : " + task.getId() + " | " + task.isDone());
    }

    public static void delete(Task task) {
        start();
        session.delete(task);
        commit();
        close();
        LoggerDAO.log("Delete task: " + task.getId());
    }

}
