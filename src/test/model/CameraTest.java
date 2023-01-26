package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CameraTest {
    private GridMap map;
    private Camera camera;

    @BeforeEach
    void runBefore() {
        map = new GridMap(30);
        camera = new Camera(map);
    }

    @Test
        // check that the length of camera is correct
    void checkCamSize() {
        int [][] cam = camera.updateCam();
        // all arrays in board have length 5
        int j = 0;
        for (int[] arr : cam) {
            j += 1;
            assertEquals(arr.length, 5);
        }
        // check there are 5 arrays in cam
        assertEquals(j,5);
    }

    @Test
        // check that set center changes x, y coordinates
    void checkSetCenter() {
        int x = 8;
        int y = -8;
        camera.setCenter(x, y);
        int col = camera.getX();
        int row = camera.getY();
        assertEquals(x, col);
        assertEquals(y, row);
    }

    @Test
        // test run genCam
    void testGenCam() {
        camera.genCam();
    }
}
