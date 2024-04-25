package com.example.application.components.data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "team_member")
@Getter @Setter
public class TeamMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "team_ID")
    private Team team;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_ID")
    private User user;
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private TeamRoles role;

    public TeamMember() {
    }
}
