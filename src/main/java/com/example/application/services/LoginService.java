package com.example.application.services;

import com.example.application.components.data.User;
import com.example.application.components.data.database.hibernate.LoggerDAO;
import com.example.application.components.data.database.hibernate.UserDAO;
import com.example.application.components.elements.components.notifications.SimpleNotification;
import com.example.application.services.session.SessionAttributes;
import com.example.application.services.session.SessionVaadin;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.stereotype.Component;

@Component
public class LoginService {
    public static boolean login(String email, String password) {

        User loggedInUser = UserDAO.loginUser(email, password);

        if (loggedInUser != null) {
            SimpleNotification.show("Logged in", NotificationVariant.LUMO_SUCCESS, false);

            SessionVaadin.loginUser(UserDAO.getUserById(loggedInUser.getId()));

            UserDAO.setOnline(true);
            LoggerDAO.log("Logged in");

            return true;

        } else {
            SimpleNotification.show("Incorrect login data", NotificationVariant.LUMO_ERROR, false);
            return false;
        }
    }

    public static void logout() {
        LoggerDAO.log("Logged out");
        VaadinSession.getCurrent().setAttribute(SessionAttributes.SESSION_EXPIRED_FLAG, true);
        User.logOut();
        UI.getCurrent().navigate("/login");

        SimpleNotification.show("Logged out", NotificationVariant.LUMO_ERROR, false);
    }
}
