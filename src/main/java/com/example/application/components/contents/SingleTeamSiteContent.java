package com.example.application.components.contents;

import com.example.application.components.data.*;
import com.example.application.components.data.database.HibernateTask;
import com.example.application.components.data.database.HibernateTeam;
import com.example.application.components.data.database.sql.SQLTaskDB;
import com.example.application.components.elements.SingleTeamMemberElement;
import com.example.application.components.elements.TaskBlockElement;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.Map;

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
    private HorizontalLayout userTasks(User user){
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addClassName("taskSiteContent");
        horizontalLayout.setWidthFull();

        if (user.equals(User.getLoggedInUser())) horizontalLayout.getStyle().set("order", "-1");


        for (Task task : HibernateTask.getTasks(user, team)) {
            horizontalLayout.add(new TaskBlockElement(task));
        }

        return horizontalLayout;
    }
    private H1 motto(){
        H1 motto = new H1(team.getMotto());
        motto.setClassName("singleTeamMotto");

        return motto;
    }

    private Scroller membersList() {
        VerticalLayout membersList = new VerticalLayout();
        Scroller scroller = new Scroller();
        scroller.setScrollDirection(Scroller.ScrollDirection.VERTICAL);
        scroller.setWidth("20%");


        for(TeamMember teamMember : HibernateTeam.findUsersInTeam(team.getId())){
            membersList.add(new SingleTeamMemberElement(teamMember));
        }


        scroller.setContent(membersList);
        return scroller;
    }
    private Scroller memberTasks(){
        VerticalLayout verticalLayout = new VerticalLayout();

        for(TeamMember teamMember : HibernateTeam.findUsersInTeam(team.getId())){
            if (!HibernateTask.getTasks(teamMember.getUser(), team).isEmpty()){ // not display users without tasks
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
