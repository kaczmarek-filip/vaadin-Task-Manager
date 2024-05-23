package com.example.application.views.main;


import com.example.application.components.dialogs.makeTask.MakeOwnTaskDialog;
import com.example.application.views.main.contents.TaskSiteContent;
import com.example.application.components.elements.components.OnSaveReload;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.router.Route;

@Route("tasks")
public class TasksSite extends Navigation implements OnSaveReload {

    private TaskSiteContent content;
    /**
     * Default constructor
     */
    public TasksSite() {
        super("Tasks");
        Button button = new Button("Make own task");
        button.addClickListener(e -> {
            MakeOwnTaskDialog dialog = new MakeOwnTaskDialog();
            dialog.setParent(this);
            dialog.open();
        });
        addTopNavButton(button);

        content = new TaskSiteContent();
        setContent(content);
    }

    @Override
    public void OnChangeReload() {
        remove(content);
        content = new TaskSiteContent();
        setContent(content);
    }
}
