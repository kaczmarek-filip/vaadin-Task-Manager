package com.example.application.components.dialogs;

import com.example.application.components.data.User;
import com.example.application.components.data.database.HibernateUser;
import com.example.application.components.elements.components.CancelButton;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;

/**
 * Register dialog with user
 */
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
            Notification notification = new Notification("Passwords are not the same", 5000, Notification.Position.BOTTOM_CENTER);
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            notification.open();

        } else if (email.getValue().isEmpty() || displayName.getValue().isEmpty() || password.getValue().isEmpty()) {
            Notification notification = new Notification("Some fields are empty", 5000, Notification.Position.BOTTOM_CENTER);
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            notification.open();
        } else if (password.getValue().length() < 8) {
            Notification notification = new Notification("The password must contain at least 8 characters", 5000, Notification.Position.BOTTOM_CENTER);
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            notification.open();
//        } else if (new UserDB().isEmailExists(user)) {
        } else if (HibernateUser.isEmailExists(emailValue)) {
//        } else if (new UserDBHibernate().isEmailExists(emailValue)) {
            Notification notification = new Notification("There is already a user with the specified email", 5000, Notification.Position.BOTTOM_CENTER);
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            notification.open();
        } else {
//            new UserDB().registerUser(user, passwordValue);
            //TODO: uprzątnąć tu trochę
            HibernateUser.registerUser(user, passwordValue);

            close();
            Notification notification = new Notification("Successfully registered", 5000, Notification.Position.BOTTOM_CENTER);
            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            notification.open();
        }


    }
}
