package com.example.application.services.session;

import com.example.application.components.data.User;
import com.example.application.components.data.database.hibernate.UserDAO;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinSession;

public class SessionVaadin {

    public static void loginUser(User user) {
        if (VaadinSession.getCurrent() != null) {
            VaadinSession.getCurrent().lock();
            VaadinSession.getCurrent().setAttribute(SessionAttributes.LOGGED_IN_USER, user);
            VaadinSession.getCurrent().unlock();
            VaadinSession.getCurrent().setAttribute(SessionAttributes.SESSION_EXPIRED_FLAG, false);
            VaadinSession.getCurrent().setAttribute("ui", new UI());
        }
    }

    public static User getUser() {
        return (User) VaadinSession.getCurrent().getAttribute(SessionAttributes.LOGGED_IN_USER);
    }

    public static void logout() {

        UserDAO.setOnline(false);

        if ((Boolean) VaadinSession.getCurrent().getAttribute(SessionAttributes.SESSION_EXPIRED_FLAG)){
            VaadinSession.getCurrent().setAttribute(SessionAttributes.LOGGED_IN_USER, null);
        } else {
            VaadinSession.getCurrent().getSession().invalidate();
        }

    }
}
