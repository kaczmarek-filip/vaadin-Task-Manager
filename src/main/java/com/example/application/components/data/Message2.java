package com.example.application.components.data;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "messages2")
@Getter @Setter
@Deprecated
public class Message2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "ID")
    private Long id;

    @Column(name = "user1_ID")
    private int user1_ID;

    @Column(name = "user2_ID")
    private int user2_ID;

    @Column(name = "content")
    private String content;

    @Column(name = "dateTime")
    private LocalDateTime localDateTime;
}
