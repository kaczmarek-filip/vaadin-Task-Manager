package com.example.application.components.contents;

import com.example.application.components.data.Team;
import com.example.application.components.data.TeamRoles;
import com.example.application.components.data.User;
import com.example.application.components.elements.SingleTeamMemberElement;
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
//        mainContent.add("ciekawe czy się scrolluje");
        mainContent.add(motto());

        return mainContent;
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
