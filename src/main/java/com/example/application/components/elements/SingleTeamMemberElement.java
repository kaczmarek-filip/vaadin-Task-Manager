package com.example.application.components.elements;

import com.example.application.components.data.TeamMember;
import com.example.application.components.data.TeamRoles;
import com.example.application.components.data.User;

public class SingleTeamMemberElement extends Element{

    private User user;
    private TeamRoles teamRoles;

    public SingleTeamMemberElement(TeamMember teamMember) {
        super("singleTeamMemberElement");
        this.user = teamMember.getUser();
        this.teamRoles = teamMember.getRole();

        layout();
    }

    @Override
    public void layout() {
        setText(user.getDisplayName() + " " + teamRoles);
        add(ActiveDot.get(user));
    }

    @Override
    public void listenerAction() {

    }
}
