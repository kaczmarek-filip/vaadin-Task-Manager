package com.example.application.components.data.database.hibernate;

import com.example.application.components.data.Team;
import com.example.application.components.data.TeamMember;
import com.example.application.components.data.TeamRoles;
import com.example.application.components.data.User;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Set;

public class TeamDAO extends HibernateConnection {
    public static List<TeamMember> findUsersInTeam(int id) {
        start();
        Query<TeamMember> query = session.createQuery("FROM TeamMember WHERE team.id = :id");
        query.setParameter("id", id);
        List<TeamMember> teamMembers = query.getResultList();
        close();
        return teamMembers;
    }

    public static TeamRoles getUserRole(Team team, User user) {
        start();
        Query<TeamRoles> query = session.createQuery("SELECT tm.role FROM TeamMember tm WHERE tm.user = :user AND tm.team = :team");
        query.setParameter("team", team);
        query.setParameter("user", user);
        TeamRoles teamRoles = query.uniqueResult();
        close();
        return teamRoles;
    }

    public static List<Team> getUserTeams(User user) {
        start();
        Query<Team> query = session.createQuery("SELECT t FROM Team t JOIN t.teamMembers tm WHERE tm.user.id = :id");
        query.setParameter("id", user.getId());
        List<Team> teams = query.getResultList();
        close();
        return teams;
    }

    public static Team findTeamByTeamId(int teamId) {
        start();
        Team team = session.get(Team.class, teamId);
        close();
        return team;
    }

    public static void createTeam(Team team, User owner, Set<User> teamMembers) {
        start();

        TeamMember teamOwner = new TeamMember();
        teamOwner.setUser(session.get(User.class, owner.getId()));
        teamOwner.setRole(TeamRoles.OWNER);
        teamOwner.setTeam(team);

        session.save(team);
        session.save(teamOwner);

        for (User user : teamMembers) {
            TeamMember teamMember = new TeamMember();
            teamMember.setUser(user);
            teamMember.setRole(TeamRoles.MEMBER);
            teamMember.setTeam(team);
            session.save(teamMember);
        }
        commit();
        close();
    }

//    public static void addUsers(Team team, Set<User> userSet) {
    public static void addUsers(TeamMember teamMember) {
        start();
        session.save(teamMember);
        commit();
        close();
    }

    public static void deleteTeam(Team team) {
        start();
        Query<Team> query = session.createQuery("DELETE FROM Team WHERE id = :id");
        query.setParameter("id", team.getId());
        query.executeUpdate();
        commit();
        close();
    }
    public static void updateInfo(int teamId, String teamName, String teamMotto){
        start();
        Query<Team> query = session.createQuery("UPDATE Team SET name = :name, motto = :motto WHERE id = :id");
        query.setParameter("name", teamName).setParameter("motto", teamMotto).setParameter("id", teamId);
        query.executeUpdate();
        close();
    }
    public static void deleteUser(Team team, User user){
        start();
        Query<TeamMember> query = session.createQuery("DELETE FROM TeamMember WHERE team.id = :teamId AND user.id = :userId");
        query.setParameter("teamId", team.getId()).setParameter("userId", user.getId());
        query.executeUpdate();
        commit();
        close();
    }
    public static void updateRole(Team team, User user, TeamRoles teamRoles){
        start();
        Query<TeamMember> query = session.createQuery("UPDATE TeamMember SET role = :role WHERE user.id = :userId AND team.id = :teamId");
        query.setParameter("role", teamRoles).setParameter("userId", user.getId()).setParameter("teamId", team.getId());
        query.executeUpdate();
        commit();
        close();
    }
}
