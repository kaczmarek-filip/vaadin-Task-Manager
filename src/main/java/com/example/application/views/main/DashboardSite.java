package com.example.application.views.main;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.router.Route;


/**
 * Dashboard site
 * @see Navigation
 */
@Route("dashboard")
public class DashboardSite extends Navigation {

    /**
     * Constructor
     */
    public DashboardSite() {
        super("Dashboard");
    }
}
