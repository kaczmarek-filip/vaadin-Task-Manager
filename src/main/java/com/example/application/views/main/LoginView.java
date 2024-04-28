package com.example.application.views.main;


import com.example.application.components.dialogs.RegisterDialog;
import com.example.application.components.elements.ForgotPassword;
import com.example.application.services.LoginService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("login")
@PageTitle("Login")
public class LoginView extends VerticalLayout {
    private Button loginButton;
    private Button registerButton;
    private EmailField emailField = new EmailField("Email");
    private PasswordField passwordField = new PasswordField("Password");

    public LoginView() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        loginButton = new Button("Login");
        loginButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
        loginButton.addClickShortcut(Key.ENTER);
        loginButton.addClickListener(e -> {
            String email = emailField.getValue();
            String password = passwordField.getValue();

            if (LoginService.login(email, password)) {
                getUI().ifPresent(ui -> ui.navigate(MainView.class));
            }
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
        add(new ForgotPassword().display());


    }

    private VerticalLayout setLayout() {

        VerticalLayout verticalLayout = new VerticalLayout(emailField, passwordField);
        verticalLayout.setPadding(false);
        verticalLayout.setSpacing(false);
        verticalLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        verticalLayout.getStyle().set("width", "18rem").set("max-width", "100%");

        return verticalLayout;
    }
}
