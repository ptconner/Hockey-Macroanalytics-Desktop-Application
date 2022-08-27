package persistence;

import exceptions.NamingException;
import exceptions.NumberException;
import exceptions.PlayerListException;
import model.Player;
import model.Team;
import model.Teams;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() throws NumberException, NamingException {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Teams teams = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            //pass
        }
    }

    @Test
    void testReaderEmptyTeams() throws NumberException, NamingException {
        JsonReader reader = new JsonReader("./data/testWriterEmptyTeams.json");
        try {
            Teams teams = reader.read();
            assertEquals("List of Teams", teams.getName());
            assertEquals(0, teams.getSize());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralTeamsAndPlayers() throws NumberException, NamingException {
        JsonReader reader = new JsonReader("./data/testWriterGeneralTeams.json");
        try {
            Teams teams = reader.read();
            assertEquals("List of Teams", teams.getName());
            List<Team> t = teams.getTeams();
            assertEquals(2, teams.getSize());
            Team team1 = t.get(0);
            Team team2 = t.get(1);
            checkTeam("team1", team1);
            checkTeam("team2", team2);

            List<Player> p1 = team1.getPlayers();
            List<Player> p2 = team2.getPlayers();
            assertEquals(2, p1.size());
            assertEquals(3, p2.size());
            checkPlayer("player1", "van", 1999, 1,1,1,1, p1.get(0));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
