package com.example.application.views.main;

import com.example.application.views.main.contents.MainViewContent;
import com.example.application.services.session.SessionVaadin;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.UI;
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
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);

        if (SessionVaadin.getUser() == null) {
            UI.getCurrent().navigate(LoginView.class);
        } else {
            setContent(new MainViewContent());
        }
    }
}
