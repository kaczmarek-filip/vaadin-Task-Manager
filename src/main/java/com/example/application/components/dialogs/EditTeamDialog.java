package com.example.application.components.dialogs;

import com.example.application.components.data.Team;
import com.example.application.components.data.TeamRoles;
import com.example.application.components.data.User;
import com.example.application.components.data.database.DatabaseConnection;
import com.example.application.views.main.TeamsSite;
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

import java.util.Map;
import java.util.stream.Collectors;

public class EditTeamDialog extends Dialog {
    private Button saveButton = new Button("Save");
    private Button deleteButton = new Button("Delete");
    private Button cancelButton = new Button("Cancel");
    private Team team;
    private TextField teamNameField = new TextField("Team name");
    private TextArea teamMottoField = new TextArea("Motto");
    private MultiSelectComboBox<User> userComboBoxField = new MultiSelectComboBox<>("Members");
    private TextArea selectedUsersArea = new TextArea("Selected Users");



    public EditTeamDialog(Team team) {
        this.team = team;
        setHeaderTitle("Edit team");
        setCloseOnOutsideClick(false);
        setCloseOnEsc(false);


        teamNameField.setValue(team.getName());
        teamMottoField.setValue(team.getMotto());

        FormLayout formLayout = new FormLayout(teamNameField, teamMottoField, setUserComboBoxField(), setSelectedUsersArea());

        add(formLayout);
        getFooter().add(setDeleteButton());
        getFooter().add(setCancelButton());
        getFooter().add(setSaveButton());
    }
    private Button setSaveButton(){
        saveButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        return saveButton;
    }
    private Button setDeleteButton(){
        deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        deleteButton.getStyle().set("margin-right", "auto");

        deleteButton.addClickListener(e -> {
//            DatabaseConnection databaseConnection = new DatabaseConnection();
//            databaseConnection.deleteTeam(team.getId());
//
//            UI.getCurrent().navigate(TeamsSite.class);
//
//            Notification notification = new Notification("The team has been deleted", 5000, Notification.Position.BOTTOM_CENTER);
//            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
//            notification.open();
//            close();
            new DeleteConfirmDialog(team).open();
            close();
        });

        return deleteButton;
    }
    private Button setCancelButton(){
        cancelButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        cancelButton.addClickListener(e -> {
            close();
        });
        return cancelButton;
    }
    private TextArea setSelectedUsersArea(){
        selectedUsersArea.setReadOnly(true);

        StringBuilder alreadyUsers = new StringBuilder();

        for (Map.Entry<User, TeamRoles> entry : team.getUsersInTeam().entrySet()){
            alreadyUsers.append(entry.getKey().getDisplayName() + " ");
        }

        selectedUsersArea.setValue(alreadyUsers.toString());

        return selectedUsersArea;
    }

    private MultiSelectComboBox<User> setUserComboBoxField() {
        userComboBoxField.setItems(User.getAllUsers());
        userComboBoxField.select(team.getUsersInTeam().keySet());
        userComboBoxField.setItemLabelGenerator(User::getDisplayName);
        userComboBoxField.setSelectedItemsOnTop(true);

        userComboBoxField.addValueChangeListener(e -> {
            String selectedUsersText = e.getValue().stream().map(User::getDisplayName).collect(Collectors.joining(" "));
            selectedUsersArea.setValue(selectedUsersText);
        });

        return userComboBoxField;
    }
}
