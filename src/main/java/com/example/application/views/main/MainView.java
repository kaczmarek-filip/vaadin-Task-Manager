package com.example.application.views.main;

import com.example.application.components.contents.MainViewContent;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

/**
 * Main view
 * @see Navigation
 */
@PageTitle("Main")
@Route(value = "")
public class MainView extends Navigation{

    public MainView() {
        super("Main");

        setContent(new MainViewContent());
    }
}
