package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GridMapTest {
    private GridMap map;

    @BeforeEach
    void runBefore() {
        map = new GridMap(30);
    }

    @Test
        // check that the length of board is correct
    void checkMapSize() {
        int length =  map.getBoardSize();
        assertEquals(30, length);
    }

    @Test
        // test run gen map
    void testGenMap() {
        map.genMap();
    }

    @Test
        // set a new Map, check new length of board, run gen map
    void testSetBoardSize() {
        map.setBoardSize(40);
        assertEquals(40, map.getBoardSize());
    }

    @Test
        // set a new Map, check new length of board, run gen map
    void testSetBoard() {
        int[][] newBoard = new int[40][40];
        map.setBoard(newBoard);
        assertEquals(newBoard, map.getBoard());
    }

    @Test
        // check that random integer generated is in correct range
    void checkValueRange() {
        int [][] board =  map.getBoard();
        for (int [] arr: board) {
            for (int val : arr) {
                assertTrue((val >= 0) && (val <=3));
            }
        }

    }
}
