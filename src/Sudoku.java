import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * This class represents the Sudoku game. It is use to generate
 * a Sudoku puzzle, solve it, validate moves, and display the solution.
 * The Sudoku board is a 9x9 grid with subgrids of size 3x3.
 * 
 * Author: Sophie Liu, Yuqing Peng, & Annabel Zhang
 * 
 * Version: 1.0
 */
public class Sudoku {
    private int[][] board;
    // The solution board
    private int[][] solution;
    private static final int BOARD_SIZE = 9;
    private static final int SUBGRID_SIZE = 3;
    private static final int EMPTY_CELL = 0;

    /**
     * Initializes the puzzle board and solution board
     * Puzzle board is cleared and a new puzzle is generated.
     */
    public Sudoku() {
        board = new int[BOARD_SIZE][BOARD_SIZE];
        solution = new int[BOARD_SIZE][BOARD_SIZE];
        clearBoard();
        generatePuzzle();
    }

    /**
     * Returns the current state of the Sudoku board.
     * 
     * @return a 2D array representing the Sudoku board.
     */
    public int[][] getBoard() {
        return board;
    }

    /**
     * Returns the solution of the Sudoku puzzle.
     * 
     * @return a 2D array representing the solved Sudoku board.
     */
    public int[][] getSolution() {
        return solution;
    }

    /**
     * Creates and returns a copy of the current Sudoku board.
     * 
     * @return a 2D array that is a copy of the current board.
     */
    public int[][] copyBoard() {
        int[][] copy = new int[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            System.arraycopy(board[i], 0, copy[i], 0, BOARD_SIZE);
        }
        return copy;
    }

    /**
     * Validates whether placing a number in a given cell (row or col) is a valid
     * move.
     * The move is valid if the number is not present in the same row, column,
     * or 3x3 subgrid.
     * 
     * @param row The row index of the cell.
     * @param col The column index of the cell.
     * @param num The number to place in the cell.
     * @return true if the move is valid, false otherwise.
     */
    public boolean isValidMove(int row, int col, int num) {
        for (int x = 0; x < BOARD_SIZE; x++) {
            if (board[row][x] == num || board[x][col] == num) {
                return false;
            }
        }

        int startRow = row - row % SUBGRID_SIZE;
        int startCol = col - col % SUBGRID_SIZE;
        for (int i = 0; i < SUBGRID_SIZE; i++) {
            for (int j = 0; j < SUBGRID_SIZE; j++) {
                if (board[i + startRow][j + startCol] == num) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Solves the Sudoku puzzle using a backtracking algorithm.
     * 
     * @return true if the puzzle is solved, false if there is no solution.
     */
    public boolean solve() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (board[row][col] == EMPTY_CELL) {
                    for (int num = 1; num <= BOARD_SIZE; num++) {
                        if (isValidMove(row, col, num)) {
                            board[row][col] = num;
                            if (solve()) {
                                return true;
                            }
                            board[row][col] = EMPTY_CELL;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Clears the Sudoku board, setting all cells to empty.
     */
    public void clearBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = EMPTY_CELL;
            }
        }
    }

    /**
     * Generates a new Sudoku puzzle by filling the diagonal subgrids and then
     * solving it.
     * The solution is stored and some cells are then removed to create the puzzle.
     */
    public void generatePuzzle() {
        Random random = new Random();
        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= BOARD_SIZE; i++) {
            numbers.add(i);
        }

        // Fill the diagonal subgrids with random numbers
        for (int i = 0; i < BOARD_SIZE; i += SUBGRID_SIZE) {
            Collections.shuffle(numbers, random);
            for (int row = 0; row < SUBGRID_SIZE; row++) {
                for (int col = 0; col < SUBGRID_SIZE; col++) {
                    board[i + row][i + col] = numbers.get(row * SUBGRID_SIZE + col);
                }
            }
        }

        // Solve the board fully to generate the solution
        solve();
        // Copy the fully solved board to the solution
        copyBoardToSolution();

        // Print solution for debugging and demoing purposes.
        // printSolution();

        // remove some of the cell to create the puzzle
        int cellsToRemove = BOARD_SIZE * BOARD_SIZE / 2;
        while (cellsToRemove > 0) {
            int row = random.nextInt(BOARD_SIZE);
            int col = random.nextInt(BOARD_SIZE);
            if (board[row][col] != EMPTY_CELL) {
                board[row][col] = EMPTY_CELL;
                cellsToRemove--;
            }
        }
    }

    /**
     * Copies the current board to the solution array. This is used after the
     * board is fully solved to store the solution.
     */
    private void copyBoardToSolution() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                solution[i][j] = board[i][j];
            }
        }
    }

    /**
     * Prints the correct solution of the Sudoku puzzle to the console.
     * This method is used primarily for debugging and demoing purposes.
     */
    public void printSolution() {
        System.out.println("Correct Solution:");
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                System.out.print(solution[i][j] + " ");
            }
            System.out.println();
        }
    }
}
