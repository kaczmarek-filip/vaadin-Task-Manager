package com.example.application.components;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;

//TODO: isDone w konstruktorze

//@Tag("div")
public class TaskElement extends Div{

//    MoreInfo moreInfo = new MoreInfo(this);
    private boolean isPressed = false;
    public TaskElement() {
        primaryInfo();

//        addClickListener(e -> {
//           isPressed = !isPressed;
//
//            if(isPressed){
//                moreInfo();
//                isPressed = true;
//            } else {
//                primaryInfo();
//                isPressed = false;
//            }
//        });
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

//    private void moreInfo(){
//        addClassName("deadlineElement");
//        setText("Betlejemskie Światło Pokoju");
//
//
//        Span date = new Span("21.12.2002");
//        date.getElement().getThemeList().add("badge");
//        date.addClassName("deadlineSpan");
//
//        Span time = new Span("18:00");
//        time.getElement().getThemeList().add("badge error");
//        time.addClassName("deadlineSpan");
//
//
//        add(date, time);
//        add("dsfybapiuagyfpdaiuygfdapuiydfgapiudfgyaoudfygapdfagiuydpofuaiygdfopuayigdfpiauygdfpa");
//    }
}
