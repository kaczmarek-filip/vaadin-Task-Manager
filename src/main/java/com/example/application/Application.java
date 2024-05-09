package com.example.application;

import com.example.application.components.data.database.hibernate.HibernateConnection;
import com.example.application.components.data.database.sql.DBEncryption;
import com.example.application.services.encryption.Encrypter;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

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
@EnableScheduling
public class Application implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        Encrypter.generateKey();

        try {
            new DBEncryption().startupEncrypt();
        } catch (Exception e) {
            Encrypter.keyRecovery();
        }
        Encrypter.saveKey();

//        HibernateConnection.start();
    }

}
