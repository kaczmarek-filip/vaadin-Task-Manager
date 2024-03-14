package com.example.application.components.elements;

public class TeamsMemberElement extends Element {

    public TeamsMemberElement() {
        super("TeamsMemberElement");
        layout();
        listenerAction();
    }

    @Override
    public void layout() {
        setText("Filip Kaczmarek");
    }

    @Override
    public void listenerAction() {

    }
}
