package com.example.application.components.elements.messenger;

import com.example.application.components.data.Chat;
import com.example.application.components.data.User;
import com.example.application.components.data.database.hibernate.ChatDAO;
import com.example.application.components.dialogs.DeleteConfirmDialog;
import com.example.application.components.elements.components.ActiveDot;
import com.example.application.components.elements.Element;
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.server.VaadinSession;

public class ChatPersonElement extends Element {
    private final Chat chat;
    private final MessengerElement messengerElement;

    public ChatPersonElement(MessengerElement messengerElement, Chat chat) {
        super("chatScrollerElement");
        this.chat = chat;
        this.messengerElement = messengerElement;

        VaadinSession.getCurrent().setAttribute("lastClickedButton", null);

        layout();
        listenerAction();
        contextMenu();
    }

    private void contextMenu() {
        ContextMenu contextMenu = new ContextMenu();
        contextMenu.setTarget(this);
        contextMenu.addItem("Delete", menuItemClickEvent -> {

            DeleteConfirmDialog dialog = new DeleteConfirmDialog("Delete the chat?", "All messages from this conversation will be deleted");
            dialog.setAction(buttonClickEvent -> {
                ChatDAO.delete(chat);
                setVisible(false);
                dialog.close();
                messengerElement.removeView();
            });
            dialog.open();
        });


        add(contextMenu);
    }

    @Override
    public void layout() {
        setWidthFull();
        User user = chat.getUserTo();
        setText(user.getDisplayName());

        setChatColor();
        add(ActiveDot.get(chat.getUserTo()));

        setColors();
    }

    @Override
    public void listenerAction() {
        addClickListener(e -> {
            messengerElement.removeView();
            messengerElement.setView(chat);
            messengerElement.setRead();
        });
    }

    private void setColors() {
        addClickListener(event -> {
            if (VaadinSession.getCurrent().getAttribute("lastClickedButton") != null) {
                ((ChatPersonElement) VaadinSession.getCurrent().getAttribute("lastClickedButton")).removeClassName("currentChat");
            }
            addClassName("currentChat");
            VaadinSession.getCurrent().setAttribute("lastClickedButton", this);
        });
    }

    private void setChatColor() {

        boolean isUnRead = ChatDAO.isHaveUnreadMessages(chat);
        if (isUnRead) {
            addClassName("notRead");
            getElement().getStyle().set("order", "-1");

            addClickListener(event -> {
                removeClassName("notRead");
            });
        }

    }
}
