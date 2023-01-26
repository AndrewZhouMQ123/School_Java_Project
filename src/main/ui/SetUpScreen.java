package ui;

import model.*;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JLabel;

// the screen during setup phase, when you start a new game
public class SetUpScreen extends JPanel {
    private JPanel panel;
    private JLabel label;

    public SetUpScreen() {
        setPreferredSize(new Dimension(StoryGame.WIDTH, StoryGame.HEIGHT));
        setBackground(Color.BLUE);
        panel = new JPanel();
        label = new JLabel("Setup Game Map");
        panel.add(label);
        label.setOpaque(true);
    }
}
