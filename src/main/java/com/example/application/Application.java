package com.example.application;

import com.example.application.components.data.database.sql.DBEncryption;
import com.example.application.services.encryption.Encrypter;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.theme.Theme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * The entry point of the Spring Boot application.
 * <p>
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 */
@SpringBootApplication
//@Theme(value = "myTheme")
@Theme(value = "mytodo")
@EnableScheduling
@Push

//TODO: Zdjęcia/załaczniki w taskach i zaproszenia do zespołów
public class Application implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        Encrypter.keyRecovery();
        DBEncryption dbEncryption = new DBEncryption();
        try {
            dbEncryption.startupDecrypt();
            Encrypter.generateKey();
            dbEncryption.startupEncrypt();
            Encrypter.saveKey();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
