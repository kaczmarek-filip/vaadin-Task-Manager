package com.example.application.components.dialogs.makeTask;

import com.example.application.components.data.Task;
import com.example.application.components.data.Team;
import com.example.application.components.data.TeamMember;
import com.example.application.components.data.User;
import com.example.application.components.data.database.hibernate.TaskDAO;
import com.example.application.components.data.database.hibernate.TaskHolderDAO;
import com.example.application.components.elements.components.notifications.SimpleNotification;
import com.example.application.views.main.SingleTeamSite;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.NotificationVariant;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MakeTeamTaskDialog extends AbstractMakeTask {
    private Team team;
    private Grid<User> userGrid;
    @Setter
    private SingleTeamSite parent;

    public MakeTeamTaskDialog(Team team) {
        this.team = team;

        add(setUserGrid());
    }

    @Override
    void onCreate() {
        Set<User> selectedUsers = userGrid.getSelectedItems();

        if (!selectedUsers.isEmpty()) {
            Task task = new Task(title, description, deadline);
            task.setTeam(team);

            TaskDAO.createTask(task);
            TaskHolderDAO.createHolders(new ArrayList<>(selectedUsers), task);

            SimpleNotification.show("Created successfully", NotificationVariant.LUMO_SUCCESS, false);
            parent.OnChangeReload();
            close();
        } else {
            SimpleNotification.show("You must choose at least one person", NotificationVariant.LUMO_ERROR, false);
        }
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
}
