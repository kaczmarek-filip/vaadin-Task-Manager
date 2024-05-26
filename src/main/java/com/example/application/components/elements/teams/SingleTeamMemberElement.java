package com.example.application.components.elements.teams;

import com.example.application.components.data.TeamMember;
import com.example.application.components.data.TeamRoles;
import com.example.application.components.data.User;
import com.example.application.components.elements.Element;
import com.example.application.components.elements.components.ActiveDot;
import com.example.application.components.elements.components.ElementOrdering;
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.html.Div;

public class SingleTeamMemberElement extends Element {

    private User user;
    private TeamRoles teamRoles;

    public SingleTeamMemberElement(TeamMember teamMember) {
        super("singleTeamMemberElement");
        this.user = teamMember.getUser();
        this.teamRoles = teamMember.getRole();

        layout();
//        listenerAction();
    }

    @Override
    public void layout() {
//        setText(user.getDisplayName() + " " + teamRoles);
        setText(user.getDisplayName());
        add(new TeamsUserRoleElement(teamRoles));

        getStyle().set("order", ElementOrdering.orderByRole(teamRoles));

        Div activeDot = ActiveDot.get(user);
        activeDot.getStyle().set("right", "0").set("left", "-10px");
        add(activeDot);
    }

    @Override
    public void listenerAction() {
        ContextMenu contextMenu = new ContextMenu();
        contextMenu.setTarget(this);
        contextMenu.setOpenOnClick(true);

        contextMenu.addItem("Delete", menuItemClickEvent -> {

        });
    }
}
