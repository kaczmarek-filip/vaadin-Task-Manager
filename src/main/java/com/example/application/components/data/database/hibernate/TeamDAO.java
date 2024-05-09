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
        start_old();
        Query<TeamMember> query = session.createQuery("FROM TeamMember WHERE team.id = :id");
        query.setParameter("id", id);
        List<TeamMember> teamMembers = query.getResultList();
        close_old();
        return teamMembers;
    }

    public static TeamRoles getUserRole(Team team, User user) {
        start_old();
        Query<TeamRoles> query = session.createQuery("SELECT tm.role FROM TeamMember tm WHERE tm.user = :user AND tm.team = :team");
        query.setParameter("team", team);
        query.setParameter("user", user);
        TeamRoles teamRoles = query.uniqueResult();
        close_old();
        return teamRoles;
    }

    public static List<Team> getUserTeams(User user) {
        start_old();
        Query<Team> query = session.createQuery("SELECT t FROM Team t JOIN t.teamMembers tm WHERE tm.user.id = :id");
        query.setParameter("id", user.getId());
        List<Team> teams = query.getResultList();
        close_old();
        return teams;
    }

    public static Team findTeamByTeamId(int teamId) {
        start_old();
        Team team = session.get(Team.class, teamId);
        close_old();
        return team;
    }

    public static void createTeam(String teamName, String teamMotto, User owner, Set<User> teamMembers) {
        start_old();
        Team team = new Team();
        team.setName(teamName);
        team.setMotto(teamMotto);

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
        close_old();
    }

//    public static void addUsers(Team team, Set<User> userSet) {
    public static void addUsers(TeamMember teamMember) {
        start_old();
        session.save(teamMember);
        commit();
        close_old();
    }

    public static void deleteTeam(Team team) {
        start_old();
        Query<Team> query = session.createQuery("DELETE FROM Team WHERE id = :id");
        query.setParameter("id", team.getId());
        query.executeUpdate();
        commit();
        close_old();
    }
    public static void updateInfo(int teamId, String teamName, String teamMotto){
        start_old();
        Query<Team> query = session.createQuery("UPDATE Team SET name = :name, motto = :motto WHERE id = :id");
        query.setParameter("name", teamName).setParameter("motto", teamMotto).setParameter("id", teamId);
        query.executeUpdate();
        close_old();
    }
    public static void deleteUser(Team team, User user){
        start_old();
        Query<TeamMember> query = session.createQuery("DELETE FROM TeamMember WHERE team.id = :teamId AND user.id = :userId");
        query.setParameter("teamId", team.getId()).setParameter("userId", user.getId());
        query.executeUpdate();
        commit();
        close_old();
    }
    public static void updateRole(Team team, User user, TeamRoles teamRoles){
        start_old();
        Query<TeamMember> query = session.createQuery("UPDATE TeamMember SET role = :role WHERE user.id = :userId AND team.id = :teamId");
        query.setParameter("role", teamRoles).setParameter("userId", user.getId()).setParameter("teamId", team.getId());
        query.executeUpdate();
        commit();
        close_old();
    }
}
