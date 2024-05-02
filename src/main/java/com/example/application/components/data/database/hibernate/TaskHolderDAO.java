package com.example.application.components.data.database.hibernate;

import com.example.application.components.data.Task;
import com.example.application.components.data.TaskHolder;
import com.example.application.components.data.User;
import org.hibernate.query.Query;

public class TaskHolderDAO extends HibernateConnection {

    public static void setIsPartDone(TaskHolder taskHolder) {
        start();
        session.update(taskHolder);
        commit();
        close();
    }

    public static TaskHolder getTaskHolder(Task task, User user) {
        start();
        Query<TaskHolder> query = session.createQuery("FROM TaskHolder WHERE task.id = :taskId AND user.id = :userId", TaskHolder.class);
        query.setParameter("taskId", task.getId()).setParameter("userId", user.getId());
        TaskHolder taskHolder = query.uniqueResult();
        close();
        return taskHolder;
    }
}
