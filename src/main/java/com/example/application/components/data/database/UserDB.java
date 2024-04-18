package com.example.application.components.data.database;

import com.example.application.components.data.User;
import com.example.application.services.encryption.Encrypter;
import com.vaadin.flow.component.notification.Notification;

import java.sql.ResultSet;
import java.util.ArrayList;

public class UserDB extends DatabaseConnection {
    /**
     * Method to register user in database
     *
     * @param user User to register in database
     */
    public void registerUser(User user, String password) {
        query = sqlParser.createUser(user, password);

        try {
            statement.executeUpdate(query);

            statement.close();
            connection.close();
        } catch (Exception e) {
            Notification.show(e.toString(), 5000, Notification.Position.BOTTOM_CENTER);
        }
    }

    /**
     * @param user User to check is email already exists in DB
     * @return true if email already exists | otherwise false
     */
    public boolean isEmailExists(User user) {
        query = sqlParser.isEmailExists(user);

        try (ResultSet resultSet = statement.executeQuery(query)) {

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                if (count != 0) {
                    return true;
                }
            }
            statement.close();
            connection.close();
        } catch (Exception e) {
            Notification.show(error, 5000, Notification.Position.BOTTOM_CENTER);
        }
        return false;
    }

    public User loginUser(String email, String password) {
        query = sqlParser.loginUser(email, password);

        try (ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {

                int DbId = resultSet.getInt("ID");
                String DbEmail = resultSet.getString("email");
                String DbPassword = resultSet.getString("password");
                String DbDisplayName = resultSet.getString("displayName");

                DbPassword = Encrypter.decrypt(DbPassword);
                if (email.equals(DbEmail) && password.equals(DbPassword)) {
                    return new User(DbId, DbDisplayName, DbEmail);
                }
            }
            statement.close();
            connection.close();
        } catch (Exception e) {
            Notification.show(error, 5000, Notification.Position.BOTTOM_CENTER);
        }

        return null;
    }

    /**
     * @return all registered users
     */
    public ArrayList<User> getAllUsers() {
        ArrayList<User> allUsers = new ArrayList<>();
        query = sqlParser.getAllUsers();
        try (ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {

                int DbId = resultSet.getInt("ID");
                String DbEmail = resultSet.getString("email");
                String DbDisplayName = resultSet.getString("displayName");

                allUsers.add(new User(DbId, DbDisplayName, DbEmail));
            }
        } catch (Exception e) {
            Notification.show(error, 5000, Notification.Position.BOTTOM_CENTER);
        }

        return allUsers;
    }
    public User getUserById(int id){
        query = sqlParser.getUserById(id);

        try (ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                String DbEmail = resultSet.getString("email");
                String DbDisplayName = resultSet.getString("displayName");
                    return new User(id, DbDisplayName, DbEmail);
            }
            statement.close();
            connection.close();
        } catch (Exception e) {
            Notification.show(e.toString(), 5000, Notification.Position.BOTTOM_CENTER);
        }
        return null;
    }
}
