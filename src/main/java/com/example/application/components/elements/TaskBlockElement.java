package com.example.application.components.elements;

import com.example.application.components.data.Task;
import com.example.application.components.dialogs.TaskDialog;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class TaskBlockElement extends Element{
    private final Task task;
    public TaskBlockElement(Task task) {
        super("taskElement");
        this.task = task;

        layout();
        listenerAction();
    }

    @Override
    public void layout() {
        Div title = new Div(task.getTitle());
        title.addClassName("taskElementTitle");

        long liczbaDni = ChronoUnit.DAYS.between(LocalDate.now(), task.getDeadline());

        Div deadline = new Div(liczbaDni + " days left");
        deadline.addClassName("taskElementDeadline");

        Checkbox checkbox = new Checkbox();
        checkbox.getElement().getStyle().set("margin-left", "auto");
        checkbox.addClassName("taskElementCheckbox");
        checkbox.addClickListener(e -> {});

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(deadline, checkbox);

        add(title);
//        add(deadline);
//        add(checkbox);
        add(horizontalLayout);
    }

    @Override
    public void listenerAction() {
        addClickListener(e -> {
            new TaskDialog(task).open();
        });
    }
}
