package com.example.application.services;

import com.example.application.components.data.User;
import com.example.application.components.data.database.hibernate.UserDAO;
import com.vaadin.flow.server.StreamResource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class BlobConverter {
    public static byte[] toBytes(InputStream inputStream) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            return outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static StreamResource getAvatar(User user) {
        byte[] bytes = UserDAO.getAvatar(user);

        StreamResource streamResource = null;
        if (bytes != null && bytes.length > 0) {
            streamResource = new StreamResource("avatar.jpg", () -> new ByteArrayInputStream(bytes));
        } else {
            System.err.println("Brak zdjęcia");
        }

        return streamResource;
    }
}
