package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TreeTest {

    @Test
        // test Tree methods
    void checkTree() {
        Tree tree = new Tree("tree", 5, 20);
        assertEquals("tree", tree.getType());
        assertEquals(5, tree.getAssetSize());
        assertEquals(20, tree.getAssetValue());
    }

    @Test
        // Tree setter
    void treeSetGet() {
        Tree tree = new Tree("tree", 10, 20);
        tree.setType("willow");
        tree.setAssetSize(20);
        tree.setAssetValue(60);
        assertEquals("willow", tree.getType());
        assertEquals(20, tree.getAssetSize());
        assertEquals(60, tree.getAssetValue());
    }
}
