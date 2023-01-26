package model;

import java.util.List;

// The House is a child class of tile, which allows the player to designate one of the tiles
// on the map as their house, which they can drop off and store lumber at
// it also serves as a point to teleport to, in case any game breaking bug occurs, and they cannot move

public class House extends Tile {
    private int houseSize;
    private int[][] house;
    private int houseRow;
    private int houseCol;
    private List<Asset> assets;

    // REQUIRES: integer, boardSize >= 5
    // MODIFIES: boardSize, board
    // EFFECTS: initializes board with 0

    public House(int houseSize, int row, int col, List<Asset> assets) {
        super(houseSize, "yellow", "house", assets);
        this.houseSize = houseSize;
        this.house = new int[houseSize][houseSize];
        this.houseRow = row;
        this.houseCol = col;
        this.assets = assets;
        for (int y = 0; y < house.length; y++) {
            for (int x = 0; x < house.length; x++) {
                house[y][x] = 0;
            }
        }
    }

    public int getHouseSize() {
        return houseSize;
    }

    public int getHouseRow() {
        return houseRow;
    }

    public int getHouseCol() {
        return houseCol;
    }

    public List<Asset> getStorage() {
        return assets;
    }
}
