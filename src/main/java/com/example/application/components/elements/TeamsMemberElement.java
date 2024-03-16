package com.example.application.components.elements;

import com.example.application.components.data.TeamRoles;
import com.example.application.components.data.User;

import java.util.HashMap;
import java.util.Map;

public class TeamsMemberElement extends Element {
//    private final TeamMembers teamMembers;
    private Map<User, TeamRoles> usersInTeam = new HashMap<>();
    private User teamUser;
    private TeamRoles teamRole;

    public TeamsMemberElement(User teamUser, TeamRoles teamRole) {
        super("TeamsMemberElement");
//        this.teamMembers = teamMembers;
//        this.usersInTeam = usersInTeam;
        this.teamUser = teamUser;
        this.teamRole = teamRole;
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
