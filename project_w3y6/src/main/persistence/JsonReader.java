package persistence;

import exceptions.NamingException;
import exceptions.NumberException;
import exceptions.PlayerListException;
import model.Player;
import model.Team;
import model.Teams;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

//represents a reader that reads team from JSON data stored in file
public class JsonReader {
    private final String source;

    //EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    //EFFECTS: reads team from file and returns it;
    //throws IOException if an error occurs reading data from file
    public Teams read() throws IOException, NumberException, NamingException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseTeams(jsonObject);
    }

    //EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }

    //EFFECTS: parses teams from JSON object and returns it
    private Teams parseTeams(JSONObject jsonObject) throws NumberException, NamingException {
        Teams teams = new Teams();
        addTeams(teams, jsonObject);
        return teams;
    }

    //MODIFIES: teams
    //EFFECTS: parses teams from JSON object and adds them to teams
    private void addTeams(Teams teams, JSONObject jsonObject) throws NumberException, NamingException {
        JSONArray jsonArray = jsonObject.getJSONArray("teams");
        for (Object json : jsonArray) {
            JSONObject nextTeam = (JSONObject) json;
            addTeam(teams, nextTeam);
        }
    }

    //MODIFIES: teams
    //EFFECTS: parses team from JSON object and adds it to teams
    private void addTeam(Teams t, JSONObject jsonObject) throws NumberException, NamingException {
        String name = jsonObject.getString("name");
        Team team = new Team(name);
        addPlayers(team, jsonObject);
        t.addTeam(team);
    }

    //MODIFIES: teams
    //EFFECTS: parses players from JSON object and adds them to teams
    private void addPlayers(Team team, JSONObject jsonObject) throws NumberException, NamingException {
        JSONArray jsonArray = jsonObject.getJSONArray("players");
        for (Object json : jsonArray) {
            JSONObject nextPlayer = (JSONObject) json;
            addPlayer(team, nextPlayer);
        }
    }

    //MODIFIES: teams
    //EFFECTS: parses player from JSON object and adds it to teams
    private void addPlayer(Team t, JSONObject jsonObject) throws NumberException, NamingException {
        String name = jsonObject.getString("name");
        String team = jsonObject.getString("team");
        int year = jsonObject.getInt("year");
        int playerGames = jsonObject.getInt("player games");
        int playerWins = jsonObject.getInt("player wins");
        int teamGames = jsonObject.getInt("team games");
        int teamWins = jsonObject.getInt("team wins");
        Player player = new Player(name, team, year, playerGames, playerWins, teamGames, teamWins);
        t.addPlayer(player);
    }
}