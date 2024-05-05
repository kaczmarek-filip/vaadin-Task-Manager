package com.example.application.components.dialogs.task;

import com.example.application.components.data.Task;
import com.example.application.components.elements.components.CancelButton;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public abstract class AbstractTaskDialog extends Dialog {

    protected Task task;

    public AbstractTaskDialog(Task task) {
        this.task = task;
        setHeaderTitle(task.getTitle());

        add(setLayout());
        setMinHeight("200");
        setMinWidth("500");
        getHeader().add(new CancelButton(this).crossButton());
    }

    private VerticalLayout setLayout() {
        VerticalLayout verticalLayout = new VerticalLayout();

        Span date = new Span(task.getFormattedDeadline());
        date.addClassName("taskDialogTime");

        Html html = new Html("<pre>" + task.getDescription() + "</pre>");
        html.getStyle().set("width", "-webkit-fill-available").set("padding", "2%").set("text-wrap", "wrap");


        verticalLayout.getStyle().set("width", "28rem").set("max-width", "100%").set("margin", "auto");
        verticalLayout.add(date, html);
        return verticalLayout;
    }

    private Button doneButton() {
        Button button = new Button("Done");
        button.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        button.addClickListener(e -> {
            close();
            onDoneButton();
        });
        return button;
    }

    protected abstract void onDoneButton();

    private Button unDoneButton() {
        Button button = new Button("Undone");
        button.addThemeVariants(ButtonVariant.LUMO_ERROR);
        button.addClickListener(e -> {
            close();
            onUnDoneButton();
        });
        return button;
    }

    protected abstract void onUnDoneButton();

    private Button deleteButton() {
        Button button = new Button(VaadinIcon.TRASH.create());
        button.addThemeVariants(ButtonVariant.LUMO_ERROR);
        button.getStyle().set("margin-left", "auto");
        button.addClickListener(e -> {
            close();
            onDeleteButton();
        });
        return button;
    }

    protected abstract void onDeleteButton();

    protected void canEdit(boolean isDone) {
        if (!isDone) {
            getFooter().add(doneButton());
        } else {
            getFooter().add(unDoneButton());
            getFooter().add(deleteButton());
        }
    }
}
