package ui;

import model.StoryGame;
import model.Lumberjack;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;

// JPanel Store Menu
public class GameStoreMenu extends JPanel {
    private static final String MENU_TXT = "q -> open / close main menu";
    private static final String BUYS_TXT = "1 - > Buy Saplings" + "($";
    private static final String SELLS_TXT = "2 - > Sell Saplings at half price" + "($";
    private static final String BUYW_TXT = "3 -> buy lumber" + "($";
    private static final String SELLW_TXT = "4 -> sell lumber" + "($";
    private static final String WALLET_TXT = "\tYour wallet: ";
    private static final int LBL_WIDTH = 250;
    private static final int LBL_HEIGHT = 100;
    private static final int returnVal = 5 / 2;
    private static final int buyVal = 20 * 2;
    private static final int sapAssetVal = 5;
    private static final int logAssetVal = 20;
    private JLabel menuLbl;
    private JLabel buysLbl;
    private JLabel sellsLbl;
    private JLabel buyWLbl;
    private JLabel sellWLbl;
    private JLabel walletLbl;

    // EFFECTS: shows the store menu options in a JPanel
    public GameStoreMenu(int money) {
        setLayout(new GridLayout(6, 1));
        setBackground(new Color(245, 222, 179));
        menuLbl = new JLabel(MENU_TXT);
        menuLbl.setPreferredSize(new Dimension(LBL_WIDTH, LBL_HEIGHT));
        buysLbl = new JLabel(BUYS_TXT + sapAssetVal + ") per sapling");
        buysLbl.setPreferredSize(new Dimension(LBL_WIDTH, LBL_HEIGHT));
        sellsLbl = new JLabel(SELLS_TXT + returnVal + ") per sapling");
        sellsLbl.setPreferredSize(new Dimension(LBL_WIDTH, LBL_HEIGHT));
        buyWLbl = new JLabel(BUYW_TXT + buyVal + ") per log");
        buyWLbl.setPreferredSize(new Dimension(LBL_WIDTH, LBL_HEIGHT));
        sellWLbl = new JLabel(SELLW_TXT + logAssetVal + ") per log");
        sellWLbl.setPreferredSize(new Dimension(LBL_WIDTH, LBL_HEIGHT));
        walletLbl = new JLabel(WALLET_TXT + money);
        walletLbl.setPreferredSize(new Dimension(LBL_WIDTH, LBL_HEIGHT));
        adder();
    }

    //
    public void adder() {
        List<JLabel> labels = new ArrayList<>();
        labels.add(menuLbl);
        labels.add(buysLbl);
        labels.add(sellsLbl);
        labels.add(buyWLbl);
        labels.add(sellWLbl);
        labels.add(walletLbl);

        for (JLabel label : labels) {
            label.setPreferredSize(new Dimension(LBL_WIDTH, LBL_HEIGHT));
            add(label);
            add(Box.createHorizontalStrut(1));
        }
    }

    // Updates the store panel
    // modifies: this
    // effects:  updates money in wallet
    //           remaining to reflect current state of game
    public void update(int money) {
        walletLbl.setText(WALLET_TXT + money);
        repaint();
    }
}
