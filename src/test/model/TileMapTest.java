package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TileMapTest {
    private GridMap map;
    private Camera camera;
    private TileMap tileMap;
    private House house;
    private List<Asset> loa;
    private StoryGame storyGame;
    private Lumberjack lumberjack;

    @BeforeEach
    void runBefore() {
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
        // check that house exists in tileMap
    void checkHouseExist() {
        assertTrue(storyGame.getTileMap().houseExist());
        map = new GridMap(30);
        camera = new Camera(map);
        tileMap = new TileMap(map.getBoard(), "Canada");
        lumberjack = new Lumberjack(600, "bob", new LinkedList<>());
        storyGame = new StoryGame("bob", map.getBoardSize());
        assertFalse(storyGame.getTileMap().houseExist());
    }

    @Test
        // set ListOfTiles, then get ListOfTiles
    void checkListOfTiles() {
        List<List<Tile>> listOfTiles = new ArrayList<>();
        tileMap.setListOfTiles(listOfTiles);
        assertIterableEquals(listOfTiles, tileMap.getListOfTiles());
    }

    @Test
        // set TileMap name, then get name
    void checkTileMapName() {
        tileMap.setName("Canada");
        assertEquals("Canada", tileMap.getName());
    }

    @Test
        // locating Tile test
    void locateTileTest() {
        Tile tile0 = tileMap.getListOfTiles().get(5).get(5);
        Tile tile1 = tileMap.locateTile(5, 5);
        assertEquals(tile1, tile0);
    }

    @Test
        // locate tile then getter setter
    void locateTileGetSet() {
        int col = camera.getX();
        int row = camera.getY();
        Tile tile = tileMap.locateTile(row, col);
        tile.setLength(10);
        tile.setColor("red");
        tile.setShape("doughnut");
        List<Asset> loa = new ArrayList<>();
        Asset a = new Asset("tree", 4, 40);
        loa.add(a);
        tile.setAssets(loa);
        assertEquals(10, tile.getLength());
        assertEquals("red", tile.getColor());
        assertEquals("doughnut", tile.getShape());
        assertEquals(loa, tile.getAssets());

    }

    @Test
        // testing tileMap sapling growth method
    void saplingGrowth() {
        Sapling sapling = new Sapling("sapling", 10, 5);
        lumberjack.addToInventory(sapling);
        lumberjack.plantSapling(storyGame, 5, 5);
        List<Asset> assets = storyGame.getTileMap().getListOfTiles().get(5).get(5).getAssets();
        assertTrue(assets.contains(sapling));
        for (int i = 0; i < 5; i++) {
            storyGame.getTileMap().saplingToTree();
            for (Asset asset : assets) {
                if (asset instanceof Sapling) {
                    String type = asset.getType();
                    int quantity = asset.getAssetSize();
                    int val = asset.getAssetValue();
                    System.out.println(type + "    " + quantity + "    " + val);
                }
            }
        }
        assertFalse(storyGame.getTileMap().getListOfTiles().get(5).get(5).getAssets().contains(sapling));
    }

    @Test
        // testing tileMap tree growth method
    void treeGrowth() {
        int val = 0;
        Tree tree = new Tree("tree", 10, 20);
        List<Asset> assets = storyGame.getTileMap().getListOfTiles().get(5).get(5).getAssets();
        assets.add(tree);
        assertTrue(assets.contains(tree));
        for (int i = 0; i < 5; i++) {
            storyGame.getTileMap().growTrees();
            for (Asset asset : assets) {
                if (asset instanceof Tree) {
                    String type = asset.getType();
                    int quantity = asset.getAssetSize();
                    val = asset.getAssetValue();
                    System.out.println(type + "    " + quantity + "    " + val);
                }
            }
        }
        assertTrue(val == 100);
    }
}
