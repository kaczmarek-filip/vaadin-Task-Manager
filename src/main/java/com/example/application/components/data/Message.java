package com.example.application.components.data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
@Setter
@Getter
//TODO: Odświeżanie wiadomości zaraz po tym jak przyjdą
public final class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "chatID")
    private Chat chat;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sender_ID")
    private User sender;

    private String content;

    @Column(name = "dateTime")
    private LocalDateTime localDateTime;

    private boolean isRead;

    public Message(Chat chat, User sender, String content, LocalDateTime localDateTime) {
        this.chat = chat;
        this.sender = sender;
        this.content = content;
        this.localDateTime = localDateTime;
    }

    public Message() {

    }
}
