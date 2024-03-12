package com.example.application.components.elements;

import com.vaadin.flow.component.html.Div;

public abstract class Element extends Div implements ElementInterface{
    protected String cssSelector;

    public Element() {
        layout();
        listenerAction();
        addClassName(cssSelector);
    }

    @Override
    public void layout() {

    }

    @Override
    public void listenerAction() {

    }
}
