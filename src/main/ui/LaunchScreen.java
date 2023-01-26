package ui;

import model.*;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JLabel;

// the screen that shows up when you launch the game
public class LaunchScreen extends JPanel {
    private JPanel panel;
    private JLabel label;

    public LaunchScreen() {
        setPreferredSize(new Dimension(StoryGame.WIDTH, StoryGame.HEIGHT));
        setBackground(Color.CYAN);
        panel = new JPanel();
        label = new JLabel("F6 to load saved game, press anything else to continue");
        panel.add(label);
        label.setOpaque(true);
    }
}
