package model;

import exceptions.EmptyException;
import exceptions.NamingException;
import exceptions.NumberException;
import exceptions.PlayerListException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TeamsTest {
    private Teams teams;
    private Team team1;
    private Player player1;

    @BeforeEach
    void runBefore() throws NamingException, NumberException {
        teams = new Teams();
        team1 = new Team("team1");
        player1 = new Player("Name", "Team", 1999, 1,1,1,1);
    }

    @Test
    void testConstructor() {
        assertEquals(0, teams.getSize());
        assertEquals("List of Teams", teams.getName());
    }

    @Test
    void testAddTeam() {
        teams.addTeam(team1);
        assertEquals(1, teams.getSize());
    }

    @Test
    void testDeleteTeamGood() {
        teams.addTeam(team1);
        teams.addTeam(team1);
        try {
            teams.deleteTeam(team1);
        } catch (PlayerListException e) {
            fail("Player List exception not expected.");
        }
        assertEquals(1, teams.getTeams().size());
    }

    @Test
    void testDeleteTeamPlayerList() throws NamingException {
        Team playerList = new Team("Player List");
        teams.addTeam(playerList);
        assertEquals(1, teams.getTeams().size());
        try {
            teams.deleteTeam(playerList);
        fail("Player List exception not thrown");
        } catch (PlayerListException e) {
            //expected
        }
        assertEquals(1, teams.getTeams().size());
    }

    @Test
    void testIsEmptyTrue() {
        assertEquals(0, team1.getPlayers().size());
        try {
            team1.isEmpty();
            fail("Empty exception not thrown.");
        } catch (EmptyException e) {
            //expected
        }
    }

    @Test
    void testIsEmptyFalse() {
        team1.addPlayer(player1);
        assertEquals(1, team1.getPlayers().size());
        try {
            team1.isEmpty();
        } catch (EmptyException e) {
            fail("Empty exception not expected.");
        }
        assertEquals(1, team1.getPlayers().size());
    }

}
