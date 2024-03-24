package com.example.application.components.contents;

import com.example.application.components.data.*;
import com.example.application.components.data.database.TaskDB;
import com.example.application.components.elements.SingleTeamMemberElement;
import com.example.application.components.elements.TaskBlockElement;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
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
        mainContent.add(motto(), horizontalLayout());

        return mainContent;
    }
    private HorizontalLayout horizontalLayout(){
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addClassName("taskSiteContent");
        horizontalLayout.setWidthFull();

        for (Task task : new TaskDB().getTeamTasks(team)) {
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
        for(int i = 0; i < 10; i++){
            for(Map.Entry<User, TeamRoles> entry : Team.getAllTeamUsers(team.getId()).entrySet()){
                membersList.add(new SingleTeamMemberElement(entry.getKey(), entry.getValue()));
            }
        }


        scroller.setContent(membersList);
        return scroller;
    }
}
