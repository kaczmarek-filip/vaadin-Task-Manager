package com.example.application.views.main;

import com.example.application.components.User;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.theme.lumo.LumoUtility;


/**
 * Navigation
 * @see AppLayout
 */
public class Navigation extends AppLayout  implements BeforeEnterObserver {


    /**
     * Default constructor
     * @param siteName Name displayed in upper left corner
     */
    public Navigation(String siteName) {
        DrawerToggle toggle = new DrawerToggle();

        H1 title = new H1(siteName);
        title.getStyle().set("font-size", "var(--lumo-font-size-l)")
                .set("margin", "0");



        Scroller scroller = new Scroller(setNavigation());
        scroller.setClassName(LumoUtility.Padding.SMALL);

        addToDrawer(scroller);
        addToNavbar(toggle, title, logOutButton());

        setPrimarySection(Section.DRAWER);
    }
    private SideNav setNavigation(){
        SideNav nav = new SideNav();
        SideNavItem mainSiteLink = new SideNavItem("Main", MainView.class, VaadinIcon.MENU.create());
        SideNavItem taskLink = new SideNavItem("Tasks", TasksSite.class, VaadinIcon.TASKS.create());
//        SideNavItem dashboardLink = new SideNavItem("Dashboard", DashboardSite.class, VaadinIcon.DASHBOARD.create());
        SideNavItem messengerLink = new SideNavItem("Messages", MessengerSite.class, VaadinIcon.COMMENT_ELLIPSIS.create());
        SideNavItem teamLink = new SideNavItem("Team", TeamsSite.class, VaadinIcon.MALE.create());
        SideNavItem userLink = new SideNavItem("User", UserSite.class, VaadinIcon.USER_CARD.create());

        nav.addItem(mainSiteLink,taskLink, messengerLink, teamLink, userLink);

        return nav;
    }

    private Button logOutButton(){
        Button logOut = new Button();

        logOut.addClassName("signOutButton");
        logOut.setText("Log out");
        logOut.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        logOut.addClickListener(e -> {

            User.logOut();
            UI.getCurrent().navigate("/login");

            Notification notification = new Notification("Logged out", 5000, Notification.Position.BOTTOM_CENTER);
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            notification.open();
        });
        return logOut;
    }

    /**
     * @param beforeEnterEvent Checking is user already logged in
     */
    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if(User.getLoggedInUser().getDisplayName() == null) {
            beforeEnterEvent.rerouteTo(LoginView.class);
        }
    }

}
