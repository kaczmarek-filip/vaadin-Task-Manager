package com.example.application.services.session;

import com.example.application.components.data.User;
import com.vaadin.flow.server.VaadinSession;

import java.util.ArrayList;
import java.util.List;

import static com.example.application.services.session.SessionAttributes.LOGGED_IN_USER;

public class AllSessions {
    static List<VaadinSession> vaadinSessionList = new ArrayList<>();

    public static List<VaadinSession> getVaadinSessionList() {
        List<VaadinSession> sessionList = new ArrayList<>();

        for (VaadinSession session : vaadinSessionList) {
            session.lock();
            if (session.getAttribute(LOGGED_IN_USER) != null) {
                sessionList.add(session);
            }
            session.unlock();
        }

        return sessionList;
    }

    public static VaadinSession getUserSession(User user) {
        VaadinSession resultSession = null;

        for (VaadinSession session : vaadinSessionList) {
            session.lock();
            if (session.getAttribute(LOGGED_IN_USER) != null) {
                if (session.getAttribute(LOGGED_IN_USER).equals(user)) {
                    resultSession = session;
                }
            }
            session.unlock();
        }
        return resultSession;
    }
}
