package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

// Represents a StoryGame having stored all the in game data and game state
public class StoryGame implements Writable {
    public static final int WIDTH = 600;
    public static final int HEIGHT = 600;
    private String name;
    private TileMap tileMap;
    private GridMap gridMap;
    private House house;
    private Lumberjack lumberjack;
    private Camera camera;
    private int homeCol;
    private int homeRow;

    // EFFECTS: constructs story game
    public StoryGame(String name, int mapSize) {
        this.name = name;
        this.gridMap = new GridMap(mapSize);
        this.camera = new Camera(gridMap);
        this.tileMap = new TileMap(gridMap.getBoard(), name);
        this.homeCol = mapSize / 2;
        this.homeRow = mapSize / 2;
        List<Asset> assets = new ArrayList<>();
        this.house = new House(5, homeRow, homeCol, assets);
        this.lumberjack = new Lumberjack(500, name, new LinkedList<>());
    }

    public Camera getCamera() {
        return camera;
    }

    public String getName() {
        return name;
    }

    public GridMap getGridMap() {
        return gridMap;
    }

    public TileMap getTileMap() {
        return tileMap;
    }

    public void setHomeCol(int hc) {
        homeCol = hc;
    }

    public void setHomeRow(int hr) {
        homeRow = hr;
    }

    public Lumberjack getLumberjack() {
        return lumberjack;
    }

    public void setLumberjack(Lumberjack jack) {
        lumberjack = jack;
    }

    public int getHomeCol() {
        return homeCol;
    }

    public int getHomeRow() {
        return homeRow;
    }

    public void setHouse(House home) {
        house = home;
    }

    public void setTileMap(TileMap tileMap1) {
        tileMap = tileMap1;
    }

    public House getHouse() {
        return house;
    }

    // MODIFIES: camera
    // EFFECTS: if user input quit, exits game loop, else go through game loop methods
    public void update(int col, int row) {
        getTileMap().growTrees();
        getTileMap().saplingToTree();
        camera.setCenter(col, row);
        camera.updateCam();
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("gameName", name);
        json.put("homeCol", homeCol);
        json.put("homeRow", homeRow);
        json.put("lumberjack", jackToJson());
        json.put("tileMap", tileMapToJson());
        return json;
    }

    // EFFECTS: returns things in this workroom as a JSON array
    private JSONObject tileMapToJson() {
        return tileMap.toJson();
    }

    private JSONObject jackToJson() {
        return lumberjack.toJson();
    }
}
