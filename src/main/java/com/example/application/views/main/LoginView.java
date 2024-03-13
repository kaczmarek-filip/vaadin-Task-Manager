package com.example.application.views.main;


import com.example.application.components.DatabaseConnection;
import com.example.application.components.User;
import com.example.application.components.dialogs.RegisterDialog;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("login")
@PageTitle("Login")
//@AnonymousAllowed
public class LoginView extends VerticalLayout{
    private Button loginButton;
    private Button cancelButton;
    private Button registerButton;
    private DatabaseConnection databaseConnection = new DatabaseConnection();
    private EmailField emailField = new EmailField("Email");
    private PasswordField passwordField = new PasswordField("Password");

    public LoginView() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        loginButton = new Button("Login");
        loginButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        loginButton.addClickListener(e -> {
            beforeLogin();
        });

        registerButton = new Button("Register");
        registerButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        registerButton.addClickListener(e -> {
            new RegisterDialog().open();
        });

        HorizontalLayout horizontalLayout = new HorizontalLayout(registerButton, loginButton);

        H1 h1 = new H1("Task Manager");


        add(h1);
        add(setLayout());
        add(horizontalLayout);


    }
    private VerticalLayout setLayout() {

        VerticalLayout verticalLayout = new VerticalLayout(emailField, passwordField);
        verticalLayout.setPadding(false);
        verticalLayout.setSpacing(false);
        verticalLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        verticalLayout.getStyle().set("width", "18rem").set("max-width", "100%");

        return verticalLayout;
    }
    private void beforeLogin() {
        String email = emailField.getValue();
        String password = passwordField.getValue();

        User loggedInUser = databaseConnection.loginUser(email, password);

        if (loggedInUser != null) {
//            close();
            Notification notification = new Notification("Logged in", 5000, Notification.Position.BOTTOM_CENTER);
            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            notification.open();

            User.getInstance().setId(loggedInUser.getId());
            User.getInstance().setDisplayName(loggedInUser.getDisplayName());
            User.getInstance().setEmail(loggedInUser.getEmail());

            Notification.show(loggedInUser.getDisplayName());
            getUI().ifPresent(ui -> ui.navigate(MainView.class));


        } else {
            Notification notification = new Notification("Incorrect login data", 5000, Notification.Position.BOTTOM_CENTER);
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            notification.open();
        }
    }
}
