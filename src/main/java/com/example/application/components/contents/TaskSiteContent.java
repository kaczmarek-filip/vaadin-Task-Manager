package com.example.application.components.contents;

import com.example.application.components.data.Task;
import com.example.application.components.data.User;
import com.example.application.components.data.database.hibernate.TaskDAO;
import com.example.application.components.elements.tasks.SingleTaskBlockElement;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class TaskSiteContent extends HorizontalLayout {

    public TaskSiteContent() {
        setWidthFull();
        addClassName("taskSiteContent");

        generateTasks();
    }

    private void generateTasks() {
        for (Task task : TaskDAO.getTasks(User.getLoggedInUser(), null)) {
            add(new SingleTaskBlockElement(task));
        }
    }
}
