package com.example.application.services.session;

public enum SessionEnum {
    HIBERNATE_SESSION("hibernateSession");

    private final String string;

    private SessionEnum(String string) {
        this.string = string;
    }

    public String getString() {
        return this.string;
    }

}
