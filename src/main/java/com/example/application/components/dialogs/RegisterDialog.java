package com.example.application.components.dialogs;

import com.example.application.components.data.User;
import com.example.application.components.data.database.hibernate.UserDAO;
import com.example.application.components.elements.components.CancelButton;
import com.example.application.components.elements.components.MyNotification;
import com.example.application.services.LoginService;
import com.example.application.views.main.MainView;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;

/**
 * Register dialog with user
 */
//TODO: Dodać automatyczne logowanie użytkownika po rejestracji
public class RegisterDialog extends Dialog {
    private Button registerButton;

    EmailField email = new EmailField("Email");
    TextField displayName = new TextField("Display name");
    PasswordField password = new PasswordField("Password");
    PasswordField confirmPassword = new PasswordField("Confirm password");

    public RegisterDialog() {
        setHeaderTitle("Register");
        setCloseOnOutsideClick(false);
        setCloseOnEsc(false);

        registerButton = new Button("Register");
        registerButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
        registerButton.addClickShortcut(Key.ENTER);
        registerButton.addClickListener(e -> {
            beforeRegister();
        });

        add(layout());
        getFooter().add(new CancelButton(this));
        getFooter().add(registerButton);
    }

    private FormLayout layout() {

        FormLayout formLayout = new FormLayout(email, displayName, password, confirmPassword);

        return formLayout;
    }

    /**
     * Validation of registration data
     */
    private void beforeRegister() {

        String displayNameValue = displayName.getValue();
        String emailValue = email.getValue();
        String passwordValue = password.getValue();

        User user = new User(0, displayNameValue, emailValue);

        if (!password.getValue().equals(confirmPassword.getValue())) {
            MyNotification.show("Passwords are not the same", NotificationVariant.LUMO_ERROR, false);

        } else if (email.getValue().isEmpty() || displayName.getValue().isEmpty() || password.getValue().isEmpty()) {
            MyNotification.show("Some fields are empty", NotificationVariant.LUMO_ERROR, false);

        } else if (password.getValue().length() < 8) {
            MyNotification.show("The password must contain at least 8 characters", NotificationVariant.LUMO_ERROR, false);

        } else if (UserDAO.isEmailExists(emailValue)) {
            MyNotification.show("There is already a user with the specified email", NotificationVariant.LUMO_ERROR, false);

        } else {
            UserDAO.registerUser(user, passwordValue);
            LoginService.login(emailValue, passwordValue);
            getUI().ifPresent(ui -> ui.navigate(MainView.class));

            MyNotification.show("Successfully registered", NotificationVariant.LUMO_SUCCESS, false);
            close();
        }


    }
}
