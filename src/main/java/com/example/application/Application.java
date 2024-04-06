package com.example.application;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The entry point of the Spring Boot application.
 *
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 *
 */
@SpringBootApplication
//@Theme(value = "myTheme")
@Theme(value = "mytodo")
public class Application implements AppShellConfigurator {

    /**
     * @param args main method
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    //TODO: Dodać szyfrowanie haseł i wiadomości
    //TODO: Dodać przykładową bazę danych
    //TODO: 404 error

}
