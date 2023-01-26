package ui;

import model.*;
import java.util.List;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TilePanel extends JPanel {
    private static final int LBL_WIDTH = 40;
    private static final int LBL_HEIGHT = 40;
    private Camera cam;
    private Tile tile;
    private int len;
    private int[] size;
    private int[] value;
    private String[] type;
    private String color;
    private String shape;
    private List<Asset> assets;
    private JLabel shapeLbl;
    private JLabel colorLbl;
    private JLabel lenLbl;
    private JLabel assetsLbl;

    public TilePanel(StoryGame sg) {
        setBackground(new Color(200, 170,40));
        cam = sg.getCamera();
        int col = cam.getX();
        int row = cam.getY();
        tile = sg.getTileMap().getListOfTiles().get(row).get(col);
        len = tile.getLength();
        assets = tile.getAssets();
        shape = tile.getShape();
        color = tile.getColor();
        processAssets();
        tileLabel();
    }

    public void processAssets() {
        size = new int[assets.size()];
        value = new int[assets.size()];
        type = new String[assets.size()];
        for (int i = 0; i < assets.size(); i++) {
            Asset asset = assets.get(i);
            size[i] = asset.getAssetSize();
            value[i] = asset.getAssetValue();
            type[i] = asset.getType();
        }
    }

    public void tileLabel() {
        shapeLbl = new JLabel("Shape: " + shape);
        shapeLbl.setPreferredSize(new Dimension(LBL_WIDTH, LBL_HEIGHT));
        colorLbl = new JLabel("Color: " + color);
        colorLbl.setPreferredSize(new Dimension(LBL_WIDTH, LBL_HEIGHT));
        lenLbl = new JLabel("Length: " + len);
        lenLbl.setPreferredSize(new Dimension(LBL_WIDTH, LBL_HEIGHT));
        assetsLbl = new JLabel("Types:" + type + "\n Sizes: " + size + "\n Values: " + type);
        assetsLbl.setPreferredSize(new Dimension(LBL_WIDTH, LBL_HEIGHT));
        add(shapeLbl);
        add(Box.createHorizontalStrut(1));
        add(colorLbl);
        add(Box.createHorizontalStrut(1));
        add(lenLbl);
        add(Box.createHorizontalStrut(1));
        add(assetsLbl);
    }

    public void update(StoryGame sg) {
        cam = sg.getCamera();
        int col = cam.getX();
        int row = cam.getY();
        tile = sg.getTileMap().getListOfTiles().get(row).get(col);
        len = tile.getLength();
        assets = tile.getAssets();
        shape = tile.getShape();
        color = tile.getColor();
    }

}
