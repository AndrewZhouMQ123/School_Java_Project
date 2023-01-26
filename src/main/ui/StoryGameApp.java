package ui;

import model.*;
import model.Event;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.PlainDocument;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

// Represents the application's main window frame and game loop
public class StoryGameApp extends JFrame implements WindowListener, PropertyChangeListener {
    protected JPanel labelPane;
    protected JPanel fieldPane;
    private static final int INTERVAL = 10;
    private static final String JSON_STORE = "./data/StoryGame.json";
    private Camera camera;
    private GridMap map;
    private Lumberjack lumberjack;
    private StoryGame storyGame;
    private GamePanel gp;
    private GameMainMenu gmm;
    private GameInstructionMenu gim;
    private GameStoreMenu gsm;
    private InventoryPanel ip;
    private TilePanel tp;
    private MapPanel mp;
    private LaunchScreen lc;
    private SetUpScreen sus;
    private KeyHandler keyListenerI;
    private KeyHandler2 keyListenerII;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private House house;
    private int homeCol;
    private int homeRow;

    // MODIFIES: jsonWriter, jsonReader, lc, keyListenerI
    // EFFECTS: constructor for StoryGameApp, setup JPanel in the JFrame
    public StoryGameApp() throws FileNotFoundException {
        super("Lumberjack Simulator");
        setVisible(false);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(false);
        setResizable(false);
        lc = new LaunchScreen();
        add(lc);
        keyListenerI = new KeyHandler();
        addKeyListener(keyListenerI);
        pack();
        centreOnScreen();
        setVisible(true);
    }

    // Set up timer
    // MODIFIES: none
    // EFFECTS:  initializes a timer that updates game each
    //           INTERVAL milliseconds
    private void addTimer() {
        Timer t = new Timer(INTERVAL, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                int row = camera.getY();
                int col = camera.getX();
                storyGame.update(col, row);
                gp.repaint();
                int money = lumberjack.getMoney();
                gsm.update(money);
                ip.update(money);
                tp.update(storyGame);
            }
        });

        t.start();
    }

    // Centres frame on desktop
    // MODIFIES: this
    // EFFECTS:  location of frame is set so frame is centred on desktop
    private void centreOnScreen() {
        Dimension scrn = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((scrn.width - getWidth()) / 2, (scrn.height - getHeight()) / 2);
    }

    public void windowOpened(WindowEvent e) {
    }

    public void windowClosing(WindowEvent e) {
    }

    public void windowClosed(WindowEvent e) {
    }

    public void windowIconified(WindowEvent e) {
    }

    public void windowDeiconified(WindowEvent e) {
    }

    public void windowActivated(WindowEvent e) {
    }

    public void windowDeactivated(WindowEvent e) {
    }

    public void propertyChange(PropertyChangeEvent evt) {
    }

    // keyHandler for only the launch screen
    private class KeyHandler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            launchScreenCommands(e.getKeyCode());
        }
    }

    // REQUIRES: keyCode
    // EFFECTS: press f6 to load game in launch screen, press anything else to start new game
    private void launchScreenCommands(int keyCode) {
        if (keyCode == KeyEvent.VK_6) {
            loadStoryGame();
            centreOnScreen();
        } else {
            setVisible(false);
            remove(lc);
            removeKeyListener(keyListenerI);
            sus = new SetUpScreen();
            add(sus);
            pack();
            centreOnScreen();
            setVisible(true);
            gameNameInput();
        }
    }

    private void gameNameInput() {
        labelPane = new JPanel();
        fieldPane = new JPanel();
        labelPane.setPreferredSize(new Dimension(50, 20));
        fieldPane.setPreferredSize(new Dimension(50, 20));
        JLabel labelName = new JLabel("enter map name: ");
        JTextField textField = new JTextField(20);
        textField.setEditable(true);
        labelPane.add(labelName);
        fieldPane.add(textField);
        labelName.setLabelFor(textField);
        add(labelPane, BorderLayout.CENTER);
        add(fieldPane, BorderLayout.LINE_END);
        pack();
        centreOnScreen();
        String mapName = textField.getText();
        setUp(mapName);
    }

    // REQUIRES: gameName String
    // MODIFIES: map, lumberjack, storyGame, camera
    // EFFECTS: takes gameName and mapSize from text field
    // constructs storyGame, camera, map, lumberjack, calls gameloop
    private void setUp(String gameName) {
        remove(labelPane);
        remove(fieldPane);
        JLabel jlabel = new JLabel("Enter an integer mapSize: ");
        JTextField textField = new JTextField(10);
        labelPane = new JPanel();
        fieldPane = new JPanel();
        fieldPane.add(textField);
        labelPane.add(jlabel);
        jlabel.setLabelFor(textField);
        PlainDocument doc = (PlainDocument) textField.getDocument();
        doc.setDocumentFilter(new MyIntFilter());
        JOptionPane.showMessageDialog(null, fieldPane);
        add(labelPane, BorderLayout.CENTER);
        add(fieldPane, BorderLayout.LINE_END);
        int mapSize = Integer.parseInt(textField.getText());
        storyGame = new StoryGame(gameName, mapSize);
        camera = storyGame.getCamera();
        map = storyGame.getGridMap();
        lumberjack = storyGame.getLumberjack();
        camera.setCenter(mapSize / 2, mapSize / 2);
        camera.updateCam();
        gameLoop();
    }

    // MODIFIES: gp, gmm, gim, gsm, ip, keyListenerII;
    // EFFECTS: set up game panel, runs the game in a loop
    private void gameLoop() {
        remove(labelPane);
        remove(fieldPane);
        gp = new GamePanel(storyGame);
        gmm = new GameMainMenu();
        gim = new GameInstructionMenu();
        int money = lumberjack.getMoney();
        gsm = new GameStoreMenu(money);
        ip = new InventoryPanel(storyGame);
        tp = new TilePanel(storyGame);
        keyListenerII = new KeyHandler2();
        addTimer();
        remove(sus);
        add(gp, BorderLayout.CENTER);
        add(gmm, BorderLayout.NORTH);
        add(gim, BorderLayout.SOUTH);
        add(gsm, BorderLayout.WEST);
        add(ip, BorderLayout.EAST);
        addKeyListener(keyListenerII);
        pack();
        centreOnScreen();
    }

    // keyHandler to be used in gameplay
    private class KeyHandler2 extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            mainMenuCommands(e.getKeyCode());
        }
    }

    private void mainMenuCommands(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_ESCAPE:
                pullThePlug();
            case KeyEvent.VK_SHIFT:
                map.genMap();
                break;
            case KeyEvent.VK_5:
                saveStoryGame();
                break;
            case KeyEvent.VK_6:
                loadStoryGame();
                break;
        }
        lumberJackControl(keyCode);
    }

    public void printEventLog() {
        for (Event next : EventLog.getInstance()) {
            System.out.println(next);
        }
    }

    // EFFECTS: Quits the game
    public void pullThePlug() {
        // this will make sure WindowListener.windowClosing() et al. will be called.
        WindowEvent wev = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);

        // this will hide and dispose the frame, so that the application quits by
        // itself if there is nothing else around.
        setVisible(false);
        dispose();
        printEventLog();
        // if you have other similar frames around, you should dispose them, too.
        // finally, call this to really exit.
        // i/o libraries such as WiiRemoteJ need this.
        // also, this is what swing does for JFrame.EXIT_ON_CLOSE
        System.exit(0);
    }

    // Controls the lumberjack
    // MODIFIES: this, camera
    // EFFECTS: moves lumberjack around in response to key code
    // we move the camera around, and draw lumberjack where camera is
    // if not movement, pass keyCode to lumberJackAction
    private void lumberJackControl(int keyCode) {
        int x = camera.getX();
        int y = camera.getY();
        switch (keyCode) {
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_KP_RIGHT:
                camera.setCenter(x + 1, y);
                break;
            case KeyEvent.VK_UP:
            case KeyEvent.VK_KP_UP:
                camera.setCenter(x, y - 1);
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_KP_DOWN:
                camera.setCenter(x, y + 1);
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_KP_LEFT:
                camera.setCenter(x - 1, y);
                break;
        }
        lumberJackAction(keyCode);
    }

    private void lumberJackAction(int keyCode) {
        int x = camera.getX();
        int y = camera.getY();
        switch (keyCode) {
            case KeyEvent.VK_F:
                lumberjack.deForest(storyGame, x, y);
                ip.updateLumber();
                break;
            case KeyEvent.VK_D:
                lumberjack.drop(storyGame, x, y);
                break;
            case KeyEvent.VK_P:
                lumberjack.pick(storyGame, x, y);
                break;
            case KeyEvent.VK_R:
                lumberjack.plantSapling(storyGame, x, y);
                ip.updateSapling();
                break;
            case KeyEvent.VK_SPACE:
                lumberjack.cutDownOneTree(storyGame, x, y);
                ip.updateLumber();
                break;
        }
        lumberjackDoing(keyCode);
    }

    private void lumberjackDoing(int keyCode) {
        int x = camera.getX();
        int y = camera.getY();
        switch (keyCode) {
            case KeyEvent.VK_O:
                lumberjack.observeTile(storyGame, x, y);
                // tile panel hide / show
                break;
            case KeyEvent.VK_I:
                if (ip.isShowing()) {
                    remove(ip);
                    pack();
                } else {
                    add(ip, BorderLayout.EAST);
                    pack();
                }
                break;
        }
        fnumCommands(keyCode);
    }

    private void fnumCommands(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_TAB:
                if (gmm.isShowing()) {
                    remove(gmm);
                    pack();
                } else {
                    add(gmm, BorderLayout.NORTH);
                    pack();
                }
                break;
            case KeyEvent.VK_ENTER:
                if (gim.isShowing()) {
                    remove(gim);
                    pack();
                } else {
                    add(gim, BorderLayout.SOUTH);
                    pack();
                }
                break;
        }
        storeCommand(keyCode);
        tileCommand(keyCode);
    }

    // EFFECTS: buy sapling, sell all sapling, buy lumber, sell all lumber
    private void storeCommand(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_Q:
                if (gsm.isShowing()) {
                    remove(gsm);
                    pack();
                } else {
                    add(gsm, BorderLayout.WEST);
                    pack();
                }
                break;
            case KeyEvent.VK_1:
                buySapling();
                break;
            case KeyEvent.VK_2:
                sellAllSapling();
                break;
            case KeyEvent.VK_3:
                buyLumber();
                break;
            case KeyEvent.VK_4:
                sellAllLumber();
                break;
        }
    }

    private void tileCommand(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_H:
                homeCallTeleport();
                break;
            case KeyEvent.VK_B:
                buildHouse();
                break;
            case KeyEvent.VK_T:
                if (tp.isShowing()) {
                    remove(tp);
                    pack();
                } else {
                    add(tp, BorderLayout.WEST);
                    pack();
                }
        }
    }

    // MODIFIES: lumberjack
    // EFFECTS: buys sapling, deducts money accordingly
    private void buySapling() {
        System.out.println("How many saplings do you want?");
        int numOfSapling = 1;
        Sapling sapling = new Sapling("sapling", numOfSapling, 5);
        int money = lumberjack.getMoney();
        int newMoney = (money - numOfSapling * 5);
        if (newMoney >= 0) {
            lumberjack.setMoney(newMoney);
            lumberjack.addToInventory(sapling);
            ip.updateSapling();
        } else {
            System.out.println("Not enough money.");
        }
        EventLog.getInstance().logEvent(new Event("buy sapling"));
        System.out.println("Have a nice day!");
    }

    // MODIFIES: lumberjack
    // EFFECTS: buys lumber, deducts money accordingly
    private void buyLumber() {
        System.out.println("How many logs do you want?");
        int numOfLumber = 1;
        Lumber lumber = new Lumber("lumber", numOfLumber, 20);
        int money = lumberjack.getMoney();
        int newMoney = (money - numOfLumber * 20 * 2);
        if (newMoney >= 0) {
            lumberjack.setMoney(newMoney);
            lumberjack.addToInventory(lumber);
            ip.updateLumber();
        }
        EventLog.getInstance().logEvent(new Event("buy lumber"));
        System.out.println("Have a nice day!");
    }

    // MODIFIES: lumberjack
    // EFFECTS: sell all lumber in lumberjack inventory
    private void sellAllLumber() {
        System.out.println("Selling all lumber.");
        lumberjack.sellAllLumber();
        ip.updateLumber();
        System.out.println("You now have $" + lumberjack.getMoney());
    }

    // MODIFIES: lumberjack
    // EFFECTS: sell all sapling in lumberjack inventory
    private void sellAllSapling() {
        System.out.println("Selling all sapling.");
        lumberjack.sellAllSapling();
        ip.updateSapling();
        System.out.println("You now have $" + lumberjack.getMoney());
    }

    // EFFECTS: set tile in given x, y coordinates to House, maximum 1, permanent
    private void buildHouse() {
        if (getTileMap().houseExist()) {
            return;
        }
        tryHouse();
    }

    // MODIFIES: homeCol, homeRow, storyGame, house
    // EFFECTS: build the house
    private void tryHouse() {
        JLabel jlabel = new JLabel("Enter an integer houseSize: ");
        JTextField textField = new JTextField(10);
        labelPane = new JPanel();
        fieldPane = new JPanel();
        fieldPane.add(textField);
        labelPane.add(jlabel);
        jlabel.setLabelFor(textField);
        PlainDocument doc = (PlainDocument) textField.getDocument();
        doc.setDocumentFilter(new MyIntFilter());
        JOptionPane.showMessageDialog(null, fieldPane);
        add(labelPane, BorderLayout.CENTER);
        add(fieldPane, BorderLayout.LINE_END);
        int houseSize = Integer.parseInt(textField.getText());
        homeCol = camera.getX();
        homeRow = camera.getY();
        storyGame.setHomeCol(homeCol);
        storyGame.setHomeRow(homeRow);
        List<Asset> assets = new ArrayList<>();
        house = new House(houseSize, homeRow, homeCol, assets);
        storyGame.setHouse(house);
        setHouse();
    }

    // EFFECTS: set location of House
    private void setHouse() {
        EventLog.getInstance().logEvent(new Event("built house"));
        remove(labelPane);
        remove(fieldPane);
        int l = map.getBoard().length;
        if (homeRow < 0) {
            getTileMap().getListOfTiles().get(homeRow + l).set(homeCol, house);
        }
        if (homeCol < 0) {
            getTileMap().getListOfTiles().get(homeRow).set(homeCol + l, house);
        }
        if (homeRow >= l) {
            getTileMap().getListOfTiles().get(homeRow - l).set(homeCol, house);
        }
        if (homeCol >= l) {
            getTileMap().getListOfTiles().get(homeRow).set(homeCol - l, house);
        }
        if ((homeRow >= l) && (homeCol >= l)) {
            getTileMap().getListOfTiles().get(homeRow - l).set(homeCol - l, house);
        }
    }

    // EFFECTS: saves game
    private void saveStoryGame() {
        try {
            jsonWriter.open();
            jsonWriter.write(storyGame);
            jsonWriter.close();
            System.out.println("Saved" + storyGame.getName() + "to" + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file:" + JSON_STORE);
        }
    }

    // EFFECTS: loads game
    private void loadStoryGame() {
        try {
            jsonWriter = new JsonWriter(JSON_STORE);
            jsonReader = new JsonReader(JSON_STORE);
            storyGame = jsonReader.read();
            gp = new GamePanel(storyGame);
            gmm = new GameMainMenu();
            gim = new GameInstructionMenu();
            int money = lumberjack.getMoney();
            gsm = new GameStoreMenu(money);
            ip = new InventoryPanel(storyGame);
            tp = new TilePanel(storyGame);
            keyListenerII = new KeyHandler2();
            remove(lc);
            removeKeyListener(keyListenerI);
            add(gp);
            add(gmm, BorderLayout.PAGE_START);
            add(gim, BorderLayout.PAGE_END);
            add(gsm, BorderLayout.LINE_START);
            addKeyListener(keyListenerII);
            pack();
            //System.out.println("Loaded " + storyGame.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // EFFECTS: teleport to house
    private void homeCallTeleport() {
        int homeRow = storyGame.getHouse().getHouseRow();
        int homeCol = storyGame.getHouse().getHouseCol();
        camera.setCenter(homeCol, homeRow);
        EventLog.getInstance().logEvent(new Event("teleport lumberjack"));
        System.out.println("you have arrived at (" + homeCol + "," + homeRow + ")!");
        camera.updateCam();
    }

    // EFFECTS: gets the tileMap from storyGame
    private TileMap getTileMap() {
        return storyGame.getTileMap();
    }
}
