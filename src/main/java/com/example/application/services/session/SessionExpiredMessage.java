package com.example.application.services.session;

import com.vaadin.flow.server.*;
import org.springframework.stereotype.Component;

@Component
public class SessionExpiredMessage implements VaadinServiceInitListener {
    @Override
    public void serviceInit(ServiceInitEvent serviceInitEvent) {
        serviceInitEvent.getSource().setSystemMessagesProvider(new SystemMessagesProvider() {
            @Override
            public SystemMessages getSystemMessages(SystemMessagesInfo systemMessagesInfo) {
                CustomizedSystemMessages messages = new CustomizedSystemMessages();
                messages.setSessionExpiredCaption("Session expired");
                messages.setSessionExpiredMessage("Take note of any unsaved data, and click here or press ESC key to continue.");
                messages.setSessionExpiredURL("/login");
                messages.setSessionExpiredNotificationEnabled(true);
                return messages;
            }
        });
    }
}
