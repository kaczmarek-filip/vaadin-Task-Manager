package com.example.application;

import com.example.application.components.data.database.sql.DBEncryption;
import com.example.application.services.encryption.Encrypter;
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
        Encrypter.generateKey();

        try {
            new DBEncryption().startupEncrypt();
        } catch (Exception e) {
            Encrypter.keyRecovery();
        }
        Encrypter.saveKey();
    }

}
