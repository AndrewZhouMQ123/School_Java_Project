package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HouseTest {
    private House house;
    private List<Asset> loa;

    @BeforeEach
    void runBefore() {
        loa = new ArrayList<>();
        Asset a = new Asset("wood", 4, 80);
        loa.add(a);
        house = new House(42, 4, 2, loa);
    }

    @Test
        // construct a house, get and set house fields

    void checkHouseGetSet() {
        assertEquals(42, house.getHouseSize());
        assertEquals(4, house.getHouseRow());
        assertEquals(2, house.getHouseCol());
        assertEquals(loa, house.getStorage());
    }
}
