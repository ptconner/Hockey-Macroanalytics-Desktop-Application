package ui;

import model.Player;
import model.Team;

import java.awt.*;

//representation for printing teams to screen
public class PlayersPrinter extends Printer {

    //EFFECTS: constructor passes title to super
    public PlayersPrinter(Component parent, Team team) {
        super(parent,team.getName() + "'s Players:");
    }

    //EFFECTS: sets text of players in log area
    public void printPlayers(Team team) {
        for (Player p : team.getPlayers()) {
            logArea.setText(logArea.getText() + p.returnStats() + "\n");
        }
        repaint();
    }

    @Override
    public void setPosition(Component parent) {
        setLocation(parent.getWidth() - getWidth() - 20, parent.getHeight() - getHeight() - 300);

    }
}