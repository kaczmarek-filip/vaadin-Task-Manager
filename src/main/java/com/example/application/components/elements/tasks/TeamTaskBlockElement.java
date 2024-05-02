package com.example.application.components.elements.tasks;

import com.example.application.components.data.Task;
import com.example.application.components.data.TaskHolder;
import com.example.application.components.data.User;
import com.example.application.components.data.database.hibernate.TaskDAO;
import com.example.application.components.data.database.hibernate.TaskHolderDAO;
import com.example.application.components.elements.components.CallbackValues;

public class TeamTaskBlockElement extends AbstractTaskBlockElement {
    //TODO: Możliwość edycji tasków tylko swoich lub creator
    private TaskHolder taskHolder;

    public TeamTaskBlockElement(Task task, User holder) {

        super(task, TaskHolderDAO.getTaskHolder(task, holder).isPartDone());
        this.taskHolder = TaskHolderDAO.getTaskHolder(task, holder);
        this.done = taskHolder.isPartDone();
    }

    @Override
    public void onSave(CallbackValues callbackValues) {

        if (callbackValues == CallbackValues.DONE){
            done = true;
        } else if (callbackValues == CallbackValues.UNDONE){
            done = false;
        } else if (callbackValues == CallbackValues.DELETE) {
            TaskDAO.delete(task); //TODO: delete team tasks
            setVisible(false);
        }

        taskHolder.setPartDone(done);
        TaskHolderDAO.setIsPartDone(taskHolder);

        isDoneChecker();
    }
}
