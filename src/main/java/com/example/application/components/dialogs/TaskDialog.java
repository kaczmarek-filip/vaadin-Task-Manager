package com.example.application.components.dialogs;

import com.example.application.components.data.Task;
import com.example.application.components.data.User;
import com.example.application.components.elements.components.CallbackValues;
import com.example.application.components.elements.components.TaskDoneCallback;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

//TODO: fix - proponuje tylko usunięcie taska
/**
 *
 */
public class TaskDialog extends Dialog {
    private Task task;
    private TaskDoneCallback callback;

    public TaskDialog(Task task, TaskDoneCallback callback) {
        this.task = task;
        this.callback = callback;
        setHeaderTitle(task.getTitle());

        add(setLayout());
        setMinHeight("200");
        setMinWidth("500");
        getHeader().add(cancelButton());


        if (task.getHolders().contains(User.getLoggedInUser())){
            if (!task.isDone()) {
                getFooter().add(doneButton());
            } else {
                getFooter().add(unDoneButton());
                getFooter().add(deleteButton());
            }
        } else if (task.getCreator().equals(User.getLoggedInUser())) {
            getFooter().add(deleteButton());
        }

    }

    /**
     * @return {@link VerticalLayout}
     */
    private VerticalLayout setLayout() {
        VerticalLayout verticalLayout = new VerticalLayout();

        Span date = new Span(task.getFormattedDeadline());
        date.addClassName("taskDialogTime");

        Html html = new Html("<pre>" + task.getDescription() + "</pre>");
        html.getStyle().set("width", "-webkit-fill-available").set("padding", "2%");


        verticalLayout.getStyle().set("width", "28rem").set("max-width", "100%");
        verticalLayout.add(date, html);
        return verticalLayout;
    }

    private Button cancelButton() {
        Button button = new Button(new Icon("lumo", "cross"));
        button.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        button.addClickListener(e -> {
            close();
        });
        return button;
    }

    private Button doneButton() {
        Button button = new Button("Done");
        button.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        button.addClickListener(e -> {
            close();
            callback.onSave(CallbackValues.DONE);
        });
        return button;
    }

    private Button unDoneButton() {
        Button button = new Button("Undone");
        button.addThemeVariants(ButtonVariant.LUMO_ERROR);
        button.addClickListener(e -> {
            close();
            callback.onSave(CallbackValues.UNDONE);
        });
        return button;
    }

    private Button deleteButton() {
        Button button = new Button(VaadinIcon.TRASH.create());
        button.addThemeVariants(ButtonVariant.LUMO_ERROR);
        button.getStyle().set("margin-left", "auto");
        button.addClickListener(e -> {
            close();
            callback.onSave(CallbackValues.DELETE);
        });
        return button;
    }
}
