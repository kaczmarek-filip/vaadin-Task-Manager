package com.example.application.components.contents;

import com.example.application.components.data.*;
import com.example.application.components.data.database.sql.TaskDB;
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

        for (Task task : new TaskDB().getUserTasks(user, team)) {
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

        for(Map.Entry<User, TeamRoles> entry : Team.getAllTeamUsers(team.getId()).entrySet()){
            membersList.add(new SingleTeamMemberElement(entry.getKey(), entry.getValue()));
        }


        scroller.setContent(membersList);
        return scroller;
    }
    private Scroller memberTasks(){
        VerticalLayout verticalLayout = new VerticalLayout();

        for(Map.Entry<User, TeamRoles> entry : Team.getAllTeamUsers(team.getId()).entrySet()){
            if (!new TaskDB().getUserTasks(entry.getKey(), team).isEmpty()){ // not display users without tasks
                H1 h1 = new H1(entry.getKey().getDisplayName());
                if (entry.getKey().equals(User.getLoggedInUser())) h1.getStyle().set("order", "-1");
                verticalLayout.add(h1);
                verticalLayout.add(userTasks(entry.getKey()));
            }
        }

        Scroller scroller = new Scroller();
        scroller.setScrollDirection(Scroller.ScrollDirection.VERTICAL);
        scroller.setWidthFull();
        scroller.setContent(verticalLayout);
        return scroller;
    }
}
