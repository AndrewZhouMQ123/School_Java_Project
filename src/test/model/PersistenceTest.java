package model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PersistenceTest {
    private static final String JSON_STORE = "./data/testPersistence.json";
    private GridMap map;
    private Camera camera;
    private TileMap tileMap;
    private House house;
    private List<Asset> loa;
    private StoryGame storyGame;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private Lumberjack lumberjack;

    @BeforeEach
    void runBefore() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        map = new GridMap(30);
        camera = new Camera(map);
        tileMap = new TileMap(map.getBoard(), "Brazil");
        loa = new ArrayList<>();
        Asset a = new Asset("wood", 4, 80);
        loa.add(a);
        house = new House(42, 4, 2, loa);
        lumberjack = new Lumberjack(500, "Aba", new LinkedList<>());
        storyGame = new StoryGame("aba", map.getBoardSize());
        storyGame.setHouse(house);
        storyGame.getTileMap().getListOfTiles().get(6).set(5, house);
    }

    @Test
        // asset toJson test
    void checkAssetToJson() {
        Asset asset = new Asset("paper", 10, 8);
        assertTrue(asset.toJson() instanceof JSONObject);
        JSONObject jsonObject = asset.toJson();
        String type = jsonObject.getString("type");
        int assetSize = jsonObject.getInt("assetSize");
        int assetValue = jsonObject.getInt("assetValue");
        assertEquals("paper", type);
        assertEquals(10, assetSize);
        assertEquals(8, assetValue);
    }

    @Test
        // Tile toJson test
    void checkTileToJson() {
        List<Asset> assets = new ArrayList<Asset>();
        Asset asset = new Asset("paper", 10, 8);
        assets.add(asset);
        Tile tile = new Tile(8, "red", "oval",  assets);
        JSONObject jsonObject = tile.toJson();
        String color = jsonObject.getString("color");
        String shape = jsonObject.getString("shape");
        int length = jsonObject.getInt("length");
        JSONArray jsonArray = jsonObject.getJSONArray("assets");
        assertEquals("red", color);
        assertEquals("oval", shape);
        assertEquals(8, length);
    }

    @Test
        // TileMap toJson test
    void checkTileMapToJson() {
        GridMap gridMap = new GridMap(10);
        TileMap tileMap1 = new TileMap(gridMap.getBoard(), "UBC");
        JSONObject jsonObject = tileMap1.toJson();
        String name = jsonObject.getString("mapName");
        int boardLength = jsonObject.getInt("boardLength");
        assertEquals("UBC", name);
        assertEquals(10, boardLength);
        JSONArray jsonArray = jsonObject.getJSONArray("tileJsonArray");
        GridMap gridMap2 = new GridMap(boardLength);
        int[][] board = gridMap2.getBoard();
        TileMap tileMap2 = new TileMap(gridMap2.getBoard(), name);
        JSONArray jsonNumArray = (JSONArray) jsonObject.get("1dNumMap");
        int i = 0;
        for (int row = 0; row < boardLength; row++) {
            for (int col = 0; col < boardLength; col++) {
                i += 1;
                board[row][col] = jsonNumArray.getInt(i - 1);
            }
        }
        assertArrayEquals(gridMap.getBoard(), board);
        int count = 0;
        for (Object json : jsonArray) {
            count += 1;
            JSONObject nextTile = (JSONObject) json;
            String color = nextTile.getString("color");
            String shape = nextTile.getString("shape");
            int length = nextTile.getInt("length");
            JSONArray jsonAssets = nextTile.getJSONArray("assets");
            List<Asset> assets = new ArrayList<>();
            for (Object jsone : jsonAssets) {
                JSONObject nextAsset = (JSONObject) jsone;
                String type = nextAsset.getString("type");
                int assetSize = nextAsset.getInt("assetSize");
                int assetValue = nextAsset.getInt("assetValue");
                Asset asset = new Asset(type, assetSize, assetValue);
                assets.add(asset);
            }
            Tile tile = new Tile (length, color, shape, assets);
            int row = 0;
            if (count % gridMap.getBoardSize() == (gridMap.getBoardSize()) - 1) {
                row += 1;
            }
            tileMap2.setTile(tile, row, count % gridMap.getBoardSize());
        }
        for (int row = 0; row < boardLength; row++) {
            for (int col = 0; col < boardLength; col++) {
                Tile t1 = tileMap1.getListOfTiles().get(row).get(col);
                Tile t2 = tileMap2.getListOfTiles().get(row).get(col);
                assertEquals(t1.getLength(), t2.getLength());
            }
        }
    }

    @Test
        // StoryGame toJson test
    void checkStoryGameToJson() {
        GridMap gridMap1 = new GridMap(10);
        TileMap tileMap1 = new TileMap(gridMap1.getBoard(), "UBC");
        Lumberjack abba = new Lumberjack(500, "abba", new LinkedList<>());
        StoryGame storyGame1 = new StoryGame("abba", gridMap1.getBoardSize());
        JSONObject jsonObject = storyGame1.toJson();
        String name = jsonObject.getString("gameName");
        assertEquals("abba", name);
        int hc = jsonObject.getInt("homeCol");
        assertEquals(5, hc);
        int hr = jsonObject.getInt("homeRow");
        assertEquals(5, hr);
        JSONObject jsonAbba = jsonObject.getJSONObject("lumberjack");
        JSONObject jsonTileMap = jsonObject.getJSONObject("tileMap");
    }

    @Test
        // LumberJack toJson test
    void checkLumberJackToJson() {
        LinkedList<Asset> assets = new LinkedList<>();
        Asset a = new Asset("apple", 5, 5);
        Asset b = new Asset("bacon", 5, 5);
        Asset c = new Asset("cheese", 5, 5);
        Lumberjack abba = new Lumberjack(500, "abba", assets);
        abba.addToInventory(a);
        abba.addToInventory(b);
        abba.addToInventory(c);
        abba.setMoney(450);
        abba.setName("Baba");
        JSONObject jsonObject = abba.toJson();
        String name = jsonObject.getString("name");
        int money = jsonObject.getInt("money");
        assertEquals("Baba", name);
        assertEquals(450, money);
        JSONArray jsonInventory = jsonObject.getJSONArray("inventory");
        LinkedList<Asset> listOfAsset = new LinkedList<>();
        for (Object json : jsonInventory) {
            JSONObject nextAsset = (JSONObject) json;
            String type = nextAsset.getString("type");
            int assetSize = nextAsset.getInt("assetSize");
            int assetValue = nextAsset.getInt("assetValue");
            Asset asset = new Asset(type, assetSize, assetValue);
            listOfAsset.add(asset);
        }
        for (int i = 0; i < 2; i++) {
            Asset alpha = assets.get(i);
            Asset beta = listOfAsset.get(i);
            assertEquals(alpha.getType(), beta.getType());
            assertEquals(alpha.getAssetSize(), beta.getAssetSize());
            assertEquals(alpha.getAssetValue(), beta.getAssetValue());
        }
    }

    @Test
        // camera outside gridMap bounds
    void checkOutLengthCamera() {
        camera.setCenter(29, 29);
        camera.updateCam();
        camera.setCenter(30, 30);
        camera.updateCam();
        camera.setCenter(-1, -1);
        camera.updateCam();
        camera.setCenter(0, 0);
        camera.updateCam();
    }

    @Test
        // persistence testing methods
    void persistenceMethods() {
        try {
            House boundHouse1 = new House(10, map.getBoardSize() + 1, map.getBoardSize() + 1, new ArrayList<>());
            storyGame.setHouse(boundHouse1);
            jsonWriter.open();
            jsonWriter.write(storyGame);
            jsonWriter.close();
            System.out.println("Saved" + storyGame.getName() + "to" + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file:" + JSON_STORE);
        }
        try {
            jsonWriter = new JsonWriter(JSON_STORE);
            jsonReader = new JsonReader(JSON_STORE);
            storyGame = jsonReader.read();
            map = storyGame.getGridMap();
            lumberjack = storyGame.getLumberjack();
            int homeCol = storyGame.getHomeCol();
            int homeRow = storyGame.getHomeRow();
            camera = new Camera(map);
            camera.setCenter(homeCol, homeRow);
            camera.updateCam();
            camera.genCam();
            System.out.println("Loaded " + storyGame.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}