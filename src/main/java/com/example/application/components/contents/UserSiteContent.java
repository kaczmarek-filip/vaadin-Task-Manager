package com.example.application.components.contents;

import com.example.application.components.data.User;
import com.example.application.components.data.database.hibernate.UserDAO;
import com.example.application.services.BlobConverter;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;

import java.io.InputStream;

public class UserSiteContent extends HorizontalLayout {

    public UserSiteContent() {
        setAlignItems(Alignment.CENTER);
        add(leftSide(), rightSide());
    }

    private VerticalLayout leftSide() {
        VerticalLayout leftSide = new VerticalLayout();
        Span displayName = new Span(User.getLoggedInUser().getDisplayName());
//        Span email = new Span(User.getLoggedInUser().getEmail());
        Span email = new Span("example.example@example.com");
        VerticalLayout userInfo = new VerticalLayout(displayName, email);

        Details details = new Details("User Details", userInfo);
        details.setOpened(true);


        leftSide.add(details);
        return leftSide;
    }

    private VerticalLayout rightSide() {
        VerticalLayout rightSide = new VerticalLayout();
        MenuBar menuBar = new MenuBar();
        menuBar.addItem("Light");
        menuBar.addItem("Dark");

        Avatar avatar = new Avatar();
        avatar.setImageResource(BlobConverter.getAvatar(User.getLoggedInUser()));

        rightSide.add(menuBar, getUpload(), avatar);
        return rightSide;
    }

    private static Upload getUpload() {
        MemoryBuffer memoryBuffer = new MemoryBuffer();
        //TODO: Hint
        Upload upload = new Upload(memoryBuffer);
        upload.setDropAllowed(true);
        upload.setAcceptedFileTypes("image/jpeg", "image/png");
        upload.setMaxFileSize(1 * 1024 * 1024);

        upload.addSucceededListener(e -> {
            InputStream inputStream = memoryBuffer.getInputStream();
            byte[] bytes = BlobConverter.toBytes(inputStream);
            UserDAO.updateAvatar(bytes);
        });

        return upload;
    }

}
