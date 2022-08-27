package model;

import exceptions.EmptyException;
import exceptions.NamingException;
import exceptions.NumberException;
import exceptions.PlayerListException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TeamTest {
    private Team team1;
    private Player player1;
    private Player player2;

    @BeforeEach
    void runBefore() throws NumberException, NamingException {
        team1 = new Team("Team Name");
        player1 = new Player("Player 1", "Team", 1989,
                60, 35, 82, 40);
        player2 = new Player("Player 2", "Team", 1989,
                60, 35, 82, 40);
        team1.addPlayer(player1);
    }

    @Test
    void testConstructorGood() {
        assertEquals("Team Name", team1.getName());
        try {
            try {
                team1.setName("New Team Name");
            } catch (PlayerListException e) {
                fail("Player list exception not expected");
            }
            assertEquals("New Team Name", team1.getName());
            assertEquals(1, team1.getPlayers().size());
        } catch (NamingException e) {
            fail("Naming exception not expected.");
        }
    }

    @Test
    void testConstructorNoName() {
        try {
            team1 = new Team("");
            fail("Naming exception not thrown.");
        } catch (NamingException e) {
            //expected
        }
    }

    @Test
    void testConstructorSetBadName() {
        try {
            team1 = new Team("New Team Name");
            try {
                team1.setName("");
            } catch (PlayerListException e) {
                fail("Player list exception not expected.");
            }
            fail("Naming exception not thrown.");
        } catch (NamingException e) {
            //expected
        }
    }

    @Test
    void testConstructorSetPlayerListName() {
        try {
            team1 = new Team("New Team Name");
            try {
                team1.setName("Player List");
                fail("Player list exception not thrown.");
            } catch (PlayerListException e) {
                //expected
            }
        } catch (NamingException e) {
            fail("Player list exception not thrown.");
        }
    }

    @Test
    void testAddPlayer() {
        assertEquals(1, team1.getPlayers().size());
        team1.addPlayer(player2);
        assertEquals(2, team1.getPlayers().size());
    }

    @Test
    void testRemovePlayer() {
        assertEquals(1, team1.getPlayers().size());
        team1.removePlayer(player1);
        assertEquals(0, team1.getPlayers().size());
    }
}
