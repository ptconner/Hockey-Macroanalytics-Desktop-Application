package ui;

import model.Team;
import model.Teams;

import java.awt.*;

//representation for printing teams to screen
public class TeamsPrinter extends Printer {

    //EFFECTS: constructor passes title to super
    public TeamsPrinter(Component parent) {
        super(parent,"List of Teams");
    }

    //EFFECTS: sets text of teams in log area
    public void printTeams(Teams teams) {
        for (Team t : teams.getTeams()) {
            logArea.setText(logArea.getText() + t.getName() + "\n");
        }
        repaint();
    }

    @Override
    public void setPosition(Component parent) {
        setLocation(parent.getWidth() - getWidth() - 20, parent.getHeight() - getHeight() - 20);

    }
}
