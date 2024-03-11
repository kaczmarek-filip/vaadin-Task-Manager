package com.example.application.views.main;

import com.example.application.components.LoginDialog;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.theme.lumo.LumoUtility;


public class Navigation extends AppLayout {

//    private ClassLoader classLoader = getClass().getClassLoader();
//    private URL avatarUrl = classLoader.getResource("Icons/avatar.png");

    public Navigation(String siteName) {
        DrawerToggle toggle = new DrawerToggle();

        H1 title = new H1(siteName);
        title.getStyle().set("font-size", "var(--lumo-font-size-l)")
                .set("margin", "0");
        Button avatar = new Button();
        avatar.setIcon(VaadinIcon.MALE.create());
        avatar.addClassName("navAvatar");
        avatar.addClickListener(e -> {
           new LoginDialog().open();
        });


        SideNav nav = new SideNav();
        SideNavItem mainSiteLink = new SideNavItem("Main", MainView.class, VaadinIcon.MENU.create());
//        SideNavItem dashboardLink = new SideNavItem("Dashboard", DashboardSite.class, VaadinIcon.DASHBOARD.create());
        SideNavItem messengerLink = new SideNavItem("Messages", MessengerSite.class, VaadinIcon.COMMENT_ELLIPSIS.create());
        SideNavItem teamLink = new SideNavItem("Team", TeamsSite.class, VaadinIcon.MALE.create());
        SideNavItem userLink = new SideNavItem("User", UserSite.class, VaadinIcon.USER_CARD.create());

        nav.addItem(mainSiteLink, messengerLink, teamLink, userLink);

        Scroller scroller = new Scroller(nav);
        scroller.setClassName(LumoUtility.Padding.SMALL);

        addToDrawer(scroller);
        addToNavbar(toggle, title, avatar);

        setPrimarySection(Section.DRAWER);

//        Button button = new Button("Siemandero");
//        setContent(button);
    }
}
