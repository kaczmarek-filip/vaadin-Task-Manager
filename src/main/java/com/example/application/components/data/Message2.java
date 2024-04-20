package com.example.application.components.data;


import jakarta.persistence.*;
import org.hibernate.annotations.Type;

import java.time.LocalDateTime;

@Entity
@Table(name = "messages2")
@Access(value = AccessType.FIELD)
//@Getter @Setter
//@Deprecated
public class Message2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "user1_ID")
    private int user1_ID;

    @Column(name = "user2_ID")
    private int user2_ID;

    @Column(name = "content")
    private String content;

    @Column(name = "dateTime")
    private LocalDateTime localDateTime;

    public Message2() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getUser1_ID() {
        return user1_ID;
    }

    public void setUser1_ID(int user1_ID) {
        this.user1_ID = user1_ID;
    }

    public int getUser2_ID() {
        return user2_ID;
    }

    public void setUser2_ID(int user2_ID) {
        this.user2_ID = user2_ID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }
}
