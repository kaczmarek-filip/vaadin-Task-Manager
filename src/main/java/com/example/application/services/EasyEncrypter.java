package com.example.application.services;

import com.example.application.services.encryption.EncryptionMethod;
import com.example.application.services.encryption.Pair;
import com.vaadin.flow.component.notification.Notification;

import java.util.ArrayList;

public class EasyEncrypter {
    private String key;
    private ArrayList<Pair<Character, Character>> keyPairs;

    public EasyEncrypter(EncryptionMethod encryptionMethod) {
        this.key = encryptionMethod.toString();
        this.keyPairs = prepareKeyPairs();
    }

    private ArrayList<Pair<Character, Character>> prepareKeyPairs() {
        ArrayList<Pair<Character, Character>> pairArrayList = new ArrayList<>();

        for (int i = 0; i < key.length(); i++) {
            char[] chars = key.toLowerCase().toCharArray();

            Pair<Character, Character> pair = new Pair<>();

            if (i % 2 == 0) {
                pair.setFirst(chars[i]);
                pair.setSecond(chars[i + 1]);
                pairArrayList.add(pair);
                Notification.show(pair.getFirst() + pair.getSecond().toString());
            }
        }
        return pairArrayList;
    }

    /**
     * In ciphers of this type, encryption and decryption work in the same way
     *
     * @param password password to encrypt
     * @return encrypted password
     */
    public String encode(String password) {
        StringBuilder stringBuilder = new StringBuilder();

        for (char ch : password.toCharArray()) {
            boolean replaced = false;
            for (Pair<Character, Character> pair : keyPairs) {
                if (ch == pair.getFirst()) {
                    stringBuilder.append(pair.getSecond());
                    replaced = true;
                    break;

                } else if (ch == pair.getSecond()) {
                    stringBuilder.append(pair.getFirst());
                    replaced = true;
                    break;

                }
            }
            if (!replaced) {
                stringBuilder.append(ch);
            }

        }

        return stringBuilder.toString();
    }
}
