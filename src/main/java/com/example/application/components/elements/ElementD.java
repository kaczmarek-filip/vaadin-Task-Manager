package com.example.application.components.elements;

import com.vaadin.flow.component.html.Div;

@Deprecated
public abstract class ElementD extends Div implements ElementInterface{

    protected String cssSelector;
    public ElementD() {
        layout();
        listenerAction();
        addClassName(cssSelector);
    }
}
