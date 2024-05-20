package com.example.application.components.dialogs;

import com.example.application.components.data.Team;
import com.example.application.components.data.User;
import com.example.application.components.data.database.hibernate.TeamDAO;
import com.example.application.components.data.database.hibernate.UserDAO;
import com.example.application.components.elements.components.CancelButton;
import com.example.application.components.elements.components.TextAreaCounter;
import com.example.application.components.elements.components.notifications.AddToTeamNotification;
import com.example.application.components.elements.components.notifications.SimpleNotification;
import com.example.application.views.main.TeamsSite;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import lombok.Setter;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.application.components.data.Team.mottoCharLimit;

public class CreateTeamDialog extends Dialog {
    private Button createButton;
    private TextField teamNameField = new TextField("Team name");
    private TextAreaCounter teamMottoField = new TextAreaCounter("Motto");
    private MultiSelectComboBox<User> userComboBoxField = new MultiSelectComboBox<>("Members");
    private TextArea selectedUsersArea = new TextArea("Selected Users");
    @Setter
    private TeamsSite parent;

    public CreateTeamDialog() {
        setHeaderTitle("Create team");
        setCloseOnOutsideClick(false);
        setCloseOnEsc(false);

        teamNameField.setRequired(true);
        teamMottoField.setCounter(mottoCharLimit);

        selectedUsersArea.setReadOnly(true);


        FormLayout formLayout = new FormLayout(teamNameField, teamMottoField, setUserComboBoxField(), selectedUsersArea);
        add(formLayout);

        getFooter().add(new CancelButton(this), setCreateButton());
    }

    private void beforeCreate() {
        String teamName = teamNameField.getValue();
        String teamMotto = teamMottoField.getValue();
        Set<User> userComboBox = userComboBoxField.getSelectedItems();

        Team team = new Team();
        team.setName(teamName);
        team.setMotto(teamMotto);

        TeamDAO.createTeam(team, User.getLoggedInUser(), userComboBox);
        close();

        for (User user : userComboBox) {
            new AddToTeamNotification(team, user);
        }

        SimpleNotification.show("New team has been created", NotificationVariant.LUMO_SUCCESS, false);

        parent.OnChangeReload();
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

        List<User> allUsersList = UserDAO.getAllUsers();
        allUsersList.remove(User.getLoggedInUser());

        return AddUserToTeamDialog.getUserMultiSelectComboBox(userComboBoxField, allUsersList, selectedUsersArea);
    }
}
