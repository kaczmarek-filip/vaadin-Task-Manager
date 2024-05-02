package com.example.application.components.elements.tasks;

import com.example.application.components.data.Task;
import com.example.application.components.data.database.hibernate.TaskDAO;
import com.example.application.components.elements.components.CallbackValues;

public class SingleTaskBlockElement extends AbstractTaskBlockElement {
    public SingleTaskBlockElement(Task task) {
        super(task, task.isDone());
    }

    @Override
    public void onSave(CallbackValues callbackValues) {

        if (callbackValues == CallbackValues.DONE) {
            task.setDone(true);
            done = true;
        } else if (callbackValues == CallbackValues.UNDONE) {
            task.setDone(false);
            done = false;
        } else if (callbackValues == CallbackValues.DELETE) {
            TaskDAO.delete(task);
            setVisible(false);
        }

        TaskDAO.setIsDone(task);

        super.isDoneChecker();
    }
}
