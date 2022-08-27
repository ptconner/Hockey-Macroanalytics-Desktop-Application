package model;

import exceptions.PlayerListException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

//represents teams which has a list of team and a name
public class Teams implements Writable {
    private final List<Team> teams;
    private String name;

    //MODIFIES: this
    //EFFECTS: creates teams an empty list of team
    public Teams() {
        teams = new ArrayList<>();
        name = "List of Teams";
    }

    //MODIFIES: this
    //EFFECTS: adds team to list of team
    public void addTeam(Team team) {
        teams.add(team);
    }

    //MODIFIES: this
    //EFFECTS: removes team from list of teams
    public void deleteTeam(Team team) throws PlayerListException {
        if (team.getName().equals("Player List")) {
            throw new PlayerListException();
        }
        teams.remove(team);
    }

    //GETTERS
    public int getSize() {
        return teams.size();
    }

    public List<Team> getTeams() {
        return teams;
    }

    public String getName() {
        return name;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("teams", teamsToJson());
        return json;
    }

    //EFFECTS: returns teams on this teams as a JSON array
    private JSONArray teamsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Team t : teams) {
            jsonArray.put(t.toJson());
        }
        return jsonArray;
    }
}
