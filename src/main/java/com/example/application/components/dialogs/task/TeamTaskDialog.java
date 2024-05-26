package com.example.application.components.dialogs.task;

import com.example.application.components.data.Task;
import com.example.application.components.data.TaskHolder;
import com.example.application.components.data.User;
import com.example.application.components.data.database.hibernate.TaskHolderDAO;
import com.example.application.components.elements.tasks.TeamTaskBlockElement;
import lombok.Setter;

public class TeamTaskDialog extends AbstractTaskDialog {

    private final TaskHolder taskHolder;

    @Setter
    private TeamTaskBlockElement parent;

    public TeamTaskDialog(Task task, TaskHolder taskHolder) {
        super(task);
        this.taskHolder = taskHolder;


        if (task.isUserInHolders(User.getLoggedInUser()) != null){
            if (task.isUserInHolders(User.getLoggedInUser()).equals(taskHolder) || task.getCreator().equals(User.getLoggedInUser())){
                canEdit(taskHolder.isPartDone());
            }
        }

    }

    @Override
    protected void onDoneButton() {
        taskHolder.setPartDone(true);
        TaskHolderDAO.setIsPartDone(taskHolder);
        parent.OnChangeReload();
    }

    @Override
    protected void onUnDoneButton() {
        taskHolder.setPartDone(false);
        TaskHolderDAO.setIsPartDone(taskHolder);
        parent.OnChangeReload();
    }

    @Override
    protected void onDeleteButton() {
        TaskHolderDAO.deleteTask(taskHolder);
        parent.setVisible(false);
    }

}
