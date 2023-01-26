package model;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import persistence.Writable;
import org.json.JSONArray;

// The list of lists of objects, representing the second stage map before rendering graphics
// TileMap takes the first stage numerical stage of the game map GridMap and transforms it into a Tile object Map

public class TileMap implements Writable {
    private List<List<Tile>> listOfTiles;
    private int[][] boardMap;
    private String name;

    public TileMap(int[][] boardMap, String name) {
        this.boardMap = boardMap;
        this.name = name;
        int boardSize = boardMap.length;
        this.listOfTiles = new ArrayList<>();
        for (int row = 0; row < boardSize; row++) {
            List<Tile> tiles = new ArrayList<>();
            for (int col = 0; col < boardSize; col++) {
                int num = boardMap[row][col];
                addTile(tiles, num);
            }
            listOfTiles.add(tiles);
        }
    }

    public void setTile(Tile t, int row, int col) {
        int l = boardMap.length;
        if (row < 0) {
            listOfTiles.get(row + l).set(col, t);
        }
        if (col < 0) {
            listOfTiles.get(row).set(col + l, t);
        }
        if ((row < 0) && (col < 0)) {
            listOfTiles.get(row + l).set(col + l, t);
        }
        if (row >= l) {
            listOfTiles.get(row - l).set(col, t);
        }
        if (col >= l) {
            listOfTiles.get(row).set(col - l, t);
        }
        if ((row >= l) && (col >= l)) {
            listOfTiles.get(row - l).set(col - l, t);
        }
    }

    public void addTile(List<Tile> tiles, int num) {
        List<Asset> water = new ArrayList<>();
        List<Asset> trees = new ArrayList<>();
        List<Asset> forest = new ArrayList<>();
        setAssets(trees, 100);
        setAssets(forest, 1000);
        switch (num) {
            case 0:
                Tile tile0 = new Tile(50, "green", "land", trees);
                tiles.add(tile0);
                break;
            case 1:
                Tile tile1 = new Tile(50, "sky blue", "river", water);
                tiles.add(tile1);
                break;
            case 2:
                Tile tile2 = new Tile(50, "medium aqua marine", "lake", water);
                tiles.add(tile2);
                break;
            case 3:
                Tile tile3 = new Tile(50, "forest green", "mountain", forest);
                tiles.add(tile3);
                break;
        }
    }

    public void setAssets(List<Asset> assets, int treeCount) {
        Random random = new Random();
        int randomNumber = random.nextInt(11);
        for (int i = 0; i < treeCount; i++) {
            Tree tree = new Tree("tree", randomNumber + 10, 20);
            assets.add(tree);
        }
    }

    public void growTrees() {
        for (List<Tile> tiles : listOfTiles) {
            for (Tile tile : tiles) {
                List<Asset> assets = tile.getAssets();
                for (Asset asset : assets) {
                    if (asset instanceof Tree) {
                        int val = asset.getAssetValue();
                        if (val < 100) {
                            val += 20;
                            asset.setAssetValue(val);
                        }
                    }
                }
            }
        }
    }

    public void saplingToTree() {
        for (List<Tile> tiles : listOfTiles) {
            for (Tile tile : tiles) {
                List<Asset> assets = tile.getAssets();
                for (int i = 0; i < assets.size(); i++) {
                    Asset asset = assets.get(i);
                    Tree tree;
                    if (asset instanceof Sapling) {
                        int sap = asset.getAssetValue();
                        int quantity = asset.getAssetSize();
                        if (sap < 20) {
                            sap += 5;
                            asset.setAssetValue(sap);
                        } else {
                            tree = new Tree("tree", quantity, sap);
                            assets.set(i, tree);
                        }
                    }
                }
            }
        }
    }

    public void setListOfTiles(List<List<Tile>> lofT) {
        listOfTiles = lofT;
    }

    public List<List<Tile>> getListOfTiles() {
        return listOfTiles;
    }

    public Tile locateTile(int row, int col) {
        return listOfTiles.get(row).get(col);
    }

    public void setName(String naming) {
        name = naming;
    }

    public String getName() {
        return name;
    }

    public boolean houseExist() {
        for (int row = 0; row < boardMap.length; row++) {
            for (int col = 0; col < boardMap.length; col++) {
                String shape = listOfTiles.get(row).get(col).getShape();
                if (shape.equals("house")) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("mapName", name);
        json.put("boardLength", boardMap.length);
        json.put("tileJsonArray", tileMapToJson());
        json.put("1dNumMap", gridMapToJson());
        return json;
    }

    // EFFECTS: returns a Json array of all the tiles
    private JSONArray tileMapToJson() {
        JSONArray jsonArray = new JSONArray();
        for (List<Tile> m : listOfTiles) {
            for (Tile n : m) {
                jsonArray.put(n.toJson());
            }
        }

        return jsonArray;
    }

    // EFFECTS: returns a Json array of all numbers contained in boardMap
    private JSONArray gridMapToJson() {
        JSONArray jsonArray = new JSONArray();
        for (int[] row : boardMap) {
            for (int col : row) {
                jsonArray.put(col);
            }
        }
        return jsonArray;
    }
}
