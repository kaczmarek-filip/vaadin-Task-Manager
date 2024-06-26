package com.example.application.components.elements.components;

import com.example.application.components.data.User;
import com.example.application.components.elements.components.colors.ChatActiveColors;
import com.example.application.services.session.AllSessions;
import com.vaadin.flow.component.html.Div;

public class ActiveDot {
    public static Div get(User user){
        Div div = new Div();
        div.setClassName("active-dot");

        if(AllSessions.getUserSession(user) != null){
            div.getStyle().set("background-color", ChatActiveColors.online);
        } else {
            div.getStyle().set("background-color", ChatActiveColors.offline);
        }

        return div;
    }
}
