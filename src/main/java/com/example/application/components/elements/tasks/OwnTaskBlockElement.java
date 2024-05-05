package com.example.application.components.elements.tasks;

import com.example.application.components.data.Task;
import com.example.application.components.dialogs.task.OwnTaskDialog;

public class OwnTaskBlockElement extends AbstractTaskBlockElement {

    public OwnTaskBlockElement(Task task) {
        super(task, task.isDone());
    }

    @Override
    public void listenerAction() {

        addClickListener(e -> {
            OwnTaskDialog ownTaskDialog = new OwnTaskDialog(task);
            ownTaskDialog.setParent(this);
            ownTaskDialog.open();
        });
    }

    @Override
    public void OnChangeReload() {

        done = task.isDone();
        super.isDoneChecker();
    }
}
