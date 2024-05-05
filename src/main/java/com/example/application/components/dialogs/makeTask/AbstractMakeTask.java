package com.example.application.components.dialogs.makeTask;

import com.example.application.components.data.Task;
import com.example.application.components.elements.components.CancelButton;
import com.example.application.components.elements.components.MyNotification;
import com.example.application.components.elements.components.TextAreaCounter;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;

import java.time.LocalDate;

public abstract class AbstractMakeTask extends Dialog {

    private FormLayout formLayout = new FormLayout();

    private TextField titleTextField;
    private TextAreaCounter descriptionTextArea;
    private DatePicker deadlineDatePicker;

    protected String title;
    protected String description;
    protected LocalDate deadline;

    public AbstractMakeTask() {
        setHeaderTitle("Make Task");
        setCloseOnOutsideClick(false);
        setCloseOnEsc(false);

        formLayout.add(title(), description(), deadline());

        add(formLayout);

        getFooter().add(new CancelButton(this), createButton());
    }

    private TextField title() {
        titleTextField = new TextField("Title");
        titleTextField.setRequired(true);
        return titleTextField;
    }

    private TextArea description() {
        descriptionTextArea = new TextAreaCounter("Description");
        descriptionTextArea.setCounter(Task.descriptionMaxLength);
        return descriptionTextArea;
    }

    private DatePicker deadline() {
        deadlineDatePicker = new DatePicker("Deadline");
        deadlineDatePicker.setMin(LocalDate.now());
        deadlineDatePicker.setRequired(true);
        return deadlineDatePicker;
    }

    private Button createButton() {
        Button createButton = new Button("Create");
        createButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);

        createButton.addClickListener(e -> {
            beforeCreate();
        });
        return createButton;
    }

    private void beforeCreate() {
        title = titleTextField.getValue();
        description = descriptionTextArea.getValue();
        deadline = deadlineDatePicker.getValue();

        if (title.isEmpty()) {
            MyNotification.show("The title field must not be empty", NotificationVariant.LUMO_ERROR, false);
        } else if (deadline == null) {
            MyNotification.show("The deadline field must not be empty", NotificationVariant.LUMO_ERROR, false);
        } else {
            onCreate();
        }
    }

    abstract void onCreate();
}
