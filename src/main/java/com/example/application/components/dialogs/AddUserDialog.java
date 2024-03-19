package com.example.application.components.dialogs;

import com.example.application.components.data.Team;
import com.example.application.components.data.User;
import com.example.application.components.data.database.DatabaseConnection;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextArea;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AddUserDialog extends Dialog {
    private Team team;
    private MultiSelectComboBox<User> userComboBoxField = new MultiSelectComboBox<>("Members");
    private ArrayList<User> allUsersWithoutTeamUsers;
    private TextArea selectedUsersArea = new TextArea("Selected Users");
    private Button saveButton = new Button("Save");
    private Button cancelButton = new Button("Cancel");

    public AddUserDialog(Team team){
        this.team = team;
        setHeaderTitle("Add user");
        setCloseOnOutsideClick(false);
        setCloseOnEsc(false);

        FormLayout formLayout = new FormLayout(setUserComboBoxField(), setSelectedUsersArea());

        add(formLayout);
        getFooter().add(setCancelButton());
        getFooter().add(setSaveButton());
    }
    private MultiSelectComboBox<User> setUserComboBoxField() {

        List<User> selectedUsersArrayList = team.getUsersInTeam().keySet().stream().toList();
        allUsersWithoutTeamUsers = User.getAllUsers();
        allUsersWithoutTeamUsers.remove(User.getLoggedInUser());
        allUsersWithoutTeamUsers.removeAll(selectedUsersArrayList);

        userComboBoxField.setItems(allUsersWithoutTeamUsers);

        userComboBoxField.setItemLabelGenerator(User::getDisplayName);
        userComboBoxField.setSelectedItemsOnTop(true);

        userComboBoxField.addValueChangeListener(e -> {
            String selectedUsersText = e.getValue().stream().map(User::getDisplayName).collect(Collectors.joining(" "));
            selectedUsersArea.setValue(selectedUsersText);
        });

        return userComboBoxField;
    }
    private TextArea setSelectedUsersArea() {
        selectedUsersArea.setReadOnly(true);

        return selectedUsersArea;
    }
    private Button setSaveButton(){
        saveButton.addClickListener(e -> {
            Set<User> userComboBox = userComboBoxField.getSelectedItems();
            DatabaseConnection databaseConnection = new DatabaseConnection();
            databaseConnection.insertUsersIntoTeam(team.getId(), userComboBox);

            UI.getCurrent().getPage().reload();
        });
        return saveButton;
    }
    private Button setCancelButton(){
        cancelButton.addClickListener(e -> {
            close();
        });
        return cancelButton;
    }
}
