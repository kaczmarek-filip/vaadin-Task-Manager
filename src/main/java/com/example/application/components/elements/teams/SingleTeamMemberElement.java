package com.example.application.components.elements.teams;

import com.example.application.components.data.TeamMember;
import com.example.application.components.data.TeamRoles;
import com.example.application.components.data.User;
import com.example.application.components.elements.ActiveDot;
import com.example.application.components.elements.Element;

public class SingleTeamMemberElement extends Element {

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
