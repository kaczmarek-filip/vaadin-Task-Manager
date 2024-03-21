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
import com.vaadin.flow.data.value.ValueChangeMode;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.application.components.data.Team.mottoCharLimit;

public class CreateTeamDialog extends Dialog {
    private Button cancelButton;
    private Button createButton;
    private TextField teamNameField = new TextField("Team name");
    private TextArea teamMottoField = new TextArea("Motto");
    private MultiSelectComboBox<User> userComboBoxField = new MultiSelectComboBox<>("Members");
    private TextArea selectedUsersArea = new TextArea("Selected Users");

    public CreateTeamDialog() {
        setHeaderTitle("Create team");
        setCloseOnOutsideClick(false);
        setCloseOnEsc(false);

        teamNameField.setRequired(true);
        teamMottoField.setMaxLength(mottoCharLimit);
        teamMottoField.setValueChangeMode(ValueChangeMode.EAGER);
        teamMottoField.addValueChangeListener(e -> {
            e.getSource().setHelperText(e.getValue().length() + "/" + mottoCharLimit);
        });

        selectedUsersArea.setReadOnly(true);


        FormLayout formLayout = new FormLayout(teamNameField, teamMottoField, setUserComboBoxField(), selectedUsersArea);
        add(formLayout);

        getFooter().add(setCancelButton(), setCreateButton());
    }

    private void beforeCreate() {
        String teamName = teamNameField.getValue();
        String teamMotto = teamMottoField.getValue();
        Set<User> userComboBox = userComboBoxField.getSelectedItems();

        DatabaseConnection databaseConnection = new DatabaseConnection();
        databaseConnection.createTeam(teamName, teamMotto, User.getLoggedInUser(), userComboBox);
        close();

        Notification notification = new Notification("The team has been created", 5000, Notification.Position.BOTTOM_CENTER);
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        notification.open();

        UI.getCurrent().getPage().reload();
    }

    private Button setCancelButton() {
        cancelButton = new Button("Cancel");
        cancelButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancelButton.addClickListener(e -> {
            close();
        });

        return cancelButton;
    }

    private Button setCreateButton() {
        createButton = new Button("Create");
        createButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        createButton.addClickShortcut(Key.ENTER);
        createButton.addClickListener(e -> {
            beforeCreate();
        });

        return createButton;
    }

    private MultiSelectComboBox<User> setUserComboBoxField() {

        List<User> allUsersList = User.getAllUsers();
        allUsersList.remove(User.getLoggedInUser());

        userComboBoxField.setItems(allUsersList);
        userComboBoxField.setItemLabelGenerator(User::getDisplayName);
        userComboBoxField.setSelectedItemsOnTop(true);

        userComboBoxField.addValueChangeListener(e -> {
            String selectedUsersText = e.getValue().stream().map(User::getDisplayName).collect(Collectors.joining(" "));
            selectedUsersArea.setValue(selectedUsersText);
        });

        return userComboBoxField;
    }
}
