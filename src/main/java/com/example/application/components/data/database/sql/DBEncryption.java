package com.example.application.components.data.database.sql;

import com.example.application.services.encryption.Encrypter;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

@Component
public class DBEncryption extends DatabaseConnection implements ApplicationListener<ContextClosedEvent> {
    private static final Logger logger = LoggerFactory.getLogger(DBEncryption.class);

    /**
     * Every time the App is switched off
     *
     * @param event event
     */
    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        try {
            Map<Integer, String> map = getAllPass();
            map.replaceAll((integer, s) -> Encrypter.decrypt(s));
            sendToDatabase(map);
            logger.info("Passwords decrypted properly".toUpperCase());
        } catch (Exception e) {
            logger.error(error);
        }


    }

    @SneakyThrows
    private Map<Integer, String> getAllPass() {
        Map<Integer, String> map = new HashMap<>();
        String query = "SELECT ID, password FROM `users`";

        try (ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                int DbId = resultSet.getInt("ID");
                String DbPassword = resultSet.getString("password");

                map.put(DbId, DbPassword);
            }
        }

        return map;
    }

    @SneakyThrows
    private void sendToDatabase(Map<Integer, String> map) {
        for (Map.Entry<Integer, String> m : map.entrySet()) {
            String updateQuery = "UPDATE `users` SET `password`='" + m.getValue() + "' WHERE ID = " + m.getKey();
            statement.executeUpdate(updateQuery);
        }
    }

    /**
     * @throws Exception if can't decode passwords
     */
    public void startupEncrypt() throws Exception{
        try {
            Map<Integer, String> map = getAllPass();
            map.replaceAll((integer, s) -> Encrypter.encrypt(s));
            sendToDatabase(map);
            logger.info("Passwords encrypted properly".toUpperCase());
        } catch (Exception e) {
            logger.error(e.toString());
            throw e;
        }

    }

}
