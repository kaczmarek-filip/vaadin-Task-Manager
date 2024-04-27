package com.example.application.services.encryption;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.SneakyThrows;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.lang.ref.PhantomReference;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Base64;

/**
 * Encrypter uses static key located in resources
 * <a {@link PhantomReference=resources/encodedKey.json}>
 */
public class StaticEncrypter {
    @Getter
    private static final Path filePath = Path.of("src/main/resources/encryptionKeys.json");

    @SneakyThrows
    static SecretKey getStaticKey(AesKeys aesKeys) {

        JsonEncrypt keys;
        ObjectMapper objectMapper = new ObjectMapper();
        keys = objectMapper.readValue(filePath.toFile(), JsonEncrypt.class);

        String encodedKey = null;
        if (aesKeys == AesKeys.MESSAGE) {
            encodedKey = keys.getMessageAesKey();
        } else if (aesKeys == AesKeys.PASSWORD) {
            encodedKey = keys.getPasswordAesKey();
        }

        byte[] decodedKey = Base64.getDecoder().decode(encodedKey);

        return new SecretKeySpec(decodedKey, "AES");
    }

    @SneakyThrows
    public static String encryptByStaticKey(AesKeys key, String data) {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, getStaticKey(key));
        byte[] encryptedBytes = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    @SneakyThrows
    public static String decryptByStaticKey(AesKeys key, String encryptedData) {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, getStaticKey(key));
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }
}
