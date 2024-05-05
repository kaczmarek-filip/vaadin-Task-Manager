package com.example.application.components.dialogs;

import com.example.application.components.data.Team;
import com.example.application.components.data.TeamMember;
import com.example.application.components.data.TeamRoles;
import com.example.application.components.data.database.hibernate.HibernateConnection;
import com.example.application.components.data.database.hibernate.TeamDAO;
import com.example.application.components.elements.components.CancelButton;
import com.example.application.components.elements.components.MyNotification;
import com.example.application.components.elements.components.OnSaveReload;
import com.example.application.components.elements.components.TextAreaCounter;
import com.example.application.views.main.SingleTeamSite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import lombok.Setter;

import java.util.*;

import static com.example.application.components.data.Team.mottoCharLimit;

public class EditTeamDialog extends Dialog implements OnSaveReload {
    private Button saveButton = new Button("Save");
    private Button deleteButton = new Button("Delete");
    private Button addUserButton = new Button("Add users");
    private Team team;
    private TextField teamNameField = new TextField("Team name");
    private TextAreaCounter teamMottoField = new TextAreaCounter("Motto");

    private Grid<TeamMember> grid = new Grid<>();
    private VerticalLayout verticalLayout = new VerticalLayout();
    private HorizontalLayout horizontalLayout;

    @Setter
    private SingleTeamSite parent;

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

        horizontalLayout = new HorizontalLayout(setAddUserButton(), setSaveButton());
        horizontalLayout.setWidthFull();
        horizontalLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);

        verticalLayout.add(teamNameField, teamMottoField, horizontalLayout, setGridContextMenu());

        CancelButton cancelButton = new CancelButton(this);
        cancelButton.addClickListener(e -> {
           parent.OnChangeReload();
        });

        add(verticalLayout);
        getFooter().add(setDeleteButton());
        getFooter().add(cancelButton);
    }

    private void beforeEdit() {
        String teamName = teamNameField.getValue();
        String teamMotto = teamMottoField.getValue();

        TeamDAO.updateInfo(team.getId(), teamName, teamMotto);
        team.setName(teamName);
        team.setMotto(teamMotto);
    }

    private Button setAddUserButton() {
        addUserButton.addClickListener(e -> {
            AddUserToTeamDialog addUserToTeamDialog = new AddUserToTeamDialog(team);
            addUserToTeamDialog.setParent(this);
            addUserToTeamDialog.open();
        });
        addUserButton.getStyle().set("margin-right", "auto");
        return addUserButton;
    }

    private Button setSaveButton() {
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);

        saveButton.getStyle().set("margin-left", "auto");

        saveButton.addClickListener(e -> {
            beforeEdit();
//            UI.getCurrent().getPage().reload();
            MyNotification.show("Saved", NotificationVariant.LUMO_SUCCESS, false);
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

//        Grid<TeamMember> grid = new Grid<>();
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
                TeamDAO.updateRole(team, teamMember.getUser(), event.getValue());
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
                DeleteConfirmDialog deleteConfirmDialog = new DeleteConfirmDialog();
                deleteConfirmDialog.setParent(this);
                deleteConfirmDialog.deleteUser(team, teamMember.getUser()).open();
            });

            return button;
        }).setFlexGrow(0);

        return grid;
    }

    @Override
    public void OnChangeReload() {
        verticalLayout.removeAll();
        grid.removeAllColumns();
        verticalLayout.add(teamNameField, teamMottoField, horizontalLayout, setGridContextMenu());
    }
}
