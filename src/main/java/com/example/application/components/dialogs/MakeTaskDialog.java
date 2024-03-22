package com.example.application.components.dialogs;

import com.example.application.components.data.Task;
import com.example.application.components.data.Team;
import com.example.application.components.data.TeamRoles;
import com.example.application.components.data.User;
import com.example.application.components.data.database.DatabaseConnection;
import com.example.application.components.elements.components.TextAreaCounter;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dnd.GridDropMode;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class MakeTaskDialog extends Dialog {
    private boolean isOwnTask = false;
    private Team team;
    private FormLayout formLayout = new FormLayout();
    private Button cancelButton;
    private Button createButton;
    private TextField titleTextField;
    private TextAreaCounter descriptionTextArea;
    private DatePicker deadlineDatePicker;
    private MultiSelectComboBox<User> multiSelectComboBox;
    private Grid<User> userGrid;

    public MakeTaskDialog() {
        setHeaderTitle("Make Task");
        setCloseOnOutsideClick(false);
        setCloseOnEsc(false);

        formLayout.add(title(), description(), deadline());
        add(formLayout);
        getFooter().add(cancelButton(), createButton());
    }

    public MakeTaskDialog(Team team) {
        this();
        this.team = team;
        add(setUserGrid());
    }

    private TextField title() {
        titleTextField = new TextField("title");
        titleTextField.setRequired(true);
        return titleTextField;
    }

    private TextArea description() {
        descriptionTextArea = new TextAreaCounter("Description");
        descriptionTextArea.setCounter(Task.descriptionMaxLength);
        return descriptionTextArea;
    }

    private DatePicker deadline() {
        deadlineDatePicker = new DatePicker("Deadline");
        deadlineDatePicker.setMin(LocalDate.now());
        return deadlineDatePicker;
    }

    private Button cancelButton() {
        cancelButton = new Button("Cancel");
        cancelButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);

        cancelButton.addClickListener(e -> {
            close();
        });

        return cancelButton;
    }

    private Button createButton() {
        createButton = new Button("Create");
        createButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);

        createButton.addClickListener(e -> {
            beforeCreate();
        });
        return createButton;
    }
    @Deprecated
    private MultiSelectComboBox<User> setUserComboBoxField() {
        multiSelectComboBox = new MultiSelectComboBox("Holders");

        Map<User, TeamRoles> usersInTeam = team.getUsersInTeam();

        multiSelectComboBox.setItems(usersInTeam.keySet());
        multiSelectComboBox.setWidthFull();

        multiSelectComboBox.setItemLabelGenerator(User::getDisplayName);
        multiSelectComboBox.setSelectedItemsOnTop(true);

        return multiSelectComboBox;
    }
    private Grid<User> setUserGrid(){
        userGrid = new Grid<>();
        userGrid.setItems(team.getUsersInTeam().keySet());

        userGrid.setSelectionMode(Grid.SelectionMode.MULTI);

        userGrid.addColumn(User::getDisplayName).setHeader("Team members");

        return userGrid;
    }
    public MakeTaskDialog onlyOwnTask(){

        this.isOwnTask = true;

        return this;
    }
    private void beforeCreate() {
        String title = titleTextField.getValue();
        String description = descriptionTextArea.getValue();
        LocalDate deadline = deadlineDatePicker.getValue();
        Task task = null;
        
        if (!isOwnTask){ // if created on SingleTeamSite
            Set<User> selectedUsers = userGrid.getSelectedItems();

            if(!selectedUsers.isEmpty()){ // is one member at least
                task = new Task(title, description, LocalDate.now(), deadline, User.getLoggedInUser(), selectedUsers);

                Notification notification = new Notification("Created successfully", 5000, Notification.Position.BOTTOM_CENTER);
                notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                notification.open();
                close();
            } else {
                Notification notification = new Notification("You must choose at least one person", 5000, Notification.Position.BOTTOM_CENTER);
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                notification.open();
            }

        } else {
            task = new Task(title, description, LocalDate.now(), deadline, User.getLoggedInUser(), null);

            Notification notification = new Notification("Created successfully", 5000, Notification.Position.BOTTOM_CENTER);
            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            notification.open();
            close();
        }
        new DatabaseConnection().createTask(task);
    }
}
