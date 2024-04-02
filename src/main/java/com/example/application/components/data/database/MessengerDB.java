package com.example.application.components.data.database;

import com.example.application.components.data.Team;
import com.example.application.components.data.TeamRoles;
import com.example.application.components.data.User;
import com.vaadin.flow.component.notification.Notification;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MessengerDB extends DatabaseConnection {
    public void addChat(User user1, User user2){
        query = sqlParser.addChat(user1, user2);

        try {
            statement.executeUpdate(query);

            statement.close();
            connection.close();
        } catch (Exception e) {
            Notification.show(error, 5000, Notification.Position.BOTTOM_CENTER);
        }
    }
    public ArrayList<User> getUserChats(User user){
        query = sqlParser.getUserChats(user);
        ArrayList<User> userArrayList = new ArrayList<>();

        try (ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int DbUser1_ID = resultSet.getInt("user1_ID");
                int DbUser2_ID = resultSet.getInt("user2_ID");
                int DbUserId;
                String DbDisplayName;
                String DbEmail;

                if (DbUser1_ID == user.getId()){
                    DbUserId = DbUser2_ID;
                    DbDisplayName = resultSet.getString("user2_displayName");
                    DbEmail = resultSet.getString("user2_email");

                } else {
                    DbUserId = DbUser1_ID;
                    DbDisplayName = resultSet.getString("user1_displayName");
                    DbEmail = resultSet.getString("user1_email");
                }

                userArrayList.add(new User(DbUserId, DbDisplayName, DbEmail));
            }
            statement.close();
            connection.close();
        } catch (Exception e) {
            Notification.show(error, 5000, Notification.Position.BOTTOM_CENTER);
        }

        return userArrayList;
    }
}
