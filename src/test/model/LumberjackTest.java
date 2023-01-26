package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LumberjackTest {
    private GridMap map;
    private Camera camera;
    private TileMap tileMap;
    private StoryGame storyGame;
    private Lumberjack lumberjack;

    @BeforeEach
    void runBefore() {
        map = new GridMap(30);
        camera = new Camera(map);
        tileMap = new TileMap(map.getBoard(), "Brazil");
        lumberjack = new Lumberjack(500, "Aba", new LinkedList<>());
        storyGame = new StoryGame("aba", map.getBoardSize());
    }

    @Test
        // testing lumberjack setter getter
    void checkLumberYack() {
        lumberjack = new Lumberjack(600, "bob", new LinkedList<>());
        lumberjack.setName("meme");
        assertEquals("meme", lumberjack.getName());
        lumberjack.setMoney(200);
        assertEquals(200, lumberjack.getMoney());
        LinkedList<Asset> inventory = new LinkedList<>();
        Asset a = new Asset("apple", 5, 5);
        Asset b = new Asset("bacon", 5, 5);
        Asset c = new Asset("cheese", 5, 5);
        inventory.add(a);
        inventory.add(b);
        inventory.add(c);
        lumberjack.setInventory(inventory);
        assertEquals(inventory, lumberjack.getInventory());
        lumberjack.printInventory();
    }

    @Test
        // another lumberjack test
    void testingLumberJack() {
        map = new GridMap(30);
        camera = new Camera(map);
        tileMap = new TileMap(map.getBoard(), "Canada");
        lumberjack = new Lumberjack(600, "bob", new LinkedList<>());
        storyGame = new StoryGame("bob", map.getBoardSize());
        Tree tree = new Tree("tree", 10, 20);
        List<Asset> assets = new ArrayList<>();
        Lumber lumber = new Lumber("lumber", 5, 20);
        lumberjack.addToInventory(lumber);
        assertTrue(lumberjack.getInventory().contains(lumber));
        assets.add(tree);
        storyGame.getTileMap().getListOfTiles().get(8).get(7).setAssets(assets);
        assertTrue(storyGame.getTileMap().getListOfTiles().get(8).get(7).getAssets().contains(tree));
        lumberjack.cutDownOneTree(storyGame, 7, 8);
        assertFalse(storyGame.getTileMap().getListOfTiles().get(8).get(7).getAssets().contains(tree));
        lumberjack.drop(storyGame, 7, 8);
        assertFalse(lumberjack.getInventory().contains(lumber));
        lumberjack.observeTile(storyGame, 7, 8);
        assertTrue(storyGame.getTileMap().getListOfTiles().get(8).get(7).getAssets().contains(lumber));
        lumberjack.pick(storyGame, 7, 8);
        assertTrue(lumberjack.getInventory().contains(lumber));
        assertFalse(storyGame.getTileMap().getListOfTiles().get(8).get(7).getAssets().contains(lumber));
        Sapling sapling = new Sapling("sapling", 10, 5);
        lumberjack.addToInventory(sapling);
        assertTrue(lumberjack.getInventory().contains(sapling));
        lumberjack.plantSapling(storyGame, 7, 8);
        assets.remove(0);
        assertFalse(lumberjack.getInventory().contains(sapling));
        tileMap.setAssets(assets, 10);
        storyGame.setTileMap(tileMap);
        lumberjack.observeTile(storyGame, 7, 8);
        assertFalse(storyGame.getTileMap().getListOfTiles().get(8).get(7).getAssets().size() == 10);
        lumberjack.deForest(storyGame, 7, 8);
        assertTrue(storyGame.getTileMap().getListOfTiles().get(8).get(7).getAssets().size() == 0);
    }

    @Test
        // test Lumberjack methods
    void checkLumberJack() {
        assertEquals(500, lumberjack.getMoney());
        Sapling sapling = new Sapling("sapling", 10, 5);
        int money = lumberjack.getMoney();
        int newMoney = money - 10 * 5;
        lumberjack.setMoney(newMoney);
        assertEquals(450, lumberjack.getMoney());
        lumberjack.addToInventory(sapling);
        LinkedList<Asset> inventory = new LinkedList<>();
        inventory.add(sapling);
        assertEquals(inventory, lumberjack.getInventory());
        Sapling sapling2 = new Sapling("sapling", 2, 5);
        Lumber lumber = new Lumber("lumber", 5, 20);
        Tree tree = new Tree("tree", 5, 20);
        inventory.addLast(sapling2);
        inventory.addLast(lumber);
        inventory.addLast(tree);
        lumberjack.addToInventory(sapling2);
        lumberjack.addToInventory(lumber);
        lumberjack.addToInventory(tree);
        assertEquals(inventory, lumberjack.getInventory());
        lumberjack.sellAllLumber();
        inventory.remove(lumber);
        assertEquals(550, lumberjack.getMoney());
        assertEquals(inventory, lumberjack.getInventory());
        lumberjack.sellAllSapling();
        inventory.remove(sapling);
        inventory.remove(sapling2);
        assertEquals(574, lumberjack.getMoney());
        assertEquals(inventory, lumberjack.getInventory());
        lumber.setType("notLumber");
        assertEquals("notLumber", lumber.getType());
        lumber.setAssetSize(20);
        assertEquals(20, lumber.getAssetSize());
        lumber.setAssetValue(20);
        assertEquals(20, lumber.getAssetValue());
        sapling.setType("notSapling");
        assertEquals("notSapling", sapling.getType());
        sapling.setAssetSize(20);
        assertEquals(20, sapling.getAssetSize());
        sapling.setAssetValue(20);
        assertEquals(20, sapling.getAssetValue());
    }
}
