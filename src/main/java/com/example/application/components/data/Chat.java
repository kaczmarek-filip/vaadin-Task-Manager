package com.example.application.components.data;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "chats")
@Setter
@Getter
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatID")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user1_ID")
    private User user1;
    @ManyToOne
    @JoinColumn(name = "user2_ID")
    private User user2;

    public User getUserTo() {
        if (!user1.equals(User.getLoggedInUser())) {
            return user1;
        } else {
            return user2;
        }
    }
}
