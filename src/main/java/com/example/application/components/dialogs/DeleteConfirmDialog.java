package com.example.application.components.dialogs;

import com.example.application.components.elements.components.CancelButton;
import com.example.application.components.elements.components.OnSaveReload;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import lombok.Setter;


@Setter
public class DeleteConfirmDialog extends Dialog {

    private OnSaveReload parent;
    private String buttonText = "Delete";

    public DeleteConfirmDialog(String header, String content) {
        setHeaderTitle(header);
        add(content);
        getFooter().add(new CancelButton(this));
    }

    public void setAction(ComponentEventListener<ClickEvent<Button>> componentEventListener) {
        Button deleteButton = new Button(buttonText);
        deleteButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);

        deleteButton.addClickListener(componentEventListener);
        getFooter().add(deleteButton);
        if (parent != null) {
            parent.OnChangeReload();
        }
    }
}
