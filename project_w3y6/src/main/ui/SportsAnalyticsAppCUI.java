package ui;

import exceptions.EmptyException;
import exceptions.NamingException;
import exceptions.NumberException;
import exceptions.PlayerListException;
import model.Player;
import model.Team;
import model.Teams;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.lang.System;

//Sports analytics application
public class SportsAnalyticsAppCUI {
    private static final String JSON_STORE = "./data/savedData.json";
    Scanner userInput;
    private Teams teams;
    private Team playerList;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    //EFFECTS: runs the sports analytics application
    public SportsAnalyticsAppCUI() throws FileNotFoundException, NumberException, NamingException, PlayerListException {
        runSportsAnalytics();
    }

    //MODIFIES: this
    //EFFECTS: processes user input
    private void runSportsAnalytics() throws NumberException, NamingException, PlayerListException {
        boolean quit = false;
        String command;

        init();

        while (!quit) {
            displayMenu();
            command = this.userInput.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                quit = true;
            } else {
                processCommand(command);
            }
        }
        System.out.println("\nGoodbye!");
    }

    //MODIFIES: this
    //EFFECTS: initializes team
    private void init() throws NamingException, PlayerListException {
        userInput = new Scanner(System.in);
        userInput.useDelimiter("\n");
        playerList = new Team("Player List");
        teams = new Teams();
        teams.addTeam(playerList);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    //MODIFIES: this
    //EFFECTS: processes user userInput
    @SuppressWarnings("methodlength")
    private void processCommand(String userInput) throws NumberException, NamingException, PlayerListException {
        switch (userInput) {
            case "ct":
                createTeam();
                break;
            case "vt":
                viewTeams();
                break;
            case "et":
                editTeam();
                break;
            case "dt":
                deleteTeam();
                break;
            case "cp":
                createPlayer();
                break;
            case "vp":
                viewPlayers();
                break;
            case "ccp":
                copyPlayer();
                break;
            case "dp":
                deletePlayer();
                break;
            case "s":
                saveTeams();
                break;
            case "l":
                loadTeams();
                break;
        }
    }

    //EFFECTS: displays full team menu options to user
    private void displayMenu() {
        System.out.println("\nWhat would you like to do?:");
        System.out.println();
        System.out.println("\tct -> create team");
        System.out.println("\tvt -> view teams");
        System.out.println("\tet -> edit team");
        System.out.println("\tdt -> delete team");
        System.out.println();
        System.out.println("\tcp -> create player");
        System.out.println("\tvp -> view players");
        System.out.println("\tccp -> copy player");
        System.out.println("\tdp -> delete player");
        System.out.println();
        System.out.println("\ts -> save to file");
        System.out.println("\tl -> load from file");
        System.out.println();
        System.out.println("\tq -> quit");
    }

    //EFFECTS: displays list of teams to user
    private void viewTeams() {
        System.out.println("Here's a list of your teams:");
        System.out.println();
        for (Team t : teams.getTeams()) {
            System.out.println(t.getName());
        }
    }

    //EFFECTS: displays a list of players and their statistics on a team
    private void viewPlayers() throws NamingException, PlayerListException {
        System.out.println("Please enter which team to display:");
        Team selectedTeam = selectTeam();
        System.out.println("Here's a list of " + selectedTeam.getName() + "'s players:");
        System.out.println();
        for (Player p : selectedTeam.getPlayers()) {
            System.out.println(p.returnStats());
        }
    }

    //MODIFIES: this
    //EFFECTS: creates a new team
    private void createTeam() {
        System.out.println("Please enter the new team's name:");
        String name = userInput.next();
        Team newTeam;
        try {
            newTeam = new Team(name);
            teams.addTeam(newTeam);
            System.out.println("Team created successfully.");
        } catch (NamingException e) {
            System.out.println("ERROR: name can't be empty!");
        }
    }

    //MODIFIES: this
    //EFFECTS: edits an existing team
    private void editTeam() {
        System.out.println("Please enter the name of the team to edit:");
        Team selectedTeam;
        try {
            selectedTeam = selectTeam();
            if (selectedTeam.getName().equals("Player List")) {
                System.out.println("ERROR: Player List can not be edited");
            } else {
                System.out.println("Please enter the team's new name:");
                String name = userInput.next();
                selectedTeam.setName(name);
                System.out.println("Team edited successfully.");
            }
        } catch (NamingException e) {
            System.out.println("ERROR: name can't be empty!");
        } catch (PlayerListException e) {
            System.out.println("ERROR: Player List can't be modified.");
        }
    }

    //MODIFIES: this
    //EFFECTS: deletes an existing team
    private void deleteTeam() throws NamingException {
        System.out.println("Please enter the name of team to be deleted:");
        Team selectedTeam;
        try {
            selectedTeam = selectTeam();
            teams.deleteTeam(selectedTeam);
            System.out.println("Team deleted successfully.");
        } catch (PlayerListException e) {
            System.out.println("ERROR: Player List cannot be deleted");
        }
    }

    //MODIFIES: this
    //EFFECTS: selects a team and adds a new player
    @SuppressWarnings("methodlength")
    private void createPlayer() throws PlayerListException {
        System.out.println("Enter player name");
        String name = userInput.next();
        System.out.println("Enter player's team:");
        String team = userInput.next();
        System.out.println("Enter year:");
        int year = userInput.nextInt();
        System.out.println("Enter player games played:");
        int playerGamesPlayed = userInput.nextInt();
        System.out.println("Enter player wins:");
        int playerWins = userInput.nextInt();
        System.out.println("Enter team games played:");
        int teamGamesPlayed = userInput.nextInt();
        System.out.println("Enter team wins:");
        int teamWins = userInput.nextInt();

        Player newPlayer;
        try {
            newPlayer = new Player(name, team, year, playerGamesPlayed, playerWins, teamGamesPlayed, teamWins);

            if (teams.getSize() == 1) {
                playerList.addPlayer(newPlayer);
            } else {
                playerList.addPlayer(newPlayer);
                System.out.println("Enter team to add player to:");
                Team selectedTeam = selectTeam();
                selectedTeam.addPlayer(newPlayer);
            }
            System.out.println("Player created successfully.");
        } catch (NamingException e) {
            System.out.println("ERROR: name/team can't be empty!");
        } catch (NumberException e) {
            System.out.println("ERROR: games/wins can't be negative!");
        }
    }

    //MODIFIES: this
    //EFFECTS: deletes the first instance of a player from a team
    private void deletePlayer() throws NumberException, NamingException, PlayerListException {
        System.out.println("Please enter team to remove player from:");
        Team selectedTeam = selectTeam();
        try {
            selectedTeam.isEmpty();
            System.out.println("Please enter name of player to delete:");
            Player selectedPlayer = selectPlayer(selectedTeam);
            for (Player p : selectedTeam.getPlayers()) {
                if (p == selectedPlayer) {
                    selectedTeam.removePlayer(p);
                    System.out.println("Player deleted successfully.");
                    return;
                }
            }
        } catch (EmptyException e) {
            System.out.println("ERROR: selected team is empty!");
        }
    }

    //MODIFIES: this
    //EFFECTS: copies a player from one team to another
    private void copyPlayer() throws NumberException, NamingException, PlayerListException {
        try {
            System.out.println("Please enter team to copy player from:");
            Team fromTeam = selectTeam();
            fromTeam.isEmpty();
            System.out.println("Please enter name of player to copy:");
            Player copiedPlayer = selectPlayer(fromTeam);
            System.out.println("Please enter name of team to copy to:");
            Team toTeam = selectTeam();
            toTeam.addPlayer(copiedPlayer);
            System.out.println("Player copied successfully.");
        } catch (EmptyException e) {
            System.out.println("ERROR: selected team is empty!");
        }
    }

    //EFFECT: prompts user to select team and returns it
    private Team selectTeam() throws NamingException, PlayerListException {
        Team selectedTeam = new Team("temp");
        while (selectedTeam.getName().equals("temp")) {
            String selectedTeamName = userInput.next();
            for (Team t : teams.getTeams()) {
                if (t.getName().equals(selectedTeamName)) {
                    selectedTeam = t;
                }
            }
        }
        return selectedTeam;
    }

    //EFFECT: prompts user to select player and returns it
    private Player selectPlayer(Team fromTeam) throws NumberException, NamingException {
        Player selectedPlayer = new Player("temp", "temp", 0,
                0, 0, 0, 0);
        while (selectedPlayer.getName().equals("temp")) {
            String selectedPlayerName = userInput.next();
            for (Player p : fromTeam.getPlayers()) {
                if (p.getName().equals((selectedPlayerName))) {
                    selectedPlayer = p;
                }
            }
        }
        return selectedPlayer;
    }

    //EFFECTS save the team to file
    private void saveTeams() {
        try {
            jsonWriter.open();
            jsonWriter.write(teams);
            jsonWriter.close();
            System.out.println("Saved " + teams.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    //MODIFIES: this
    //EFFECTS: loads teams from file
    private void loadTeams() throws NumberException, NamingException, PlayerListException {
        try {
            teams = jsonReader.read();
            System.out.println("Loaded " + teams.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
