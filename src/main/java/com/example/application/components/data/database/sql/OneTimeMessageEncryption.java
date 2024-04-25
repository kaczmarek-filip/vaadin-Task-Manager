package com.example.application.components.data.database.sql;

import com.example.application.services.encryption.StaticEncrypter;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

@Component
@Deprecated
public class OneTimeMessageEncryption extends DatabaseConnection {
    private static final Logger logger = LoggerFactory.getLogger(OneTimeMessageEncryption.class);

    @SneakyThrows
    private Map<Integer, String> getAllMessages() {
        Map<Integer, String> map = new HashMap<>();
        String query = "SELECT ID, content FROM `messages`";

        try (ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                int DbId = resultSet.getInt("ID");
                String DbPassword = resultSet.getString("content");

                map.put(DbId, DbPassword);
            }
        }

        return map;
    }

    @SneakyThrows
    private void sendToDatabase(Map<Integer, String> map) {
        for (Map.Entry<Integer, String> m : map.entrySet()) {
            String updateQuery = "UPDATE `messages` SET `content`='" + m.getValue() + "' WHERE ID = " + m.getKey();
            statement.executeUpdate(updateQuery);
        }
    }

    public void startupEncrypt() {
        try {
            Map<Integer, String> map = getAllMessages();
            map.replaceAll((integer, s) -> StaticEncrypter.encryptByStaticKey(s));
            sendToDatabase(map);
            logger.info("Passwords encrypted properly".toUpperCase());
        } catch (Exception e) {
            logger.error(e.toString());
        }

    }

}