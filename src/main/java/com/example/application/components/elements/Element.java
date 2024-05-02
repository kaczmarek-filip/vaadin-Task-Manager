package com.example.application.components.elements;

import com.vaadin.flow.component.html.Div;

public abstract class Element extends Div implements ElementInterface{
    public Element(String cssSelector){
        addClassName(cssSelector);
    }
}
