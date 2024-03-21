package com.example.application.components.elements;

import com.example.application.components.data.TeamRoles;
import com.example.application.components.data.User;

public class SingleTeamMemberElement extends Element{

    private User user;
    private TeamRoles teamRoles;

    public SingleTeamMemberElement(User user, TeamRoles teamRoles) {
        super("singleTeamMemberElement");
        this.user = user;
        this.teamRoles = teamRoles;

        layout();
    }

    @Override
    public void layout() {
        setText(user.getDisplayName() + " " + teamRoles);
    }

    @Override
    public void listenerAction() {

    }
}
