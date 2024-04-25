package com.example.application.components.dialogs;

import com.example.application.components.data.Team;
import com.example.application.components.data.TeamMember;
import com.example.application.components.data.User;
import com.example.application.components.data.database.hibernate.TeamDAO;
import com.example.application.components.data.database.hibernate.UserDAO;
import com.example.application.components.elements.components.CancelButton;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextArea;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AddUserToTeamDialog extends Dialog {
    private Team team;
    private MultiSelectComboBox<User> userComboBoxField = new MultiSelectComboBox<>("Members");
    private List<User> allUsersWithoutTeamUsers;
    private TextArea selectedUsersArea = new TextArea("Selected Users");
    private Button saveButton = new Button("Save");

    public AddUserToTeamDialog(Team team){
        this.team = team;
        setHeaderTitle("Add user");
        setCloseOnOutsideClick(false);
        setCloseOnEsc(false);

        FormLayout formLayout = new FormLayout(setUserComboBoxField(), setSelectedUsersArea());

        add(formLayout);
        getFooter().add(new CancelButton(this));
        getFooter().add(setSaveButton());
    }
    private MultiSelectComboBox<User> setUserComboBoxField() {

        List<User> selectedUsersArrayList = team.getTeamMembers().stream().map(TeamMember::getUser).toList();
        allUsersWithoutTeamUsers = UserDAO.getAllUsers();
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

            TeamDAO.addUsers(team, userComboBox);

            UI.getCurrent().getPage().reload();
        });
        return saveButton;
    }
}
