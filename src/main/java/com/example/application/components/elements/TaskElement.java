package com.example.application.components.elements;

import com.example.application.components.dialogs.TaskDialog;
import com.vaadin.flow.component.html.Span;

//TODO: isDone w konstruktorze


/**
 * Displayed element with Tasks
 */
public class TaskElement extends Element {

    public TaskElement() {
        super("deadlineElement");
        layout();
        listenerAction();
    }

    @Override
    public void layout() {

        setText("Betlejemskie Światło Pokoju");


        Span date = new Span("21.12.2002");
        date.getElement().getThemeList().add("badge");
        date.addClassName("deadlineSpan");

        Span time = new Span("18:00");
        time.getElement().getThemeList().add("badge error");
        time.addClassName("deadlineSpan");


        add(date, time);
    }

    @Override
    public void listenerAction() {
        addClickListener(e -> {
            new TaskDialog().open();
        });
    }
}
