package com.example.application.components;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;

//TODO: isDone w konstruktorze


/**
 * Displayed element with Tasks
 */
public class TaskElement extends Div{

    private boolean isPressed = false;
    public TaskElement() {
        primaryInfo();

        addClickListener(e -> {
            new TaskDialog().open();
        });

    }
    private void primaryInfo(){
        addClassName("deadlineElement");
        setText("Betlejemskie Światło Pokoju");


        Span date = new Span("21.12.2002");
        date.getElement().getThemeList().add("badge");
        date.addClassName("deadlineSpan");

        Span time = new Span("18:00");
        time.getElement().getThemeList().add("badge error");
        time.addClassName("deadlineSpan");


        add(date, time);

    }
}
