package com.example.application.views.main;

import com.example.application.components.data.database.DatabaseConnection;
//import com.example.application.components.SecurityService;
import com.example.application.components.elements.TaskElement;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

/**
 * Main view
 * @see Navigation
 */
@PageTitle("Main")
@Route(value = "")
public class MainView extends Navigation{

    private HorizontalLayout mainLayout = new HorizontalLayout();
    private VerticalLayout verticalLeftLayout = new VerticalLayout();
    private VerticalLayout verticalRightLayout = new VerticalLayout();
    private DatabaseConnection databaseConnection = new DatabaseConnection();
    public MainView() {
        super("Main");

        Paragraph toDo = new Paragraph("To Do");
        toDo.addClassName("toDoDoneLabel");
        verticalLeftLayout.add(toDo);


        for(int i = 0; i < 5; i++){
            TaskElement taskElement = new TaskElement();
            verticalLeftLayout.add(taskElement);
        }
        Paragraph done = new Paragraph("Done");
        done.addClassName("toDoDoneLabel");
        verticalLeftLayout.add(done);

        for(int i = 0; i < 5; i++){
            TaskElement taskElement = new TaskElement();
            verticalLeftLayout.add(taskElement);
        }


        mainLayout.add(verticalLeftLayout);
        mainLayout.add(verticalRightLayout);
        setContent(mainLayout);
    }
}
