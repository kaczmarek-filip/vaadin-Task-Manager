package com.example.application.components.elements.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;

public class CancelButton extends Button{
    private final Dialog dialog;
    public CancelButton(Dialog dialog) {
        this.dialog = dialog;

        cancelButton();
    }

    public void cancelButton() {
        setText("Cancel");

        addClickListener(e -> {
            dialog.close();
        });
        addThemeVariants(ButtonVariant.LUMO_ERROR);
    }
}
