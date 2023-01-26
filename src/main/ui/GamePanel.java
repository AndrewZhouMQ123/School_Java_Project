package ui;

import model.*;

import java.awt.*;

import javax.swing.JPanel;
import java.io.IOException;

// the gameplay graphics in using JPanel
public class GamePanel extends JPanel {
    public static final Color FOREST = new Color(34, 139, 34);
    public static final Color GREEN = new Color(0, 128, 0);
    public static final Color SKY = new Color(0, 191, 255);
    public static final Color MARINE = new Color(102, 205, 170);
    public static final Color WOOD = new Color(222,184,135);
    public static final Color GOLD = new Color(255, 215, 0);
    private StoryGame game;

    // Constructs a game panel
    // effects:  sets size and background colour of panel,
    //           updates this with the game to be displayed
    public GamePanel(StoryGame g) {
        int length = g.getGridMap().getBoardSize();
        setLayout(new GridLayout(length, length, 5, 5));
        setPreferredSize(new Dimension(StoryGame.WIDTH, StoryGame.HEIGHT));
        setBackground(Color.GRAY);
        this.game = g;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        try {
            drawGame(g);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Draws the game
    // modifies: g
    // effects:  draws the game onto g
    private void drawGame(Graphics g) throws IOException {
        drawTiles(g);
        drawAssets(g);
        drawLumberjack(g);
        drawHouse(g);
    }

    // Draw the tiles
    // modifies: g
    // effects:  draws the tiles onto g
    private void drawTiles(Graphics g) {
        TileMap t = game.getTileMap();
        for (int row = 0; row < t.getListOfTiles().size(); row++) {
            for (int col = 0; col < t.getListOfTiles().get(row).size(); col++) {
                Tile next = t.getListOfTiles().get(row).get(col);
                drawTile(g, next, row, col);
            }
        }
    }

    // Draw the tile
    // modifies: g
    // effects:  draws the tile t onto g
    private void drawTile(Graphics g, Tile t, int row, int col) {
        Color savedCol = g.getColor();
        String color = t.getColor();
        switch (color) {
            case "forest green":
                g.setColor(FOREST);
                g.fillRect(col * 50 - t.getLength(), row * 50 - t.getLength(), t.getLength(), t.getLength());
                break;
            case "green":
                g.setColor(GREEN);
                g.fillRect(col * 50 - t.getLength(), row * 50 - t.getLength(), t.getLength(), t.getLength());
                break;
            case "sky blue":
                g.setColor(SKY);
                g.fillRect(col * 50 - t.getLength(), row * 50 - t.getLength(), t.getLength(), t.getLength());
                break;
            case "medium aqua marine":
                g.setColor(MARINE);
                g.fillRect(col * 50 - t.getLength(), row * 50 - t.getLength(), t.getLength(), t.getLength());
                break;
        }
        g.setColor(savedCol);
    }

    // Draws assets
    // MODIFIES: g
    // EFFECTS: Draws trees, saplings, lumbers onto g at specified row col positions
    private void drawAssets(Graphics g) throws IOException {
        TileMap t = game.getTileMap();
        for (int row = 0; row < t.getListOfTiles().size(); row++) {
            for (int col = 0; col < t.getListOfTiles().get(row).size(); col++) {
                Tile next = t.getListOfTiles().get(row).get(col);
                for (Asset asset : next.getAssets()) {
                    if (asset instanceof Tree) {
                        drawTree(g, col * 50, row * 50);
                    }
                    if (asset instanceof Sapling) {
                        drawSapling(g, col * 50, row * 50);
                    }
                    if (asset instanceof Lumber) {
                        drawLumber(g, col * 50, row * 50);
                    }
                }
            }
        }
    }

    // Draw a tree
    // MODIFIES: g
    // EFFECTS:  draws tree onto g, at its tile x, y
    private void drawTree(Graphics g, int col, int row) throws IOException {
        Color savedCol = g.getColor();
        g.setColor(FOREST);
        int[] columns = {col - 12, col + 12, col};
        int[] rows = {row + 12, row + 12, row - 12};
        g.fillPolygon(columns, rows,3);
        //final BufferedImage image = ImageIO.read(new File("./data/tree.png"));
        //g.drawImage(image, x, y, 50, 50, savedCol, null);
        g.setColor(savedCol);
    }

    // Draw a sapling
    // MODIFIES: g
    // EFFECTS:  draws the Sapling onto g
    private void drawSapling(Graphics g, int col, int row) throws IOException {
        Color savedCol = g.getColor();
        g.setColor(Color.GREEN);
        g.fillOval(col, row, 10, 10);
        //final BufferedImage image = ImageIO.read(new File("./data/sapling.png"));
        //g.drawImage(image, x, y, 10, 10, savedCol, null);
        g.setColor(savedCol);
    }

    // Draw lumber
    // MODIFIES: g
    // EFFECTS:  draws the Lumberjack i onto g
    private void drawLumber(Graphics g, int col, int row) {
        Color savedCol = g.getColor();
        g.setColor(WOOD);
        g.fill3DRect(col, row, 30, 10, true);
        g.setColor(savedCol);
    }

    // Draw a Lumberjack
    // MODIFIES: g
    // EFFECTS:  draws the Lumberjack onto g at camera x, y
    private void drawLumberjack(Graphics g) throws IOException {
        Camera camera = game.getCamera();
        Color savedCol = g.getColor();
        g.setColor(Color.yellow);
        g.fillOval(camera.getX() * 50, camera.getY() * 50, 20, 20);
        //final BufferedImage image = ImageIO.read(new File("./data/Minotaur.png"));
        //g.drawImage(image, camera.getX(), camera.getY(), 20, 40, savedCol, null);
        g.setColor(savedCol);
    }

    // Draw a house
    // MODIFIES: g
    // EFFECTS:  draws the house h onto g
    private void drawHouse(Graphics g) {
        House h = game.getHouse();
        int x = h.getHouseCol();
        int y = h.getHouseRow();
        int[] columns = {x - 10, x + 10, x, x - 10, x + 10};
        int[] rows = {y + 10, y + 10, y - 20, y - 10, y - 10};
        Color savedCol = g.getColor();
        g.setColor(GOLD);
        g.fillPolygon(columns, rows, 5);
        g.setColor(savedCol);
    }

    // Centres a string on the screen
    // MODIFIES: g
    // EFFECTS:  centres the string str horizontally onto g at vertical position yPos
    private void centreString(String str, Graphics g, FontMetrics fm, int y) {
        int width = fm.stringWidth(str);
        g.drawString(str, (StoryGame.WIDTH - width) / 2, y);
    }
}
