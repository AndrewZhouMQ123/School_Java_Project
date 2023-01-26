package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LumberTest {

    @Test
        // test Lumber methods
    void checkLumber() {
        Lumber lumber = new Lumber("lumber", 5, 20);
        assertEquals("lumber", lumber.getType());
        assertEquals(5, lumber.getAssetSize());
        assertEquals(20, lumber.getAssetValue());
    }

}
