package com.example.application.components.elements.components;

import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.data.value.ValueChangeMode;


public class TextAreaCounter extends TextArea {

    public void setCounter(int chars){
        setMaxLength(chars);
        setValueChangeMode(ValueChangeMode.EAGER);
        setHelperText(getValue().length() + "/" + chars);
        addValueChangeListener(e -> {
            e.getSource().setHelperText(e.getValue().length() + "/" + chars);
        });
    }

    /*
        Constructors
     */
    public TextAreaCounter() {
    }

    public TextAreaCounter(String label) {
        super(label);
    }

    public TextAreaCounter(String label, String placeholder) {
        super(label, placeholder);
    }

    public TextAreaCounter(String label, String initialValue, String placeholder) {
        super(label, initialValue, placeholder);
    }

    public TextAreaCounter(ValueChangeListener<? super ComponentValueChangeEvent<TextArea, String>> listener) {
        super(listener);
    }

    public TextAreaCounter(String label, ValueChangeListener<? super ComponentValueChangeEvent<TextArea, String>> listener) {
        super(label, listener);
    }

    public TextAreaCounter(String label, String initialValue, ValueChangeListener<? super ComponentValueChangeEvent<TextArea, String>> listener) {
        super(label, initialValue, listener);
    }


}
