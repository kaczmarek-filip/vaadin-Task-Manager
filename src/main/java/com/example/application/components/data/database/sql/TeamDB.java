package com.example.application.components.data.database.sql;

import com.example.application.components.data.Team;
import com.example.application.components.data.TeamRoles;
import com.example.application.components.data.User;
import com.vaadin.flow.component.notification.Notification;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TeamDB extends DatabaseConnection {
    /**
     * @param user user for search
     * @return ArrayList of {@link Team}
     */
    public ArrayList<Team> findTeamsByUser(User user) {
        query = sqlParser.findTeamsByUser(user);
        ArrayList<Team> userTeamsArrayList = new ArrayList<>();

        try (ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int DbTeamId = resultSet.getInt("ID");
                String DbTeamName = resultSet.getString("name");
                String DbTeamMotto = resultSet.getString("motto");
                TeamRoles DbRole = TeamRoles.valueOf(resultSet.getString("role"));

                Map<User, TeamRoles> userTeamRolesMap = new HashMap<>();
                userTeamRolesMap.put(user, DbRole);

                userTeamsArrayList.add(new Team(DbTeamId, DbTeamName, DbTeamMotto, userTeamRolesMap));
            }
            statement.close();
            connection.close();
        } catch (Exception e) {
            Notification.show(error, 5000, Notification.Position.BOTTOM_CENTER);
        }

        return userTeamsArrayList;
    }

    /**
     * @param teamId team ID
     * @return Team members with roles by ID
     */
    public Map<User, TeamRoles> findUsersInTeam(int teamId) {
        query = sqlParser.findUsersInTeam(teamId);
        Map<User, TeamRoles> userTeamRolesMap = new HashMap<>();

        try (ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {

                int DbId = resultSet.getInt("ID");
                String DbEmail = resultSet.getString("email");
//                    String DbPassword = resultSet.getString("password");
                String DbDisplayName = resultSet.getString("displayName");

                TeamRoles DbRole = TeamRoles.valueOf(resultSet.getString("role"));

                userTeamRolesMap.put(new User(DbId, DbDisplayName, DbEmail), DbRole);

            }
            statement.close();
            connection.close();
        } catch (Exception e) {
            Notification.show(error, 5000, Notification.Position.BOTTOM_CENTER);
        }


        return userTeamRolesMap;
    }
    /**
     * Searching for full team information by ID
     *
     * @param teamId team ID
     * @return {@link Team} for teamId
     */
    public Team findTeamByTeamId(int teamId) {
        query = sqlParser.findTeamByTeamId(teamId);

        try (ResultSet resultSet = statement.executeQuery(query)) {
            if (resultSet.next()) {
                int DbTeamId = resultSet.getInt("ID");
                String DbTeamName = resultSet.getString("name");
                String DbTeamMotto = resultSet.getString("motto");

                Map<User, TeamRoles> userTeamRolesMap = new HashMap<>();

                do {
                    int DbUserId = resultSet.getInt("user_ID");
                    String DbUserEmail = resultSet.getString("email");
                    String DbUserDisplayName = resultSet.getString("displayName");

                    TeamRoles DbRole = TeamRoles.valueOf(resultSet.getString("role"));

                    userTeamRolesMap.put(new User(DbUserId, DbUserDisplayName, DbUserEmail), DbRole);
                } while ((resultSet.next()));

                return new Team(DbTeamId, DbTeamName, DbTeamMotto, userTeamRolesMap);
            }
            statement.close();
            connection.close();
        } catch (Exception e) {
            Notification.show(error, 5000, Notification.Position.BOTTOM_CENTER);
        }
        return null;
    }
    /**
     * Create team
     *
     * @param teamName    team name
     * @param teamMotto   team
     * @param owner       team owner
     * @param teamMembers team members
     */
    public void createTeam(String teamName, String teamMotto, User owner, Set<User> teamMembers) {
        ArrayList<String> queryArrayList = sqlParser.createTeam(teamName, teamMotto, owner, teamMembers);

        try {
            for (String query : queryArrayList) {
                statement.executeUpdate(query);
            }

            statement.close();
            connection.close();
        } catch (Exception e) {
            Notification.show(e.toString(), 5000, Notification.Position.BOTTOM_CENTER);
        }
    }

    /**
     * Delete team
     *
     * @param teamId to be removed
     */
    public void deleteTeam(int teamId) {
        query = sqlParser.deleteTeam(teamId);
        try {
            statement.executeUpdate(query);

            statement.close();
            connection.close();
        } catch (Exception e) {
            Notification.show(e.toString(), 5000, Notification.Position.BOTTOM_CENTER);
        }

    }
    public void updateTeamInfo(int teamId, String teamName, String teamMotto) {
        query = sqlParser.updateTeamInfo(teamId, teamName, teamMotto);

        try {
            statement.executeUpdate(query);

            statement.close();
            connection.close();
        } catch (Exception e) {
            Notification.show(e.toString(), 5000, Notification.Position.BOTTOM_CENTER);
        }
    }
    public void insertUsersIntoTeam(int teamId, Set<User> userSet) {
        ArrayList<String> queryArrayList = sqlParser.insertUsersIntoTeam(teamId, userSet);

        try {
            for (String query : queryArrayList) {
                statement.executeUpdate(query);
            }

            statement.close();
            connection.close();
        } catch (Exception e) {
            Notification.show(e.toString(), 5000, Notification.Position.BOTTOM_CENTER);
        }
    }
    public void deleteUserFromTeam(Team team, User user){
        query = sqlParser.deleteUserFromTeam(team, user);

        try {
            statement.executeUpdate(query);

            statement.close();
            connection.close();
        } catch (Exception e) {
            Notification.show(e.toString(), 5000, Notification.Position.BOTTOM_CENTER);
        }
    }
    public void updateUserRole(Team team, User user, TeamRoles teamRoles){
        query = sqlParser.updateUserRole(team, user, teamRoles);

        try {
            statement.executeUpdate(query);

            statement.close();
            connection.close();
        } catch (Exception e) {
            Notification.show(e.toString(), 5000, Notification.Position.BOTTOM_CENTER);
        }
    }
}
