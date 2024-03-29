package com.example.application.components.contents;

import com.example.application.components.data.Task;
import com.example.application.components.data.User;
import com.example.application.components.data.database.TaskDB;
import com.example.application.components.elements.TaskBlockElement;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class TaskSiteContent extends HorizontalLayout {

    public TaskSiteContent() {
        setWidthFull();
        addClassName("taskSiteContent");

        generateTasks();
    }

    private void generateTasks() {
        for (Task task : new TaskDB().getOwnTasks(User.getLoggedInUser())) {
            add(new TaskBlockElement(task));
        }
    }
}
