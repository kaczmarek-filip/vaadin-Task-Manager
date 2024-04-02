package com.example.application.components.elements;

import com.example.application.components.data.Task;
import com.example.application.components.data.database.TaskDB;
import com.example.application.components.dialogs.TaskDialog;
import com.example.application.components.elements.components.CallbackValues;
import com.example.application.components.elements.components.TaskDoneCallback;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class TaskBlockElement extends Element implements TaskDoneCallback {
    private final Task task;
    private long liczbaDni;
    public TaskBlockElement(Task task) {
        super("taskElement");
        this.task = task;

        liczbaDni = ChronoUnit.DAYS.between(LocalDate.now(), task.getDeadline());

        layout();
        listenerAction();
    }

    @Override
    public void layout() {
        Div title = new Div(task.getTitle());
        title.addClassName("taskElementTitle");
        checkDate();

        Div deadline = new Div(liczbaDni + " days left");
        deadline.addClassName("taskElementDeadline");

        isDoneChecker();

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(deadline);

        add(title);
        if (!task.isDone())
            add(horizontalLayout);
    }

    @Override
    public void listenerAction() {
        addClickListener(e -> {
            new TaskDialog(task, this).open();
        });
    }
    public void checkDate(){
        if (liczbaDni <= 0){
            getStyle().set("background-color", "#ba1a1a");
        } else if (liczbaDni < 3) {
            getStyle().set("background-color", "rgba(255, 86, 74, 0.6)");
        } else {
            getStyle().set("background-color", "#006783");
        }
    }

    @Override
    public void onSave(CallbackValues callbackValues) {

        if (callbackValues == CallbackValues.DONE){
            task.setDone(true);
        } else if (callbackValues == CallbackValues.UNDONE){
            task.setDone(false);
        } else if (callbackValues == CallbackValues.DELETE) {
            new TaskDB().deleteTask(task);
            setVisible(false);
        }
        new TaskDB().setIsDone(task);
        isDoneChecker();
    }

    private void isDoneChecker(){
        if (task.isDone()){
            getStyle().set("background-color", "#03C03C");
            getStyle().set("order", "1");
        } else {
            checkDate();
            getStyle().set("order", "0");
        }
    }
}
