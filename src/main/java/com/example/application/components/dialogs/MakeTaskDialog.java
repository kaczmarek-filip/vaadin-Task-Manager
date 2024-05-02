package com.example.application.components.dialogs;

import com.example.application.components.data.Task;
import com.example.application.components.data.Team;
import com.example.application.components.data.TeamMember;
import com.example.application.components.data.User;
import com.example.application.components.data.database.hibernate.TaskDAO;
import com.example.application.components.data.database.hibernate.TaskHolderDAO;
import com.example.application.components.elements.components.CancelButton;
import com.example.application.components.elements.components.MyNotification;
import com.example.application.components.elements.components.TextAreaCounter;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MakeTaskDialog extends Dialog {
    private boolean isOwnTask = false;
    private Team team;
    private FormLayout formLayout = new FormLayout();
    private Button createButton;
    private TextField titleTextField;
    private TextAreaCounter descriptionTextArea;
    private DatePicker deadlineDatePicker;
    private Grid<User> userGrid;

    public MakeTaskDialog() {
        setHeaderTitle("Make Task");
        setCloseOnOutsideClick(false);
        setCloseOnEsc(false);

        formLayout.add(title(), description(), deadline());
        add(formLayout);
        getFooter().add(new CancelButton(this), createButton());
    }

    public MakeTaskDialog(Team team) {
        this();
        this.team = team;
        add(setUserGrid());
    }

    private TextField title() {
        titleTextField = new TextField("Title");
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
        deadlineDatePicker.setRequired(true);
        return deadlineDatePicker;
    }

    private Button createButton() {
        createButton = new Button("Create");
        createButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);

        createButton.addClickListener(e -> {
            beforeCreate();
        });
        return createButton;
    }

    private Grid<User> setUserGrid() {
        userGrid = new Grid<>();
        List<TeamMember> teamMembers = team.getTeamMembers();
        List<User> users = teamMembers.stream().map(TeamMember::getUser).collect(Collectors.toList());
        userGrid.setItems(users);

        userGrid.setSelectionMode(Grid.SelectionMode.MULTI);

        userGrid.addColumn(User::getDisplayName).setHeader("Team members");

        return userGrid;
    }

    public MakeTaskDialog onlyOwnTask() {

        this.isOwnTask = true;

        return this;
    }

    private void beforeCreate() {
        String title = titleTextField.getValue();
        String description = descriptionTextArea.getValue();
        LocalDate deadline = deadlineDatePicker.getValue();
        Task task;

        if (title.isEmpty()) {
            MyNotification.show("The title field must not be empty", NotificationVariant.LUMO_SUCCESS, false);
        } else if (deadline == null) {
            MyNotification.show("The deadline field must not be empty", NotificationVariant.LUMO_SUCCESS, false);
        } else {
            if (!isOwnTask) { // if created on SingleTeamSite
                Set<User> selectedUsers = userGrid.getSelectedItems();

                if (!selectedUsers.isEmpty()) { // is one member at least
                    task = new Task(title, description, deadline);
                    task.setTeam(team);

                    TaskDAO.createTask(task);
                    TaskHolderDAO.createHolders(new ArrayList<>(selectedUsers), task);

                    MyNotification.show("Created successfully", NotificationVariant.LUMO_SUCCESS, true);
                    close();
                } else {
                    MyNotification.show("You must choose at least one person", NotificationVariant.LUMO_ERROR, false);
                }

            } else {
                task = new Task(title, description, deadline);
                TaskDAO.createTask(task);

                MyNotification.show("Created successfully", NotificationVariant.LUMO_SUCCESS, true);
                close();
            }
        }
//
    }
}
