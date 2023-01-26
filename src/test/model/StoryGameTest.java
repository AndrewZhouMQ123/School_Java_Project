package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StoryGameTest {
    private GridMap map;
    private Camera camera;
    private TileMap tileMap;
    private House house;
    private List<Asset> loa;
    private StoryGame storyGame;
    private Lumberjack lumberjack;

    @BeforeEach
    void runBefore() {
        loa = new ArrayList<>();
        Asset a = new Asset("wood", 4, 80);
        loa.add(a);
        house = new House(42, 4, 2, loa);
        storyGame = new StoryGame("aba", 30);
        lumberjack = storyGame.getLumberjack();
        storyGame.setHouse(house);
        tileMap = storyGame.getTileMap();
        map = storyGame.getGridMap();
        camera = new Camera(map);
        storyGame.getTileMap().getListOfTiles().get(6).set(5, house);
    }

    @Test
        // get set StoryGame
    void storyGameGetSet() {
        assertEquals("aba", storyGame.getName());
        assertEquals(tileMap, storyGame.getTileMap());
        assertEquals(map, storyGame.getGridMap());
        assertEquals(15, storyGame.getHomeCol());
        assertEquals(15, storyGame.getHomeRow());
        assertEquals(lumberjack, storyGame.getLumberjack());
        assertEquals(house, storyGame.getHouse());
    }

    @Test
        // Story Game setter
    void storyGameSetGet() {
        Lumberjack jack = new Lumberjack(2000, "jack", new LinkedList<Asset>());
        storyGame.setLumberjack(jack);
        assertEquals(jack, storyGame.getLumberjack());
        House housey = new House(12, -5, 8, new ArrayList<>());
        storyGame.setHouse(housey);
        assertEquals(housey, storyGame.getHouse());
        storyGame.setHomeRow(-5);
        storyGame.setHomeCol(8);
        assertEquals(-5, storyGame.getHomeRow());
        assertEquals(8, storyGame.getHomeCol());
    }
}
