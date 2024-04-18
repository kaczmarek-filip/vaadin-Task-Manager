package com.example.application.services.encryption;

import lombok.Getter;
import lombok.SneakyThrows;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Encrypting and Decrypting data
 *
 * A new key is generated each time the application is started
 */
public class Encrypter {
    private static SecretKey secretKey;

    @SneakyThrows
    public static void generateKey(){
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256);
        secretKey = keyGenerator.generateKey();
    }
    @SneakyThrows
    public static String encrypt(String data) {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }
    @SneakyThrows
    public static String decrypt(String encryptedData) {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }
}
