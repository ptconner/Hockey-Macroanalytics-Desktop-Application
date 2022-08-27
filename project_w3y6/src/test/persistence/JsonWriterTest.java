package persistence;

import exceptions.NamingException;
import exceptions.NumberException;
import model.Player;
import model.Team;
import model.Teams;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            //pass
        }
    }

    @Test
    void testWriterEmptyTeams() throws NumberException, NamingException {
        try {
            Teams teams = new Teams();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyTeams.json");
            writer.open();
            writer.write(teams);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyTeams.json");
            teams = reader.read();
            assertEquals("List of Teams", teams.getName());
            assertEquals(0, teams.getSize());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralTeams() throws NumberException, NamingException {
        try {
            Teams teams = new Teams();
            Team team1 = new Team("team1");
            Team team2 = new Team("team2");
            Player player1 = new Player("player1", "van", 1999,
                    1, 1, 1, 1);
            Player player2 = new Player("player2", "van", 1999,
                    1, 1, 1, 1);
            teams.addTeam(team1);
            teams.addTeam(team2);
            team1.addPlayer(player1);
            team1.addPlayer(player2);
            team2.addPlayer(player2);
            team2.addPlayer(player1);
            team2.addPlayer(player2);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralTeams.json");
            writer.open();
            writer.write(teams);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralTeams.json");
            teams = reader.read();
            assertEquals("List of Teams", teams.getName());
            List<Team> t = teams.getTeams();
            checkTeam("team1", t.get(0));
            checkTeam("team2", t.get(1));
        } catch (IOException e) {
            fail("Exception should not have been thrown.");
        }
    }
}
