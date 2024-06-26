package com.example.application.views.main.contents;

import com.example.application.components.data.Task;
import com.example.application.components.data.User;
import com.example.application.components.data.database.hibernate.LoggerDAO;
import com.example.application.components.data.database.hibernate.TaskDAO;
import com.example.application.components.elements.components.notifications.SimpleNotification;
import com.example.application.components.elements.tasks.OwnTaskBlockElement;
import com.example.application.services.session.AllSessions;
import com.example.application.services.session.SessionAttributes;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.VaadinSession;

import java.util.ArrayList;

public class MainViewContent extends HorizontalLayout {
    public MainViewContent() {
        setWidthFull();
        setHeightFull();
        add(leftSide(), rightSide());
    }

    private HorizontalLayout getTasks(ArrayList<Task> taskArrayList) {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addClassName("taskSiteContent");
        horizontalLayout.setWidthFull();

        int i = 0;
        int limit = 10;

        for (Task task : taskArrayList) {
            if (i >= limit) {
                break;
            }
            horizontalLayout.add(new OwnTaskBlockElement(task));
            i++;
        }

        return horizontalLayout;
    }

    private VerticalLayout leftSide() {
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setHeightFull();
        verticalLayout.setWidth(50, Unit.PERCENTAGE);

        ArrayList<Task> doneTasks = new ArrayList<>();
        ArrayList<Task> notDoneTasks = new ArrayList<>();

        for (Task task : TaskDAO.getTasks(User.getLoggedInUser())) {
            if (task.isDone()) {
                doneTasks.add(task);
            } else {
                notDoneTasks.add(task);
            }
        }

        Paragraph myTaskLabel = new Paragraph("To Do");
        myTaskLabel.addClassName("toDoDoneLabel");
        verticalLayout.add(myTaskLabel, getTasks(notDoneTasks));

        Paragraph teamTasksLabel = new Paragraph("Done");
        teamTasksLabel.addClassName("toDoDoneLabel");
        verticalLayout.add(teamTasksLabel, getTasks(doneTasks));

        return verticalLayout;
    }

    private VerticalLayout rightSide() {
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setHeightFull();
        verticalLayout.setWidth(50, Unit.PERCENTAGE);
        verticalLayout.add(new Text("Nothing"));
        Button button = new Button("Hibernate");
        button.addClickListener(e -> {
//            Notification.show(User.getLoggedInUser().getDisplayName());
//            new OneTimeMessageEncryption().startupEncrypt();
            for (VaadinSession vaadinSession : AllSessions.getVaadinSessionList()){
                vaadinSession.lock();
                User user = (User) vaadinSession.getAttribute(SessionAttributes.LOGGED_IN_USER);
                SimpleNotification.test(user.getDisplayName());
                vaadinSession.unlock();
            }
            LoggerDAO.log("Test");
        });
        verticalLayout.add(button);
        return verticalLayout;
    }
}
