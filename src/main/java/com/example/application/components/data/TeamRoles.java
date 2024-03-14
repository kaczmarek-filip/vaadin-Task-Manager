package com.example.application.components.data;

public enum TeamRoles {
//    ADMIN(0),
//    USER(1),
//    MODERATOR(2),
    MEMBER("Member"),
    ADMIN("Admin"),
    OWNER("Owner"),
    MODERATOR("Moderator");

    private final String value;

    TeamRoles(String value) {
        this.value = value;
    }
    public String getValue(){
        return value;
    }
}
