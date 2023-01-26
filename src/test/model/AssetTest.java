package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AssetTest {

    @Test
        // get set Asset
    void checkAssetGetSet() {
        Asset a = new Asset("wood", 4, 80);
        assertEquals("wood", a.getType());
        assertEquals(4, a.getAssetSize());
        assertEquals(80, a.getAssetValue());
    }

    @Test
        // Asset setter
    void assetSetGet() {
        Asset asset = new Asset("asset", 10, 12);
        asset.setType("ball");
        asset.setAssetSize(20);
        asset.setAssetValue(10);
        assertEquals("ball", asset.getType());
        assertEquals(20, asset.getAssetSize());
        assertEquals(10, asset.getAssetValue());
    }

}
