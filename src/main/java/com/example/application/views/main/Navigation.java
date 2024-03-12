package com.example.application.views.main;

import com.example.application.components.User;
import com.example.application.components.dialogs.LoginDialog;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.theme.lumo.LumoUtility;


/**
 * Navigation
 * @see AppLayout
 */
public class Navigation extends AppLayout {
    private Button avatar = new Button();

    /**
     * Default constructor
     * @param siteName Name displayed in upper left corner
     */
    public Navigation(String siteName) {
        DrawerToggle toggle = new DrawerToggle();

        H1 title = new H1(siteName);
        title.getStyle().set("font-size", "var(--lumo-font-size-l)")
                .set("margin", "0");


        if(User.getLoggedInUser().getDisplayName() == null) avatar.setText("Sign in");
        else avatar.setText(User.getLoggedInUser().getDisplayName());

        avatar.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        avatar.addClassName("navAvatar");
        avatar.addClickListener(e -> {
            new LoginDialog().open();
        });


        SideNav nav = new SideNav();
        SideNavItem mainSiteLink = new SideNavItem("Main", MainView.class, VaadinIcon.MENU.create());
        SideNavItem taskLink = new SideNavItem("Tasks", TasksSite.class, VaadinIcon.TASKS.create());
//        SideNavItem dashboardLink = new SideNavItem("Dashboard", DashboardSite.class, VaadinIcon.DASHBOARD.create());
        SideNavItem messengerLink = new SideNavItem("Messages", MessengerSite.class, VaadinIcon.COMMENT_ELLIPSIS.create());
        SideNavItem teamLink = new SideNavItem("Team", TeamsSite.class, VaadinIcon.MALE.create());
        SideNavItem userLink = new SideNavItem("User", UserSite.class, VaadinIcon.USER_CARD.create());

        nav.addItem(mainSiteLink,taskLink, messengerLink, teamLink, userLink);

        Scroller scroller = new Scroller(nav);
        scroller.setClassName(LumoUtility.Padding.SMALL);

        addToDrawer(scroller);
        addToNavbar(toggle, title, avatar);

        setPrimarySection(Section.DRAWER);

    }
}
