package com.example.application.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.theme.lumo.LumoUtility;


/**
 * More informations about {@link TaskElement}
 */
public class TaskDialog extends Dialog {
    private Button cancelButton;
    private String informations = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum";
    public TaskDialog() {
        setHeaderTitle("Betlejemskie Światło Pokoju");


        cancelButton = new Button(new Icon("lumo", "cross"));
        cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        cancelButton.addClickListener(e -> {
            close();
        });
        add(setLayout());
        setMinHeight("200");
        setMinWidth("500");
        getHeader().add(cancelButton);
    }

    /**
     * @return {@link VerticalLayout}
     */
    private VerticalLayout setLayout(){

        Span date = new Span("21.12.2002");
        date.getElement().getThemeList().add("badge");
        date.addClassName("taskDialogTime");

        Span time = new Span("18:00");
        time.getElement().getThemeList().add("badge error");
        time.addClassName("taskDialogTime");

        HorizontalLayout horizontalLayout = new HorizontalLayout(time, date);

        Paragraph paragraph = new Paragraph(informations);

        horizontalLayout.addClassName(LumoUtility.Padding.MEDIUM);
        VerticalLayout verticalLayout = new VerticalLayout(horizontalLayout, paragraph);

        verticalLayout.setPadding(false);
        verticalLayout.setSpacing(false);

        verticalLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        verticalLayout.getStyle().set("width", "28rem").set("max-width", "100%");

        return verticalLayout;
    }
}
