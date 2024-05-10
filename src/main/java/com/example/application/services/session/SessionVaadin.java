package com.example.application.services.session;

import com.example.application.components.data.User;
import com.vaadin.flow.server.VaadinSession;
import lombok.Getter;

import java.util.*;

public class SessionVaadin {

    public static void loginUser(User user) {
        if (VaadinSession.getCurrent() != null) {
            VaadinSession.getCurrent().lock();
            VaadinSession.getCurrent().setAttribute("loggedInUser", user);
            VaadinSession.getCurrent().unlock();
        }
    }

    public static User getUser() {
//        VaadinSession.getCurrent().lock();
        User user = (User) VaadinSession.getCurrent().getAttribute("loggedInUser");
//        VaadinSession.getCurrent().unlock();
        return user;
    }

    public static void logout() {
        VaadinSession.getCurrent().getSession().invalidate();
    }
}
