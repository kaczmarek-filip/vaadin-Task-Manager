package com.example.application.components.contents;

import com.example.application.components.data.Task;
import com.example.application.components.data.Team;
import com.example.application.components.data.TeamMember;
import com.example.application.components.data.User;
import com.example.application.components.data.database.hibernate.TaskHolderDAO;
import com.example.application.components.data.database.hibernate.TeamDAO;
import com.example.application.components.elements.tasks.TeamTaskBlockElement;
import com.example.application.components.elements.teams.SingleTeamMemberElement;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class SingleTeamSiteContent extends HorizontalLayout {
    private Team team;

    public SingleTeamSiteContent(Team team) {
        this.team = team;
        setWidth("100%");
        setHeight("100%");
        add(mainContent(), membersList());
    }

    private VerticalLayout mainContent() {
        VerticalLayout mainContent = new VerticalLayout();
        mainContent.setWidth("80%");
        mainContent.add(motto(), memberTasks());

        return mainContent;
    }

    private HorizontalLayout userTasks(User user) {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addClassName("taskSiteContent");
        horizontalLayout.setWidthFull();

        if (user.equals(User.getLoggedInUser())) horizontalLayout.getStyle().set("order", "-1");


        for (Task task : TaskHolderDAO.getTasks(user, team)) {
            horizontalLayout.add(new TeamTaskBlockElement(task, user));
        }

        return horizontalLayout;
    }

    private H1 motto() {
        H1 motto = new H1(team.getMotto());
        motto.setClassName("singleTeamMotto");

        return motto;
    }

    private Scroller membersList() {
        VerticalLayout membersList = new VerticalLayout();
        Scroller scroller = new Scroller();
        scroller.setScrollDirection(Scroller.ScrollDirection.VERTICAL);
        scroller.setWidth("20%");


        for (TeamMember teamMember : TeamDAO.findUsersInTeam(team.getId())) {
            membersList.add(new SingleTeamMemberElement(teamMember));
        }


        scroller.setContent(membersList);
        return scroller;
    }

    private Scroller memberTasks() {
        VerticalLayout verticalLayout = new VerticalLayout();

        for (TeamMember teamMember : TeamDAO.findUsersInTeam(team.getId())) {
            if (!TaskHolderDAO.getTasks(teamMember.getUser(), team).isEmpty()) { // not display users without tasks
                H1 h1 = new H1(teamMember.getUser().getDisplayName());
                if (teamMember.getUser().equals(User.getLoggedInUser())) h1.getStyle().set("order", "-1");
                verticalLayout.add(h1);
                verticalLayout.add(userTasks(teamMember.getUser()));
            }
        }

        Scroller scroller = new Scroller();
        scroller.setScrollDirection(Scroller.ScrollDirection.VERTICAL);
        scroller.setWidthFull();
        scroller.setContent(verticalLayout);
        return scroller;
    }
}
