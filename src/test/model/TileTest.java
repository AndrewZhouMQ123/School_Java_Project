package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TileTest {

    @Test
        // getter setter for tile
    void checkGetSetTile() {
        List<Asset> assets = new ArrayList<>();
        Asset asset = new Asset("tree", 1, 10);
        assets.add(asset);
        Tile tile = new Tile(5, "cyan", "cylinder", assets);
        assertEquals(5, tile.getLength());
        assertEquals("cyan", tile.getColor());
        assertEquals("cylinder", tile.getShape());
        assertEquals(assets, tile.getAssets());
        tile.setLength(10);
        tile.setColor("red");
        tile.setShape("doughnut");
        List<Asset> loa = new ArrayList<>();
        Asset a = new Asset("tree", 4, 40);
        loa.add(a);
        tile.setAssets(loa);
        assertEquals(10, tile.getLength());
        assertEquals("red", tile.getColor());
        assertEquals(loa, tile.getAssets());

    }

}
