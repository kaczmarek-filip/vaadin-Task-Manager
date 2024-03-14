package com.example.application.components.elements;

import com.example.application.components.TeamMembers;
import com.example.application.components.TeamRoles;

public class TeamsMemberElement extends Element {
    private final TeamMembers teamMembers;

    public TeamsMemberElement(TeamMembers teamMembers) {
        super("TeamsMemberElement");
        this.teamMembers = teamMembers;
        layout();
        listenerAction();
    }

    @Override
    public void layout() {
        if(teamMembers.getRole() == TeamRoles.OWNER){
            addClassName("TeamsMemberOwner");
            getElement().getStyle().set("order", "-1");
        }
        setText(teamMembers.getUser().getDisplayName());
    }

    @Override
    public void listenerAction() {

    }
}
