package com.example.application.components.dialogs.makeTask;

import com.example.application.components.data.Task;
import com.example.application.components.data.database.hibernate.TaskDAO;
import com.example.application.components.elements.components.MyNotification;
import com.example.application.views.main.TasksSite;
import com.vaadin.flow.component.notification.NotificationVariant;
import lombok.Setter;

public class MakeOwnTaskDialog extends AbstractMakeTask {

    @Setter
    private TasksSite parent;

    @Override
    void onCreate() {

        Task task = new Task(title, description, deadline);
        TaskDAO.createTask(task);

        parent.OnChangeReload();
        MyNotification.show("Created successfully", NotificationVariant.LUMO_SUCCESS, false);
        close();
    }
}
