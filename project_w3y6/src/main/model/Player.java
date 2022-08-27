package model;

import exceptions.NamingException;
import exceptions.NumberException;
import org.json.JSONObject;
import persistence.Writable;

//represents a player which has a name, team, year, player games, player wins team games and team wins
public class Player implements Writable {
    private String name;
    private String team;
    private int year;
    private int playerGames;
    private int playerWins;
    private int teamGames;
    private int teamWins;
    private double playerScore;
    private double playerAdv;
    private double teamScore;

    //MODIFIES: this
    //EFFECTS: creates player with name, team, year, player games, player
    //  wins, team games, team wins, playerScore, playerAdv and teamScore
    public Player(String name, String team, int year, int playerGames,
                  int playerWins, int teamGames, int teamWins) throws NamingException, NumberException {
        if (name.length() == 0 || team.length() == 0) {
            throw new NamingException();
        }
        if (playerGames < 0 || playerWins < 0 || teamWins < 0 || teamGames < 0) {
            throw new NumberException();
        }
        this.name = name;
        this.team = team;
        this.year = year;
        this.playerGames = playerGames;
        this.playerWins = playerWins;
        this.teamGames = teamGames;
        this.teamWins = teamWins;
        playerScore = 0.0;
        playerAdv = 0.0;
        teamScore = 0.0;
        runCalc();
    }

    //MODIFIES: this
    //EFFECTS: calculates "teamScore", "playerScore" and "playerAdv"
    public void runCalc() {
        int teamLosses = teamGames - teamWins;
        int playerLosses = playerGames - playerWins;
        teamScore = teamWins - teamLosses;
        int winMinus = teamWins - playerWins;
        int lossPlus = teamLosses - playerLosses;
        playerScore = playerWins - playerLosses - winMinus + lossPlus;
        playerAdv = playerScore - teamScore;
    }

    //MODIFIES: this
    //EFFECTS: edits an existing player's fields
    public void editPlayer(String name, String team, int year, int playerGames,
                           int playerWins, int teamGames, int teamWins) throws NamingException, NumberException {
        if (name.length() == 0 || team.length() == 0) {
            throw new NamingException();
        }
        if (playerGames < 0 || playerWins < 0 || teamWins < 0 || teamGames < 0) {
            throw new NumberException();
        }
        this.name = name;
        this.team = team;
        this.year = year;
        this.playerGames = playerGames;
        this.playerWins = playerWins;
        this.teamGames = teamGames;
        this.teamWins = teamWins;
        playerScore = 0.0;
        playerAdv = 0.0;
        teamScore = 0.0;
        runCalc();
    }

    //EFFECTS: returns a players stats
    public String returnStats() {
        return name + ", " + team + ", " + year + " -- "
                + "GP: " + playerGames
                + ", Score: " + playerScore
                + ", Advantage: " + playerAdv;
    }

    //GETTERS
    public String getName() {
        return name;
    }

    public String getTeam() {
        return team;
    }

    public int getYear() {
        return year;
    }

    public int getPlayerGames() {
        return playerGames;
    }

    public int getPlayerWins() {
        return playerWins;
    }

    public int getTeamGames() {
        return teamGames;
    }

    public int getTeamWins() {
        return teamWins;
    }

    public double getPlayerScore() {
        return playerScore;
    }

    public double getPlayerAdv() {
        return playerAdv;
    }

    public double getTeamScore() {
        return teamScore;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("team", team);
        json.put("year", year);
        json.put("player games", playerGames);
        json.put("player wins", playerWins);
        json.put("team games", teamGames);
        json.put("team wins", teamWins);
        json.put("player score", playerScore);
        json.put("player adv", playerAdv);
        json.put("team score", teamScore);
        return json;
    }
}