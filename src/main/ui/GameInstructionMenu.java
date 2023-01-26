package ui;

import model.*;


import java.awt.*;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.util.ArrayList;
import java.util.List;

// Instruction Menu for how to play the game
public class GameInstructionMenu extends JPanel {
    private static final String MENU_TXT = "return/enter -> open / close Game instruction menu";
    private static final String DIR_TXT = "use arrow keys to move around";
    private static final String ACTION_TXT = "space bar -> chop down 1 tree \n r -> plant sapling";
    private static final String ASSET_TXT = "i -> open inventory \n o -> observe tile properties";
    private static final String PICK_DROP_TXT = "p -> pick up wood on tile \n d -> drop wood on tile";
    private static final String HOME_TXT = "b -> build house, maximum 1";
    private static final String TELEPORT_TXT = "h -> teleport to house";
    private static final String DEFOREST_TXT = "f -> deforest";

    private static final int LBL_WIDTH = 150;
    private static final int LBL_HEIGHT = 30;
    private JLabel menuLbl;
    private JLabel dirLbl;
    private JLabel actionLbl;
    private JLabel assetLbl;
    private JLabel pickDropLbl;
    private JLabel homeLbl;
    private JLabel teleportLbl;
    private JLabel forestLbl;

    // EFFECTS: displays menu of main menu options to user
    public GameInstructionMenu() {
        setBackground(new Color(153, 50, 204));
        menuLbl = new JLabel(MENU_TXT);
        dirLbl = new JLabel(DIR_TXT);
        actionLbl = new JLabel(ACTION_TXT);
        assetLbl = new JLabel(ASSET_TXT);
        pickDropLbl = new JLabel(PICK_DROP_TXT);
        homeLbl = new JLabel(HOME_TXT);
        teleportLbl = new JLabel(TELEPORT_TXT);
        forestLbl = new JLabel(DEFOREST_TXT);
        List<JLabel> labels = new ArrayList<>();
        labels.add(menuLbl);
        labels.add(dirLbl);
        labels.add(actionLbl);
        labels.add(assetLbl);
        labels.add(pickDropLbl);
        labels.add(homeLbl);
        labels.add(teleportLbl);
        labels.add(forestLbl);
        for (JLabel label : labels) {
            label.setPreferredSize(new Dimension(LBL_WIDTH, LBL_HEIGHT));
            add(label);
            add(Box.createVerticalStrut(1));
        }
    }
}
