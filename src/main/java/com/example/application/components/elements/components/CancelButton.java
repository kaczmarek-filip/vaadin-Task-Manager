package com.example.application.components.elements.components;

import com.example.application.components.data.database.HibernateConnection;
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
//            HibernateConnection.flush();
            //TODO: Callback po którym jeszcze raz wczytujemy informacje
        });
        addThemeVariants(ButtonVariant.LUMO_ERROR);
    }
}
