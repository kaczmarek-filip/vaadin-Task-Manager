package com.example.application.components.elements;

import com.example.application.components.TeamRoles;

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
//        super.layout();

        setText(teamRole.toString());
//        setText("MEMBER");
    }

    @Override
    public void listenerAction() {

    }
}
