package com.example.application.components.elements.teams;

import com.example.application.components.data.TeamRoles;
import com.example.application.components.elements.Element;

public class TeamsUserRoleElement extends Element {
    private TeamRoles teamRole;

    public TeamsUserRoleElement(TeamRoles teamRole) {
        super("TeamsUserRole");
        this.teamRole = teamRole;
        layout();
        listenerAction();
    }

    @Override
    public void layout() {
        setText(teamRole.toString());
    }

    @Override
    public void listenerAction() {

    }
}
