package com.example.application.components.dialogs;

import com.example.application.components.data.User;
import com.example.application.components.data.database.sql.MessengerDB;
import com.example.application.components.data.database.HibernateUser;
import com.example.application.components.elements.components.ChatCreateCallback;
import com.example.application.components.elements.components.CancelButton;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;

import java.util.List;

public class AddChatDialog extends Dialog {
    private static final int width = 20;
    private final ComboBox<User> userComboBoxField = new ComboBox<>();
    private final ChatCreateCallback callback;
    public AddChatDialog(ChatCreateCallback callback) {
        this.callback = callback;
        setHeaderTitle("Add chat");
        setCloseOnOutsideClick(false);
        setCloseOnEsc(false);

        setWidth(width, Unit.PERCENTAGE);

        add(setUserComboBoxField());

        getFooter().add(new CancelButton(this), addButton());
    }
    private Button addButton(){
        Button button = new Button();
        button.setText("Add");
        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);

        button.addClickListener(e -> {
            User selectedUser = userComboBoxField.getValue();
            new MessengerDB().addChat(selectedUser, User.getLoggedInUser());
            close();
            callback.onSave(userComboBoxField.getValue());
        });
        return button;
    }
    private ComboBox<User> setUserComboBoxField() {
        List<User> allUsersList = HibernateUser.getAllUsers();
        allUsersList.remove(User.getLoggedInUser());
        allUsersList.removeAll(new MessengerDB().getUserChats(User.getLoggedInUser()));

        userComboBoxField.setWidthFull();

        userComboBoxField.setItems(allUsersList);
        userComboBoxField.setItemLabelGenerator(User::getEmail);

        userComboBoxField.setAllowCustomValue(false);


        return userComboBoxField;
    }
}
