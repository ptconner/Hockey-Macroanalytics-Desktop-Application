package model;

import exceptions.NamingException;
import exceptions.NumberException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    private Player player1;
    private Player player2;

    @BeforeEach
    void runBefore() throws NumberException, NamingException {
        player1 = new Player("Name", "Team", 1989,
                60, 35, 82, 40);
    }

    @Test
    public void testConstructorGood() {
            assertEquals("Name", player1.getName());
            assertEquals("Team", player1.getTeam());
            assertEquals(1989, player1.getYear());
            assertEquals(60, player1.getPlayerGames());
            assertEquals(35, player1.getPlayerWins());
            assertEquals(82, player1.getTeamGames());
            assertEquals(40, player1.getTeamWins());
            assertEquals(22.0, player1.getPlayerScore());
            assertEquals(24.0, player1.getPlayerAdv());
            assertEquals(-2.0, player1.getTeamScore());
    }

    @Test
    public void testConstructorBadName() {
        try {
            Player playerBadName = new Player("", "Team", 1989,
                    60, 35, 82, 40);
            fail("Naming exception not thrown.");
        } catch (NamingException e) {
            //expected
        } catch (NumberException e) {
            fail("Number exception not expected.");
        }
    }

    @Test
    public void testConstructorBadTeam() {
        try {
            Player playerBadName = new Player("Name", "", 1989,
                    60, 35, 82, 40);
            fail("Naming exception not thrown.");
        } catch (NamingException e) {
            //expected
        } catch (NumberException e) {
            fail("Number exception not expected.");
        }
    }

    @Test
    public void testConstructorBadPlayerGames() {
        try {
            Player playerBadName = new Player("Name", "Team", 1989,
                    -1, 35, 82, 40);
            fail("Number exception not thrown.");
        } catch (NamingException e) {
            fail("Naming exception not expected.");
        } catch (NumberException e) {
            //expected
        }
    }

    @Test
    public void testConstructorBadPlayerWins() {
        try {
            Player playerBadName = new Player("Name", "Team", 1989,
                    1, -1, 82, 40);
            fail("Number exception not thrown.");
        } catch (NamingException e) {
            fail("Naming exception not expected.");
        } catch (NumberException e) {
            //expected
        }
    }

    @Test
    public void testConstructorBadTeamGames() {
        try {
            Player playerBadName = new Player("Name", "Team", 1989,
                    1, 35, -1, 40);
            fail("Number exception not thrown.");
        } catch (NamingException e) {
            fail("Naming exception not expected.");
        } catch (NumberException e) {
            //expected
        }
    }

    @Test
    public void testConstructorBadTeamWins() {
        try {
            Player playerBadName = new Player("Name", "Team", 1989,
                    1, 35, 82, -1);
            fail("Number exception not thrown.");
        } catch (NamingException e) {
            fail("Naming exception not expected.");
        } catch (NumberException e) {
            //expected
        }
    }

    @Test
    void testEditPlayerGood() {
        try {
            player1.editPlayer("New Name", "New Team", 2000,
                    50, 20, 82, 40);
            assertEquals("New Name", player1.getName());
            assertEquals("New Team", player1.getTeam());
            assertEquals(2000, player1.getYear());
            assertEquals(50, player1.getPlayerGames());
            assertEquals(20, player1.getPlayerWins());
            assertEquals(82, player1.getTeamGames());
            assertEquals(40, player1.getTeamWins());
        } catch (NamingException e) {
            fail("Naming exception not expected.");
        } catch (NumberException e) {
            fail("Number exception not expected.");
        }
    }

    @Test
    void testEditPlayerBadName() {
        try {
            player1.editPlayer("", "Team", 2000,
                    50, 20, 82, 40);
            fail("Naming exception not thrown.");
        } catch (NamingException e) {
            //expected
        } catch (NumberException e) {
            fail("Naming exception not expected.");
        }
    }

    @Test
    void testEditPlayerBadTeam() {
        try {
            player1.editPlayer("Name", "", 2000,
                    50, 20, 82, 40);
            fail("Naming exception not thrown.");
        } catch (NamingException e) {
            //expected
        } catch (NumberException e) {
            fail("Naming exception not expected.");
        }
    }

    @Test
    void testEditPlayerBadPlayerGames() {
        try {
            player1.editPlayer("New Name", "New Team", 2000,
                    -1, 1, 1, 1);
            fail("Number exception not thrown.");
        } catch (NamingException e) {
            fail("Naming exception not expected.");
        } catch (NumberException e) {
            //expected
        }
    }

    @Test
    void testEditPlayerBadPlayerWins() {
        try {
            player1.editPlayer("New Name", "New Team", 2000,
                    1, -1, 1, 1);
            fail("Number exception not thrown.");
        } catch (NamingException e) {
            fail("Naming exception not expected.");
        } catch (NumberException e) {
            //expected
        }
    }

    @Test
    void testEditPlayerBadTeamGames() {
        try {
            player1.editPlayer("New Name", "New Team", 2000,
                    1, 1, -1, 1);
            fail("Number exception not thrown.");
        } catch (NamingException e) {
            fail("Naming exception not expected.");
        } catch (NumberException e) {
            //expected
        }
    }

    @Test
    void testEditPlayerBadTeamWins() {
        try {
            player1.editPlayer("New Name", "New Team", 2000,
                    1, 1, 1, -1);
            fail("Number exception not thrown.");
        } catch (NamingException e) {
            fail("Naming exception not expected.");
        } catch (NumberException e) {
            //expected
        }
    }

    @Test
    void testReturnStats() {
        String expected = player1.getName() + ", " + player1.getTeam() + ", " + player1.getYear() + " -- "
                + "GP: " + player1.getPlayerGames()
                + ", Score: " + player1.getPlayerScore()
                + ", Advantage: " + player1.getPlayerAdv();
        assertEquals(player1.returnStats(), expected);
    }
}