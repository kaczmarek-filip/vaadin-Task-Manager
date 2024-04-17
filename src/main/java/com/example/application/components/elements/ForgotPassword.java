package com.example.application.components.elements;

import com.example.application.components.dialogs.ForgotPasswordDialog;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Paragraph;

public class ForgotPassword extends Button {
    public Button display(){
        setText("Forgot your password?");
        addThemeVariants(ButtonVariant.LUMO_ERROR);
        addClickListener(e -> {
            new ForgotPasswordDialog().open();
        });

        return this;
    }
}
