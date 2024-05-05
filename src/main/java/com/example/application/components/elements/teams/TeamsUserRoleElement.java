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
        setColor();
    }

    @Override
    public void layout() {
        setText(teamRole.toString());
    }

    @Override
    public void listenerAction() {

    }
    private void setColor(){
        if(teamRole.equals(TeamRoles.OWNER)){
            addClassName("teams-owner");
        } else if (teamRole.equals(TeamRoles.ADMIN)) {
            addClassName("teams-admin");
        } else if (teamRole.equals(TeamRoles.MODERATOR)) {
            addClassName("teams-moderator");
        }
    }
}
