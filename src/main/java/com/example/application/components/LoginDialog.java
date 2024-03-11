package com.example.application.components;//package com.vaadin.demo.component.dialog;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.router.Route;

@Route("dialog-basic")
public class LoginDialog extends Dialog {

    private Button emailButton;
    private Button cancelButton;

    public LoginDialog() {

        setHeaderTitle("Login");

        emailButton = new Button("Login");
        emailButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);

        cancelButton = new Button("Cancel");
        cancelButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancelButton.addClickListener(e -> {
           close();
        });



        add(setLayout());
        getFooter().add(cancelButton);
        getFooter().add(emailButton);
    }

    private VerticalLayout setLayout(){
        EmailField firstNameField = new EmailField("Email");
        PasswordField lastNameField = new PasswordField("Password");

        VerticalLayout verticalLayout = new VerticalLayout(firstNameField, lastNameField);
        verticalLayout.setPadding(false);
        verticalLayout.setSpacing(false);
        verticalLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        verticalLayout.getStyle().set("width", "18rem").set("max-width", "100%");

        return verticalLayout;
    }
}