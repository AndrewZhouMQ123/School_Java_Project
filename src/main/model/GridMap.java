package model;

import java.util.Random;

// class for an array of arrays game map
// the first numerical stage of the game map

public class GridMap {
    private int boardSize;
    private int[][] board;

    // REQUIRES: integer, boardSize >= 20
    // MODIFIES: boardSize, board
    // EFFECTS: fills the board with random integers from 0 to 3
    public GridMap(int boardSize) {
        this.boardSize = boardSize;
        this.board = new int[boardSize][boardSize];
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                Random random = new Random();
                int randomNumber = random.nextInt(4);
                board[row][col] = randomNumber;
            }
        }
    }

    // EFFECTS: prints the board in the console when called
    public void genMap() {
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                System.out.print(board[row][col] + "\t");
            }
            System.out.println();
        }

    }

    // getter, gets the board
    public int[][] getBoard() {
        return board;
    }

    // setter, modifies boardSize
    public void setBoardSize(int newBoardSize) {
        boardSize = newBoardSize;
    }

    public void setBoard(int [][] newBoard) {
        board = newBoard;
    }

    public int getBoardSize() {
        return boardSize;
    }

}
