package com.example.application.components.elements.teams;

import com.example.application.components.data.TeamMember;
import com.example.application.components.data.TeamRoles;
import com.example.application.components.data.User;
import com.example.application.components.elements.Element;

public class TeamsMemberElement extends Element {
    private User teamUser;
    private TeamRoles teamRole;

    public TeamsMemberElement(TeamMember teamMember) {
        super("TeamsMemberElement");
        this.teamUser = teamMember.getUser();
        this.teamRole = teamMember.getRole();
        layout();
        listenerAction();
    }

    @Override
    public void layout() {
        if(teamRole == TeamRoles.OWNER){
            addClassName("TeamsMemberOwner");
            getElement().getStyle().set("order", "-1");
        }
        setText(teamUser.getDisplayName());
    }

    @Override
    public void listenerAction() {

    }
}
