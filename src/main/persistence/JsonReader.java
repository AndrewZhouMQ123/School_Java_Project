package persistence;

import model.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.stream.Stream;
import java.util.List;

import org.json.*;

// Represents a reader that reads Story Game from JSON data stored in file
public class JsonReader {
    private String source;
    private GridMap gridMap;
    private TileMap tileMap;
    private Lumberjack lumberjack;
    private String gameName;
    private int homeCol;
    private int homeRow;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads Story Game from file and returns it
    // throws IOException if an error occurs reading data from file
    public StoryGame read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        parseStoryGame(jsonObject);
        return new StoryGame(gameName, gridMap.getBoardSize());
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses StoryGame from JSON object and returns it
    private void parseStoryGame(JSONObject jsonObject) {
        this.gameName = jsonObject.getString("gameName");
        this.homeRow = jsonObject.getInt("homeRow");
        this.homeCol = jsonObject.getInt("homeCol");
        parseLumberJack(jsonObject.getJSONObject("lumberjack"));
        parseTileMap(jsonObject.getJSONObject("tileMap"));
    }

    private void parseTileMap(JSONObject jsonObject) {
        String mapName = jsonObject.getString("mapName");
        int boardLength = jsonObject.getInt("boardLength");
        gridMap = new GridMap(boardLength);
        int[][] board = gridMap.getBoard();
        tileMap = new TileMap(gridMap.getBoard(), mapName);
        JSONArray jsonNumArray = (JSONArray) jsonObject.get("1dNumMap");
        int i = 0;
        for (int row = 0; row < boardLength; row++) {
            for (int col = 0; col < boardLength; col++) {
                i += 1;
                board[row][col] = jsonNumArray.getInt(i - 1);
            }
        }
        int j = 0;
        JSONArray jsonArray = jsonObject.getJSONArray("tileJsonArray");
        for (Object json : jsonArray) {
            j += 1;
            JSONObject nextTile = (JSONObject) json;
            parseTile(nextTile, j);
        }
    }

    private void parseLumberJack(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int money = jsonObject.getInt("money");
        JSONArray jsonArray = jsonObject.getJSONArray("inventory");
        LinkedList<Asset> inventory = new LinkedList<>();
        for (Object json : jsonArray) {
            JSONObject nextAsset = (JSONObject) json;
            parseAsset(nextAsset, inventory);
        }
        lumberjack = new Lumberjack(money, name, inventory);
    }

    private void parseTile(JSONObject jsonObject, int count) {
        String color = jsonObject.getString("color");
        String shape = jsonObject.getString("shape");
        int length = jsonObject.getInt("length");
        JSONArray jsonArray = jsonObject.getJSONArray("assets");
        List<Asset> assets = new ArrayList<>();
        for (Object json : jsonArray) {
            JSONObject nextAsset = (JSONObject) json;
            parseAsset(nextAsset, assets);
        }
        if (shape.equals("house")) {
            House house = new House(length, homeRow, homeCol, assets);
            setHouse(homeRow, homeCol, house);
        } else {
            Tile tile = new Tile(length, color, shape, assets);
            int row = 0;
            if (count % gridMap.getBoardSize() == (gridMap.getBoardSize()) - 1) {
                row += 1;
            }
            tileMap.setTile(tile, row, count % gridMap.getBoardSize());
        }
    }

    private void setHouse(int row, int col, House house) {
        int l = gridMap.getBoard().length;
        if (row < 0) {
            tileMap.getListOfTiles().get(row + l).set(col, house);
        }
        if (col < 0) {
            tileMap.getListOfTiles().get(row).set(col + l, house);
        }
        if ((row < 0) && (col < 0)) {
            tileMap.getListOfTiles().get(row + l).set(col + l, house);
        }
        if (row >= l) {
            tileMap.getListOfTiles().get(row - l).set(col, house);
        }
        if (col >= l) {
            tileMap.getListOfTiles().get(row).set(col - l, house);
        }
        if ((row >= l) && (col >= l)) {
            tileMap.getListOfTiles().get(row - l).set(col - l, house);
        }
    }

    private void parseAsset(JSONObject jsonObject, List<Asset> assets) {
        String type = jsonObject.getString("type");
        int assetSize = jsonObject.getInt("assetSize");
        int assetValue = jsonObject.getInt("assetValue");
        Asset asset = new Asset(type, assetSize, assetValue);
        assets.add(asset);
    }
}