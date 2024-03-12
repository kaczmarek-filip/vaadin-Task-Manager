package com.example.application.components.elements;

public class TeamsMemberElement extends Element{

    @Override
    public void layout() {
        super.layout();
        cssSelector = "TeamsMemberElement";

        setText("Filip Kaczmarek");
    }

    @Override
    public void listenerAction() {

    }
}
