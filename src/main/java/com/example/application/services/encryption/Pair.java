package com.example.application.services.encryption;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class Pair<K, V> {
    private K first;
    private K second;
}
