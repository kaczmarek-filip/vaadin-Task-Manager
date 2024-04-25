package com.example.application.components.data;

import com.example.application.components.data.database.hibernate.TeamDAO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@Entity
@Table(name = "teams")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String motto;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<TeamMember> teamMembers;
    public static int mottoCharLimit = 200;

    public Team(int id, String name, String motto, List<TeamMember> teamMembers) {
        this.id = id;
        this.name = name;
        this.motto = motto;
        this.teamMembers = teamMembers;
    }

    public Team() {
    }

    public static TeamRoles getUserTeamRole(User user, int teamId) {

        for (TeamMember teamMember : TeamDAO.findUsersInTeam(teamId)) {
            User entryKey = teamMember.getUser();
            TeamRoles entryValue = teamMember.getRole();

            if (entryKey.equals(user)) {
                return entryValue;
            }

        }

        return null;
    }
}
