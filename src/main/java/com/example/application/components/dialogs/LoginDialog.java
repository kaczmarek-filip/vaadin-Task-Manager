package com.example.application.components.dialogs;

import com.example.application.components.DatabaseConnection;
import com.example.application.components.User;
import com.example.application.views.main.MainView;
import com.example.application.views.main.Navigation;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;

/**
 * Loggin dialog with user
 */
public final class LoginDialog extends Dialog {

    private Button loginButton;
    private Button cancelButton;
    private Button registerButton;
    private DatabaseConnection databaseConnection = new DatabaseConnection();

    private EmailField emailField = new EmailField("Email");
    private PasswordField passwordField = new PasswordField("Password");

    /**
     * No args constructor
     */
    public LoginDialog() {
        setCloseOnOutsideClick(false);
        setCloseOnEsc(false);
        setHeaderTitle("Login");

        loginButton = new Button("Login");
        loginButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        loginButton.addClickListener(e -> {
            beforeLogin();
        });

        cancelButton = new Button("Cancel");
        cancelButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancelButton.addClickListener(e -> {
            close();
        });

        registerButton = new Button("Register");
        registerButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        registerButton.addClickListener(e -> {
            new RegisterDialog().open();
        });
        registerButton.getStyle().set("margin-right", "auto");


        add(setLayout());
        getFooter().add(registerButton);
        getFooter().add(cancelButton);
        getFooter().add(loginButton);
    }

    /**
     * @return layout
     * @see VerticalLayout
     */
    private VerticalLayout setLayout() {

        VerticalLayout verticalLayout = new VerticalLayout(emailField, passwordField);
        verticalLayout.setPadding(false);
        verticalLayout.setSpacing(false);
        verticalLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        verticalLayout.getStyle().set("width", "18rem").set("max-width", "100%");

        return verticalLayout;
    }

    /**
     * Checking for correct login data
     * @see DatabaseConnection
     * @see User
     */
    private void beforeLogin() {
        String email = emailField.getValue();
        String password = passwordField.getValue();

        User loggedInUser = databaseConnection.loginUser(email, password);

        if (loggedInUser != null) {
            close();
            Notification notification = new Notification("Logged in", 5000, Notification.Position.BOTTOM_CENTER);
            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            notification.open();

            User.getInstance().setId(loggedInUser.getId());
            User.getInstance().setDisplayName(loggedInUser.getDisplayName());
            User.getInstance().setEmail(loggedInUser.getEmail());

            Notification.show(loggedInUser.getDisplayName());

        } else {
            Notification notification = new Notification("Incorrect login data", 5000, Notification.Position.BOTTOM_CENTER);
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            notification.open();
        }
    }
}