package com.example.application.components.dialogs;

import com.example.application.components.data.Team;
import com.example.application.components.data.TeamMember;
import com.example.application.components.data.TeamRoles;
import com.example.application.components.data.User;
import com.example.application.components.data.database.HibernateTeam;
import com.example.application.components.data.database.sql.SQLTeamDB;
import com.example.application.components.elements.components.CancelButton;
import com.example.application.components.elements.components.TextAreaCounter;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;

import java.util.*;

import static com.example.application.components.data.Team.mottoCharLimit;

public class EditTeamDialog extends Dialog {
    private Button saveButton = new Button("Save");
    private Button deleteButton = new Button("Delete");
    private Button addUserButton = new Button("Add users");
    private Team team;
    private TextField teamNameField = new TextField("Team name");
    private TextAreaCounter teamMottoField = new TextAreaCounter("Motto");
    private Grid<TeamMember> grid = new Grid<>();
//    private Set<Map.Entry<User, TeamRoles>> items;
    private Set<TeamMember> items;

    public EditTeamDialog(Team team) {
        this.team = team;
        setHeaderTitle("Edit team");
        setCloseOnOutsideClick(false);
        setCloseOnEsc(false);
        setWidth("50%");


        teamNameField.setValue(team.getName());
        teamNameField.setWidthFull();
        teamMottoField.setValue(team.getMotto());
        teamMottoField.setWidthFull();
        teamMottoField.setCounter(mottoCharLimit);

        HorizontalLayout horizontalLayout = new HorizontalLayout(setAddUserButton(), setSaveButton());
        horizontalLayout.setWidthFull();
        horizontalLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);

        VerticalLayout verticalLayout = new VerticalLayout(teamNameField, teamMottoField, horizontalLayout, setGridContextMenu());
//        VerticalLayout verticalLayout = new VerticalLayout(teamNameField, teamMottoField, setGridContextMenu());

        add(verticalLayout);
        getFooter().add(setDeleteButton());
        getFooter().add(new CancelButton(this));
    }

    private void beforeEdit() {
        String teamName = teamNameField.getValue();
        String teamMotto = teamMottoField.getValue();

//        new SQLTeamDB().updateTeamInfo(team.getId(), teamName, teamMotto);
        HibernateTeam.updateInfo(team.getId(), teamName, teamMotto);
    }

    private Button setAddUserButton() {
        addUserButton.addClickListener(e -> {
            new AddUserToTeamDialog(team).open();
        });
        addUserButton.getStyle().set("margin-right", "auto");
        return addUserButton;
    }

    private Button setSaveButton() {
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);

        saveButton.getStyle().set("margin-left", "auto");

        saveButton.addClickListener(e -> {
            beforeEdit();
            UI.getCurrent().getPage().reload();
        });

        return saveButton;
    }

    private Button setDeleteButton() {
        deleteButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);
        deleteButton.getStyle().set("margin-right", "auto");

        deleteButton.addClickListener(e -> {
            new DeleteConfirmDialog().deleteTeam(team).open();
            close();
        });

        return deleteButton;
    }

    private Grid<TeamMember> setGridContextMenu() {

        Grid<TeamMember> grid = new Grid<>();
        List<TeamMember> list = team.getTeamMembers();
        grid.setItems(list);

        grid.addColumn(teamMember -> teamMember.getUser().getDisplayName())
                        .setHeader("Display name");
        grid.addColumn(teamMember -> teamMember.getUser().getEmail())
                .setHeader("Email");

        grid.addComponentColumn(teamMember -> {
            Select<TeamRoles> rolesSelect = new Select<>();
            Set<TeamRoles> rolesSet = EnumSet.allOf(TeamRoles.class);
            rolesSet.remove(TeamRoles.OWNER);
            rolesSelect.setItems(rolesSet);
            rolesSelect.setValue(teamMember.getRole());


            rolesSelect.addValueChangeListener(event -> {
                teamMember.setRole(event.getValue());
//                new SQLTeamDB().updateUserRole(team, teamMember.getUser(), event.getValue());
                HibernateTeam.updateRole(team, teamMember.getUser(), event.getValue());
            });

            if (teamMember.getRole() == TeamRoles.OWNER) {
                rolesSelect.setReadOnly(true);
            }

            return rolesSelect;
        }).setHeader("User Role");


        grid.addComponentColumn(teamMember -> {

            Button button = new Button();
            button.setIcon(VaadinIcon.TRASH.create());
            button.addThemeVariants(ButtonVariant.LUMO_ERROR);

            if (teamMember.getRole() == TeamRoles.OWNER) {
                button.setEnabled(false);
            }

            button.addClickListener(e -> {
                new DeleteConfirmDialog().deleteUser(team, teamMember.getUser()).open();
            });

            return button;
        }).setFlexGrow(0);

        return grid;
    }
}
