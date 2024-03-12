package com.example.application.components.elements;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;

public class TeamsElement extends Element{

    @Override
    public void layout() {
        cssSelector = "teamsElement";


//        setText("Zespół Promocji");
        H1 title = new H1("Zespół promocji");
        title.getStyle().set("text-align", "center");

        Div membersDiv = new Div();
        membersDiv.setClassName("teamsMembersDiv");

        membersDiv.add(new TeamsMemberElement());
        membersDiv.add(new TeamsMemberElement());
        membersDiv.add(new TeamsMemberElement());
        membersDiv.add(new TeamsMemberElement());
        membersDiv.add(new TeamsMemberElement());

        add(new TeamsUserRole());
        add(title);
        add(membersDiv);
    }

    @Override
    public void listenerAction() {

    }
}
