package com.example.application.components.dialogs.task;

import com.example.application.components.data.Task;
import com.example.application.components.data.database.hibernate.TaskDAO;
import com.example.application.components.elements.tasks.OwnTaskBlockElement;
import lombok.Setter;

public class OwnTaskDialog extends AbstractTaskDialog {

    @Setter
    private OwnTaskBlockElement parent;

    public OwnTaskDialog(Task task) {
        super(task);

        canEdit(task.isDone());
    }

    @Override
    protected void onDoneButton() {
        task.setDone(true);
        TaskDAO.setIsDone(task);
        parent.OnChangeReload();
    }

    @Override
    protected void onUnDoneButton() {
        task.setDone(false);
        TaskDAO.setIsDone(task);
        parent.OnChangeReload();
    }

    @Override
    protected void onDeleteButton() {
        TaskDAO.delete(task);
        parent.setVisible(false);
    }
}
