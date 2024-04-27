package com.example.application.services.encryption;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JsonEncrypt {

    @JsonProperty("message_aes_key")
    private String messageAesKey;

    @JsonProperty("password_aes_key")
    private String passwordAesKey;
}
