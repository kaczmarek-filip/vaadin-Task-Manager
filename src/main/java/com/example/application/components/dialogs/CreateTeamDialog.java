package com.example.application.components.dialogs;

import com.example.application.components.data.User;
import com.example.application.components.data.database.DatabaseConnection;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;

import java.util.Set;

public class CreateTeamDialog extends Dialog {
    private Button cancelButton;
    private Button createButton;
    TextField teamNameField = new TextField("Team name");
    TextArea teamMottoField = new TextArea("Motto");
    MultiSelectComboBox<User> userComboBoxField = new MultiSelectComboBox<>("Members");

    public CreateTeamDialog() {
        setHeaderTitle("Create team");
        setCloseOnOutsideClick(false);
        setCloseOnEsc(false);

        cancelButton = new Button("Cancel");
        cancelButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancelButton.addClickListener(e -> {
            close();
        });
        createButton = new Button("Create");
        createButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        createButton.addClickShortcut(Key.ENTER);
        createButton.addClickListener(e -> {
            beforeCreate();
        });
        teamNameField.setRequired(true);

        userComboBoxField.setItems(User.getAllUsers());
        userComboBoxField.setItemLabelGenerator(User::getDisplayName);
        userComboBoxField.setSelectedItemsOnTop(true);
        FormLayout formLayout = new FormLayout(teamNameField, teamMottoField, userComboBoxField);
        add(formLayout);

        getFooter().add(cancelButton, createButton);
    }

    private void beforeCreate() {
        String teamName = teamNameField.getValue();
        String teamMotto = teamMottoField.getValue();
        Set<User> userComboBox = userComboBoxField.getSelectedItems();

        DatabaseConnection databaseConnection = new DatabaseConnection();
        databaseConnection.createTeam(teamName, teamMotto, User.getLoggedInUser(), userComboBox);
        close();

        UI.getCurrent().getPage().reload();

        Notification notification = new Notification("The team has been created", 5000, Notification.Position.BOTTOM_CENTER);
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        notification.open();


    }
}
