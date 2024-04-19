package com.example.application.services.encryption;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Encrypter uses static key located in resources
 * <a {@link java.lang.ref.PhantomReference=resources/encodedKey.json}>
 */
public class StaticEncrypter {
    private static final String fileName = "encryptionKey.json";
    private static final String rootNodePath = "encryption";
    private static final String keyPath = "message_aes_key";

    @SneakyThrows
    private static SecretKey getStaticKey() {

        ObjectMapper objectMapper = new ObjectMapper();
        ClassLoader classLoader = StaticEncrypter.class.getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());

        JsonNode rootNode = objectMapper.readTree(file);
        JsonNode encryptionNode = rootNode.path(rootNodePath);

        String encodedKey = encryptionNode.path(keyPath).asText();
        byte[] decodedKey = Base64.getDecoder().decode(encodedKey);

        return new SecretKeySpec(decodedKey, "AES");
    }

    @SneakyThrows
    public static String encryptByStaticKey(String data) {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, getStaticKey());
        byte[] encryptedBytes = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    @SneakyThrows
    public static String decryptByStaticKey(String encryptedData) {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, getStaticKey());
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }
}
