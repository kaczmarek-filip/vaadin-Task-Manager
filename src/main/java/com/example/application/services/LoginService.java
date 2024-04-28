package com.example.application.services;

import com.example.application.components.data.User;
import com.example.application.components.data.database.hibernate.UserDAO;
import com.example.application.components.elements.components.MyNotification;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.NotificationVariant;
import org.springframework.stereotype.Component;

@Component
public class LoginService {
    public static boolean login(String email, String password) {

        User loggedInUser = UserDAO.loginUser(email, password);

        if (loggedInUser != null) {
            MyNotification.show("Logged in", NotificationVariant.LUMO_SUCCESS, false);

            User.getInstance().setId(loggedInUser.getId());
            User.getInstance().setDisplayName(loggedInUser.getDisplayName());
            User.getInstance().setEmail(loggedInUser.getEmail());

            UserDAO.setOnline(true);
            return true;

        } else {
            MyNotification.show("Incorrect login data", NotificationVariant.LUMO_ERROR, false);
            return false;
        }
    }

    public static void logout() {
        UserDAO.setOnline(false);
        User.logOut();
        UI.getCurrent().navigate("/login");

        MyNotification.show("Logged out", NotificationVariant.LUMO_ERROR, false);
    }
}
