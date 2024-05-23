package com.example.application.views.main.contents;

import com.example.application.components.data.Task;
import com.example.application.components.data.User;
import com.example.application.components.data.database.hibernate.TaskDAO;
import com.example.application.components.elements.tasks.OwnTaskBlockElement;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class TaskSiteContent extends HorizontalLayout {

    public TaskSiteContent() {
        setWidthFull();
        addClassName("taskSiteContent");

        generateTasks();
    }

    private void generateTasks() {
        for (Task task : TaskDAO.getTasks(User.getLoggedInUser())) {
            add(new OwnTaskBlockElement(task));
        }
    }
}
