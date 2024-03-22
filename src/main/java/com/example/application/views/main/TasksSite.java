package com.example.application.views.main;


import com.example.application.components.dialogs.MakeTaskDialog;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.router.Route;

@Route("tasks")
public class TasksSite extends Navigation{
    /**
     * Default constructor
     */
    public TasksSite() {
        super("Tasks");
        Button button = new Button("Make own task");
        button.addClickListener(e -> {
            new MakeTaskDialog().onlyOwnTask().open();
        });
        addTopNavButton(button);
    }
}
