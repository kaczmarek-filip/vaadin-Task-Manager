package com.example.application.components.elements.tasks;

import com.example.application.components.data.Task;
import com.example.application.components.data.TaskHolder;
import com.example.application.components.data.User;
import com.example.application.components.data.database.hibernate.TaskHolderDAO;
import com.example.application.components.elements.components.CallbackValues;

public class TeamTaskBlockElement extends AbstractTaskBlockElement {
    private final TaskHolder taskHolder;

    public TeamTaskBlockElement(Task task, User holder) {
        super(task, task.getHolderFromUser(holder).isPartDone());
        this.taskHolder = task.getHolderFromUser(holder);
        this.done = taskHolder.isPartDone();
    }

    @Override
    public void onSave(CallbackValues callbackValues) {

        if (callbackValues == CallbackValues.DONE) {
            done = true;
        } else if (callbackValues == CallbackValues.UNDONE) {
            done = false;
        } else if (callbackValues == CallbackValues.DELETE) {
            TaskHolderDAO.deleteTask(taskHolder);
            setVisible(false);
        }

        taskHolder.setPartDone(done);
        TaskHolderDAO.setIsPartDone(taskHolder);

        isDoneChecker();
    }
}
