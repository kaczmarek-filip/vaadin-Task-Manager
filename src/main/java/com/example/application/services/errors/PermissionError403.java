package com.example.application.services.errors;

import com.example.application.components.elements.components.notifications.SimpleNotification;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.ErrorParameter;
import com.vaadin.flow.router.HasErrorParameter;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.context.annotation.Configuration;


@Configuration
@Tag(Tag.DIV)
public class PermissionError403 extends Component implements HasErrorParameter<BeanCreationException> {
    private static final Logger logger = LoggerFactory.getLogger(PermissionError403.class);
    @Override
    public int setErrorParameter(BeforeEnterEvent beforeEnterEvent, ErrorParameter<BeanCreationException> errorParameter) {

        beforeEnterEvent.rerouteTo("/");

        UI.getCurrent().access(() -> {
            SimpleNotification.show("You don't have access to this path (403 ERROR)", NotificationVariant.LUMO_ERROR, false);
        });
        logger.error(errorParameter.getException().getMessage());

        return HttpServletResponse.SC_FORBIDDEN;
    }
}
