package com.example.application.components.elements.tasks;

import com.example.application.components.data.Task;
import com.example.application.components.data.TaskHolder;
import com.example.application.components.data.User;
import com.example.application.components.dialogs.task.TeamTaskDialog;

public class TeamTaskBlockElement extends AbstractTaskBlockElement {
    private final TaskHolder taskHolder;

    public TeamTaskBlockElement(Task task, User holder) {
        super(task, task.isUserInHolders(holder).isPartDone());
        this.taskHolder = task.isUserInHolders(holder);
        this.done = taskHolder.isPartDone();
    }

    @Override
    public void listenerAction() {

        addClickListener(e -> {
            TeamTaskDialog dialog = new TeamTaskDialog(task, taskHolder);
            dialog.setParent(this);
            dialog.open();
        });
    }

    @Override
    public void OnChangeReload() {

        done = taskHolder.isPartDone();
        super.isDoneChecker();
    }
}
