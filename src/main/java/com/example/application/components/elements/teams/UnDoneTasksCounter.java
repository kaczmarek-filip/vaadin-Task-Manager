package com.example.application.components.elements.teams;

import com.example.application.components.data.Task;
import com.example.application.components.data.TaskHolder;
import com.example.application.components.data.Team;
import com.example.application.components.data.User;
import com.example.application.components.data.database.hibernate.TaskHolderDAO;
import com.example.application.components.elements.Element;
import com.example.application.components.elements.components.colors.CssColors;

import java.util.List;

public class UnDoneTasksCounter extends Element {
    private final Team team;
    private int counter = 0;

    public UnDoneTasksCounter(Team team) {
        super("teamCardTaskCounter");

        this.team = team;
        layout();
    }

    @Override
    public void layout() {
        List<Task> tasks = TaskHolderDAO.getTasks(User.getLoggedInUser(), team);
        for (Task task : tasks) {
            TaskHolder userInHolders = task.isUserInHolders(User.getLoggedInUser());
            if (!userInHolders.isPartDone()) {
                counter++;
            }
        }
        setText(String.valueOf(counter));
        setColor();
    }

    private void setColor() {
        if (counter >= 5) {
            getStyle().setBackgroundColor(CssColors.RED);
        } else if (counter > 0) {
            getStyle().setBackgroundColor(CssColors.BLUE);
            getStyle().setColor(CssColors.WHITE);
        } else {
            getStyle().set("display", "none");
        }
    }

    @Override
    public void listenerAction() {
    }
}
