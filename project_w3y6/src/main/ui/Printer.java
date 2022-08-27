package ui;

import javax.swing.*;
import java.awt.*;

//defines behaviors that printers must support
public abstract class Printer extends JInternalFrame {
    protected static final int WIDTH = 400;
    protected static final int HEIGHT = 300;
    protected JTextArea logArea;

    //EFFECTS: constructor sets up window to print to screen
    public Printer(Component parent, String title) {
        super(title, false, true, false, false);
        logArea = new JTextArea();
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane((logArea));
        add(scrollPane);
        setSize(WIDTH, HEIGHT);
        setPosition(parent);
        setVisible(true);
    }

    //EFFECTS: sets position of printable
    public abstract void setPosition(Component parent);
}
