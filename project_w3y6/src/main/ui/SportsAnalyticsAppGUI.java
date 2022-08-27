package ui;

import exceptions.*;
import model.Event;
import model.EventLog;
import model.Player;
import model.Team;
import model.Teams;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

//Sports analytics application graphical user interface
public class SportsAnalyticsAppGUI extends JFrame {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private JDesktopPane desktop;
    private JInternalFrame controlPanel;

    private static final String JSON_STORE = "./data/savedData.json";
    private Teams teams;
    private Team playerList;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private PlayersPrinter pp;
    private TeamsPrinter tp;
    private Image tanev;

    //EFFECTS: constructor sets up button panel and initializes player list, teams, jsonWriter and jsonReader
    public SportsAnalyticsAppGUI() throws NamingException {
        super("Sports Analytics App GUI");

        setDesktopAndBackground();
        controlPanel = new JInternalFrame("Options", false, false, false, false);
        controlPanel.setLayout(new BorderLayout());
        setContentPane(desktop);
        setTitle("Sports Analytics App");
        setSize(WIDTH, HEIGHT);
        addButtonPanel();
        controlPanel.pack();
        controlPanel.setVisible(true);
        desktop.add(controlPanel);
        centreOnScreen();
        setVisible(true);
        playerList = new Team("Player List");
        teams = new Teams();
        teams.addTeam(playerList);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    //EFFECTS: helper to add control buttons
    private void addButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(11, 1));
        buttonPanel.add(new JButton(new CreateTeamAction()));
        buttonPanel.add(new JButton(new ViewTeamAction()));
        buttonPanel.add(new JButton(new EditTeamAction()));
        buttonPanel.add(new JButton(new DeleteTeamAction()));
        buttonPanel.add(new JButton(new CreatePlayerAction()));
        buttonPanel.add(new JButton(new ViewPlayerAction()));
        buttonPanel.add(new JButton(new CopyPlayerAction()));
        buttonPanel.add(new JButton(new DeletePlayerAction()));
        buttonPanel.add(new JButton(new SaveToFileAction()));
        buttonPanel.add(new JButton(new LoadFromFileAction()));
        buttonPanel.add(new JButton(new QuitAction()));
        controlPanel.add(buttonPanel, BorderLayout.WEST);
    }

    //EFFECTS: helper to add keypad to main application window
    private void centreOnScreen() {
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        setLocation((width - getWidth()) / 2, (height - getHeight()) / 2);
    }

    //MODIFIES: this
    //EFFECTS: creates a new team
    private class CreateTeamAction extends AbstractAction implements ActionListener {
        CreateTeamAction() {
            super("Create Team");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            String name = takeUserInput("Team Name", "Please enter team name");
            if (name.equals("Player List")) {
                try {
                    throw new PlayerListException();
                } catch (PlayerListException e) {
                    printErrorMessage(e, "Player List Exception");
                }
            }
            try {
                Team team = new Team(name);
                teams.addTeam(team);
            } catch (NamingException e) {
                printErrorMessage(e, "Naming Exception");
            }
        }
    }

    //EFFECTS: displays list of teams to user
    private class ViewTeamAction extends AbstractAction implements ActionListener {
        ViewTeamAction() {
            super("View Teams");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            tp = new TeamsPrinter(SportsAnalyticsAppGUI.this);
            desktop.add(tp);
            tp.printTeams(teams);
        }
    }

    //MODIFIES: this
    //EFFECTS: edits an existing team
    private class EditTeamAction extends AbstractAction implements ActionListener {
        EditTeamAction() {
            super("Edit Team");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            Team selectedTeam;
            try {
                selectedTeam = selectTeam();
                String name = takeUserInput("Edit Team", "Please enter new team name:");
                selectedTeam.setName(name);
            } catch (NamingException e) {
                printErrorMessage(e, "Naming Exception");
            } catch (PlayerListException e) {
                printErrorMessage(e, "Player List Exception");
            } catch (DoesNotExistExemption e) {
                printErrorMessage(e, "Does not exist exemption");
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: deletes an existing team
    private class DeleteTeamAction extends AbstractAction implements ActionListener {
        DeleteTeamAction() {
            super("Delete Team");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            Team selectedTeam;
            try {
                selectedTeam = selectTeam();
                teams.deleteTeam(selectedTeam);
            } catch (PlayerListException e) {
                printErrorMessage(e, "Player List Exception");
            } catch (NamingException e) {
                printErrorMessage(e, "Naming Exception");
            } catch (DoesNotExistExemption e) {
                printErrorMessage(e, "Does not exist exemption");
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: selects a team and adds a new player
    private class CreatePlayerAction extends AbstractAction implements ActionListener {
        CreatePlayerAction() {
            super("Create Player");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            String name = takeUserInput("Player name", "Enter player name:");
            String team = takeUserInput("Player Team", "Enter player's team:");
            int year = Integer.parseInt(takeUserInput("Year", "Enter year:"));
            int playerGamesPlayed = Integer.parseInt(takeUserInput("Player Games Played",
                    "Enter player games played:"));
            int playerWins = Integer.parseInt(takeUserInput("Player Wins", "Enter player wins:"));
            int teamGamesPlayed = Integer.parseInt(takeUserInput("Team Games Played", "Enter team games played:"));
            int teamWins = Integer.parseInt(takeUserInput("Team Wins", "Enter team wins:"));
            Player newPlayer;
            try {
                newPlayer = new Player(name, team, year, playerGamesPlayed, playerWins, teamGamesPlayed, teamWins);
                playerList.addPlayer(newPlayer);
                if (teams.getSize() > 1) {
                    Team selectedTeam = selectTeam();
                    selectedTeam.addPlayer(newPlayer);
                }
            } catch (NumberException e) {
                printErrorMessage(e, "Number Exception");
            } catch (NamingException e) {
                printErrorMessage(e, "Naming Exception");
            } catch (DoesNotExistExemption e) {
                printErrorMessage(e, "Does not exist exemption");
            }
        }
    }

    //EFFECTS: displays a list of players and their statistics on a team
    private class ViewPlayerAction extends AbstractAction implements ActionListener {
        ViewPlayerAction() {
            super("View Players");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            try {
                Team selectedTeam = selectTeam();
                pp = new PlayersPrinter(SportsAnalyticsAppGUI.this, selectedTeam);
                desktop.add(pp);
                pp.printPlayers(selectedTeam);
            } catch (NamingException e) {
                printErrorMessage(e, "Naming Exception");
            } catch (DoesNotExistExemption e) {
                printErrorMessage(e, "Does not exist exemption");
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: copies a player from one team to another
    private class CopyPlayerAction extends AbstractAction implements ActionListener {
        CopyPlayerAction() {
            super("Copy Player");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            try {
                Team fromTeam = selectTeam();
                fromTeam.isEmpty();
                Player copiedPlayer = selectPlayer(fromTeam);
                Team toTeam = selectTeam();
                toTeam.addPlayer(copiedPlayer);
            } catch (NamingException e) {
                printErrorMessage(e, "Naming Exception");
            } catch (EmptyException e) {
                printErrorMessage(e, "Empty Exception");
            } catch (NumberException e) {
                printErrorMessage(e, "Number Exception");
            } catch (DoesNotExistExemption e) {
                printErrorMessage(e, "Does not exist exemption");
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: deletes the first instance of a player from a team
    private class DeletePlayerAction extends AbstractAction implements ActionListener {
        DeletePlayerAction() {
            super("Delete Player");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            Team selectedTeam;
            try {
                selectedTeam = selectTeam();
                Player selectedPlayer = selectPlayer(selectedTeam);
                for (Player p : selectedTeam.getPlayers()) {
                    if (p == selectedPlayer) {
                        selectedTeam.removePlayer(p);
                        return;
                    }
                }
            } catch (NamingException e) {
                printErrorMessage(e, "Naming Exception");
            } catch (NumberException e) {
                printErrorMessage(e, "Number Exception");
            } catch (DoesNotExistExemption e) {
                printErrorMessage(e, "Does not exist exemption");
            }
        }
    }

    //EFFECTS: save the team to file
    private class SaveToFileAction extends AbstractAction implements ActionListener {
        SaveToFileAction() {
            super("Save");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            try {
                int result = JOptionPane.showConfirmDialog(
                        desktop,
                        "Would you like to save?",
                        "Save",
                        JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    jsonWriter.open();
                    jsonWriter.write(teams);
                    jsonWriter.close();
                }
            } catch (FileNotFoundException e) {
                printErrorMessage(e, "Unable to write to file: " + JSON_STORE);
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: loads teams from file
    private class LoadFromFileAction extends AbstractAction implements ActionListener {
        LoadFromFileAction() {
            super("Load");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            try {
                int result = JOptionPane.showConfirmDialog(
                        desktop,
                        "Would you like to load?",
                        "Load",
                        JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    teams = jsonReader.read();
                }
            } catch (IOException e) {
                printErrorMessage(e, "Unable to read from file: " + JSON_STORE);
            } catch (NumberException e) {
                printErrorMessage(e, "Number Exception");
            } catch (NamingException e) {
                printErrorMessage(e, "Naming Exception");
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: loads teams from file
    private class QuitAction extends AbstractAction implements ActionListener {
        QuitAction() {
            super("Quit");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            int result = JOptionPane.showConfirmDialog(
                    desktop,
                    "Would you like to quit?",
                    "Quit",
                    JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                for (Event e : EventLog.getInstance()) {
                    System.out.println(e);
                }
                dispose();
            }
        }
    }

    //EFFECT: helper that prompts user to select team and returns it
    public Team selectTeam() throws NamingException, DoesNotExistExemption {
        Team selectedTeam = new Team("temp");
        String selectedTeamName = takeUserInput("Select Team", "Please select a team");
        for (Team t : teams.getTeams()) {
            if (t.getName().equals(selectedTeamName)) {
                selectedTeam = t;
            }
        }
        if (selectedTeam.getName().equals("temp")) {
            throw new DoesNotExistExemption();
        }
        return selectedTeam;
    }

    //EFFECT: helper that prompts user to select player and returns it
    public Player selectPlayer(Team fromTeam) throws NumberException, NamingException, DoesNotExistExemption {
        Player selectedPlayer = new Player("temp", "temp", 0,
                0, 0, 0, 0);
        String selectedPlayerName = takeUserInput("Select Player", "Please select a player");
        for (Player p : fromTeam.getPlayers()) {
            if (p.getName().equals((selectedPlayerName))) {
                selectedPlayer = p;
            }
        }
        if (selectedPlayer.getName().equals("temp")) {
            throw new DoesNotExistExemption();
        }
        return selectedPlayer;
    }

    //EFFECTS: helper that returns string from GUI user input
    private String takeUserInput(String title, String message) {
        return JOptionPane.showInputDialog(null,
                message,
                title,
                JOptionPane.QUESTION_MESSAGE);
    }

    //EFFECTS: helper that prints error message
    private void printErrorMessage(Exception e, String title) {
        JOptionPane.showMessageDialog(null, e.getMessage(), title, JOptionPane.ERROR_MESSAGE);
    }

    //EFFECTS: helper that initializes desktop pane with background
    private void setDesktopAndBackground() {
        ImageIcon img = new ImageIcon("images/tanev.jpg");
        tanev = img.getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH);
        desktop = new JDesktopPane() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(tanev, 0, 0, this);
            }
        };
    }
}