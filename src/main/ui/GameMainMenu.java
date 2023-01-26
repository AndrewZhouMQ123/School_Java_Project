package ui;

import model.*;

import java.awt.*;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;

// JPanel Main Menu
public class GameMainMenu extends JPanel {
    private static final String MENU_TXT = "tab -> open / close main menu";
    private static final String SAVE_TXT = "5 -> save story game to file";
    private static final String LOAD_TXT = "6 -> load story game from file";
    private static final String MAP_TXT = "shift -> print the map";
    private static final String QUIT_TXT = "esc -> quit story game";
    private static final int LBL_WIDTH = 150;
    private static final int LBL_HEIGHT = 30;
    private JLabel menuLbl;
    private JLabel saveLbl;
    private JLabel loadLbl;
    private JLabel mapLbl;
    private JLabel quitLbl;

    // EFFECTS: displays menu of main menu options to user
    public GameMainMenu() {
        setBackground(new Color(153, 50, 204));
        menuLbl = new JLabel(MENU_TXT);
        menuLbl.setPreferredSize(new Dimension(LBL_WIDTH, LBL_HEIGHT));
        saveLbl = new JLabel(SAVE_TXT);
        saveLbl.setPreferredSize(new Dimension(LBL_WIDTH, LBL_HEIGHT));
        loadLbl = new JLabel(LOAD_TXT);
        loadLbl.setPreferredSize(new Dimension(LBL_WIDTH, LBL_HEIGHT));
        mapLbl = new JLabel(MAP_TXT);
        mapLbl.setPreferredSize(new Dimension(LBL_WIDTH, LBL_HEIGHT));
        quitLbl = new JLabel(QUIT_TXT);
        quitLbl.setPreferredSize(new Dimension(LBL_WIDTH, LBL_HEIGHT));
        add(menuLbl);
        add(Box.createVerticalStrut(1));
        add(saveLbl);
        add(Box.createVerticalStrut(1));
        add(loadLbl);
        add(Box.createVerticalStrut(1));
        add(mapLbl);
        add(Box.createVerticalStrut(1));
        add(quitLbl);
    }

}
