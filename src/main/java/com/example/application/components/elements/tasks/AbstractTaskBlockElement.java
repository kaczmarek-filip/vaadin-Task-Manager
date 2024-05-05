package com.example.application.components.elements.tasks;

import com.example.application.components.data.Task;
import com.example.application.components.dialogs.TaskDialog;
import com.example.application.components.elements.Element;
import com.example.application.components.elements.components.TaskDoneCallback;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import lombok.Getter;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public abstract class AbstractTaskBlockElement extends Element implements TaskDoneCallback {
    @Getter
    protected Task task;
    @Getter
    protected boolean done;
    private final long daysLeft;
    public AbstractTaskBlockElement(Task task, boolean done) {
        super("taskElement");
        this.task = task;
        this.done = done;

        daysLeft = ChronoUnit.DAYS.between(LocalDate.now(), task.getDeadline());
        layout();
        listenerAction();
    }

    @Override
    public void layout() {
        Div title = new Div(task.getTitle());
        title.addClassName("taskElementTitle");

        Div deadline = new Div();
        deadline.addClassName("taskElementDeadline");

        isDoneChecker();

        HorizontalLayout horizontalLayout = new HorizontalLayout();

        if (!done){
            deadline.setText(daysLeft + " days left");
        } else {
            deadline.setText("Deadline: " + task.getFormattedDeadline());
        }

        horizontalLayout.add(deadline);
        add(title);
        add(horizontalLayout);
    }

    @Override
    public void listenerAction() {
        addClickListener(e -> {
            new TaskDialog(this, this).open();
        });
    }

    protected void isDoneChecker(){
        if (done){
            addClassName("task-done");
            removeClassNames("task-late task-almost-late");
            getStyle().set("order", "1");
        } else {
            checkDate();
            getStyle().set("order", "0");
        }
    }

    private void checkDate(){
        removeClassName("task-done");

        if (daysLeft <= 0){
            addClassName("task-late");
        } else if (daysLeft < 3) {
            addClassName("task-almost-late");
        }
    }
}
