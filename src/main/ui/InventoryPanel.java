package ui;

import model.Asset;
import model.StoryGame;
import model.Lumberjack;
import model.Lumber;
import model.Sapling;

import java.awt.*;
import java.util.LinkedList;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;

// lumberjack's inventory
public class InventoryPanel extends JPanel {
    private static final int LBL_WIDTH = 100;
    private static final int LBL_HEIGHT = 100;
    private Lumberjack lumberjack;
    private String name;
    private int money;
    private LinkedList<Asset> inventory;
    private JLabel saplingLbl;
    private JLabel lumberLbl;
    private JLabel walletLbl;
    private int mint = 0;
    private int nint = 0;

    public InventoryPanel(StoryGame g) {
        setLayout(new GridLayout(5, 1));
        setBackground(new Color(245, 222, 179));
        lumberjack = g.getLumberjack();
        inventory = lumberjack.getInventory();
        money = lumberjack.getMoney();
        name = lumberjack.getName();
        saplingLbl = new JLabel("Sapling: " + mint);
        saplingLbl.setPreferredSize(new Dimension(LBL_WIDTH, LBL_HEIGHT));
        lumberLbl = new JLabel("Lumber: " + nint);
        lumberLbl.setPreferredSize(new Dimension(LBL_WIDTH, LBL_HEIGHT));
        walletLbl = new JLabel("money: " + money);
        walletLbl.setPreferredSize(new Dimension(LBL_WIDTH, LBL_HEIGHT));
        add(saplingLbl);
        add(Box.createHorizontalStrut(1));
        add(lumberLbl);
        add(Box.createHorizontalStrut(1));
        add(walletLbl);
    }

    public void updateSapling() {
        for (Asset a: inventory) {
            if (a instanceof Sapling) {
                int val = a.getAssetSize();
                mint += val;
            }
        }
    }

    public void updateLumber() {
        for (Asset a: inventory) {
            if (a instanceof Lumber) {
                int val = a.getAssetSize();
                nint += val;
            }
        }
    }

    // Updates the inventory panel
    // modifies: this
    // effects:  updates money in wallet
    //           remaining to reflect current state of game
    public void update(int money) {
        walletLbl.setText("money:" + money);
        lumberLbl.setText("Lumber: " + nint);
        saplingLbl.setText("Sapling: " + mint);
        repaint();
    }
}
