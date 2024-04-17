package com.example.application.components.dialogs;

import com.example.application.components.elements.components.CancelButton;
import com.example.application.services.ForgotPasswordService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.textfield.EmailField;

public class ForgotPasswordDialog extends Dialog {
    EmailField emailField = new EmailField();
    public ForgotPasswordDialog() {
        setHeaderTitle("Forgot password");
        setCloseOnEsc(false);
        setCloseOnOutsideClick(false);

        add(emailField());
        getFooter().add(new CancelButton(this), send());
    }
    private EmailField emailField(){
        return emailField;
    }
    private Button send(){
        Button button = new Button();
        button.setText("Reset");

        button.addClickListener(e -> {
            String email = emailField.getValue();

           new ForgotPasswordService().sendEmail(email);
        });
        return button;
    }
}
