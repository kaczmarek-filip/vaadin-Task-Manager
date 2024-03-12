package com.example.application.components.elements;

import com.example.application.components.TeamRoles;

public class TeamsUserRole extends Element{

    @Override
    public void layout() {
        super.layout();
        cssSelector = "TeamsUserRole";

        setText(String.valueOf(TeamRoles.MEMBER.toString()));
    }

    @Override
    public void listenerAction() {

    }
}
