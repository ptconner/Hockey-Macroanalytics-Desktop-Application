package persistence;

import model.Player;
import model.Team;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkTeam(String name, Team team) {
        assertEquals(name, team.getName());
    }

    protected void checkPlayer(String name, String team, int year,
                               int playerGames, int playerWins, int teamGames, int teamWins, Player player) {
        assertEquals(name, player.getName() );
        assertEquals(team, player.getTeam() );
        assertEquals(year, player.getYear() );
        assertEquals(playerGames, player.getPlayerGames() );
        assertEquals(playerWins, player.getPlayerWins() );
        assertEquals(teamGames, player.getTeamGames() );
        assertEquals(teamWins, player.getTeamWins() );
    }
}
