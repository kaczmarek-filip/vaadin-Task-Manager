package com.example.application.services.errors;

import com.example.application.components.elements.components.notifications.SimpleNotification;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.ErrorParameter;
import com.vaadin.flow.router.HasErrorParameter;
import com.vaadin.flow.router.NotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;


@Configuration
@Tag(Tag.DIV)
class Error404 extends Component implements HasErrorParameter<NotFoundException> {

    @Override
    public int setErrorParameter(BeforeEnterEvent beforeEnterEvent, ErrorParameter<NotFoundException> errorParameter) {

        beforeEnterEvent.rerouteTo("/");

        UI.getCurrent().access(() -> {
            SimpleNotification.show("Path not found (404 ERROR)", NotificationVariant.LUMO_ERROR, false);
        });

        return HttpServletResponse.SC_NOT_FOUND;
    }
}
