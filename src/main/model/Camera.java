package model;

// class for an array of arrays game camera
// slice of the array of arrays gridMap, centered on xcolPos and yrowPos
public class Camera {
    private int xcolPos;
    private int yrowPos;
    private final int width = 5;
    private final int height = 5;
    GridMap mapped;

    int[][] cam = new int[width][height];

    // REQUIRES: GridMap
    // MODIFIES: mapped
    // EFFECTS: sets mapped equals gridmap
    // the camera is now looking at the gridmap
    public Camera(GridMap gridmap) {
        mapped = gridmap;
    }

    // REQUIRES: integer x and y
    // MODIFIES: xcolPos, yrowPos
    // EFFECTS: sets the coordinates of center of the camera to x and y
    public void setCenter(int x, int y) {
        this.xcolPos = x;
        this.yrowPos = y;
    }

    public int getX() {
        return xcolPos;
    }

    public int getY() {
        return yrowPos;
    }

    // MODIFIES: cam
    // EFFECTS: updates what the camera sees
    // camera view is a 5*5 area around xcolPos and yrowPos
    // camera takes on same value as 5*5 area around xcolPos and yrowPos on the board
    public int[][] updateCam() {
        EventLog.getInstance().logEvent(new Event("camera update"));
        int[][] board = mapped.getBoard();
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                int y = (row + yrowPos) % board.length;
                int x = (col + xcolPos) % board.length;
                try {
                    cam[row][col] = board[y][x];
                } catch (ArrayIndexOutOfBoundsException e) {
                    if (y >= board.length) {
                        cam[row][col] = board[y - board.length][x];
                    }
                    if (x >= board.length) {
                        cam[row][col] = board[y][x - board.length];
                    }
                    if ((y >= board.length) && (x >= board.length)) {
                        cam[row][col] = board[y - board.length][x - board.length];
                    }
                }
            }
        }
        return cam;
    }

    // EFFECTS: prints camera on console when called
    public void genCam() {
        EventLog.getInstance().logEvent(new Event("camera matrix"));
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                System.out.print(cam[row][col] + "\t");
            }
            System.out.println();
        }

    }

    // MODIFIES: this
    // EFFECTS: process user command
    // updates camera center with setCenter, moves the camera around
    //public void processMoveCommand(String command) {
    //    switch (command) {
    //        case ("a"):
    //            System.out.println("The right arrow key is pressed");
    //           setCenter(xcolPos - 1, yrowPos);
    //            break;
    //        case ("d"):
    //            System.out.println("The left arrow key is pressed");
    //            setCenter(xcolPos + 1, yrowPos);
    //            break;
    //        case ("w"):
    //            System.out.println("The up arrow key is pressed");
    //            setCenter(xcolPos, yrowPos - 1);
    //            break;
    //        case ("s"):
    //            System.out.println("The down arrow key is pressed");
    //            setCenter(xcolPos, yrowPos + 1);
    //            break;
    //    }
    //}
}
