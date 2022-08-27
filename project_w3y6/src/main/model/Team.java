package model;

import exceptions.EmptyException;
import exceptions.NamingException;
import exceptions.PlayerListException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

//represents a team which has a name and a list of players
public class Team implements Writable {
    private String name;
    private final List<Player> players;

    //REQUIRES: team name not null
    //MODIFIES: this
    //EFFECTS: creates team with name and an empty list of players
    public Team(String name) throws NamingException {
        if (name.length() == 0) {
            throw new NamingException();
        }
        this.name = name;
        players = new ArrayList<>();
    }

    //MODIFIES: this
    //EFFECTS: adds given player to list of players
    public void addPlayer(Player player) {
        players.add(player);
        EventLog.getInstance().logEvent(new Event(
                "Added player " + player.getName() + " to " + name));
    }

    //REQUIRES: list is not empty
    //MODIFIES: this
    //EFFECTS: removes given player from list of players
    public void removePlayer(Player player) {
        players.remove(player);
        EventLog.getInstance().logEvent(new Event(
                "Removed player " + player.getName() + " from " + name));
    }

    //EFFECTS: throws empty exception if players is empty
    public void isEmpty() throws EmptyException {
        if (players.size() == 0) {
            throw new EmptyException();
        }
    }

    //GETTERS
    public String getName() {
        return name;
    }

    public List<Player> getPlayers() {
        return players;
    }

    //SETTERS
    public void setName(String name) throws NamingException, PlayerListException {
        if (name.length() == 0) {
            throw new NamingException();
        }
        if (name.equals("Player List")) {
            throw new PlayerListException();
        }
        this.name = name;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("players", playersToJson());
        return json;
    }

    //EFFECTS: returns players on this team as a JSON array
    private JSONArray playersToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Player p : players) {
            jsonArray.put(p.toJson());
        }
        return jsonArray;
    }
}
