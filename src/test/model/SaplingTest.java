package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SaplingTest {
    @Test
        // test Sapling methods
    void checkSapling() {
        Sapling sapling = new Sapling("sapling", 8, 5);
        assertEquals("sapling", sapling.getType());
        assertEquals(8, sapling.getAssetSize());
        assertEquals(5, sapling.getAssetValue());
    }
}
