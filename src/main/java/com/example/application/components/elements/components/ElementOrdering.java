package com.example.application.components.elements.components;

import com.example.application.components.data.TeamRoles;

public class ElementOrdering {
    public static String orderByRole(TeamRoles teamRoles) {
        if (teamRoles.equals(TeamRoles.OWNER)){
            return "-3";
        } else if (teamRoles.equals(TeamRoles.ADMIN)){
            return "-2";
        } else if (teamRoles.equals(TeamRoles.MODERATOR)){
            return "-1";
        } else {
            return "0";
        }
    }
}
