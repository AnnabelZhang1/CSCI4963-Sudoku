import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import javafx.geometry.Insets;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

/**
 * This class represents the view in the MVC pattern for the Sudoku application.
 * It manages the display of the Sudoku board, user interaction elements, and
 * updates to the visual state of the game. It also handles the game timer and
 * provides methods to interact with the board, such as updating the board,
 * clearing inputs, and highlighting cells.
 * 
 * Author: Sophie Liu, Yuqing Peng, & Annabel Zhang
 * Version: 1.0
 */
public class SudokuViewer {

    private VBox root;
    private GridPane gridPane_board;
    private HBox buttonPanel;
    private TextField[][] cells;
    private TextField selectedCell;
    private Label timerLabel;
    private Timeline timer;
    private int secondsElapsed;

    // Function Buttons
    private Button solveButton;
    private Button clearButton;
    private Button generateButton;
    private Button checkButton;
    private Label feedbackLabel;

    // Number Buttons
    private Button[] numberButtons = new Button[9];
    private int[] numberCount = new int[9]; // Tracks numbers for button grey-out

    private boolean isGameWon;

    /**
     * Constructs a SudokuViewer and initializes the UI components.
     */
    public SudokuViewer() {
        // UI Setup
        gridPane_board = new GridPane();
        cells = new TextField[9][9];
        isGameWon = false;

        // Sets up board
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                cells[row][col] = new TextField();
                cells[row][col].setPrefSize(50, 50);
                cells[row][col].setStyle("-fx-font-size: 24px; -fx-alignment: center; -fx-background-color: white;");

                gridPane_board.add(cells[row][col], col, row);

                // Selects cell with mouse click
                final int r = row;
                final int c = col;

                cells[row][col].setOnMouseClicked(event -> {
                    selectedCell = cells[r][c];
                });

                // Updates buttons when number is manually removed
                cells[row][col].textProperty().addListener((observable, oldValue, newValue) -> {
                    updateButtonState();
                });
            }
        }

        // Creates function buttons
        solveButton = new Button("Solve");
        clearButton = new Button("Clear");
        generateButton = new Button("Generate");
        checkButton = new Button("Check");

        // Creates feedback label
        feedbackLabel = new Label();
        timerLabel = new Label("Time: 00:00");

        // Creates buttons from 1-9
        for (int i = 0; i < 9; i++) {
            int number = i + 1;
            Button button = new Button(String.valueOf(number));
            numberButtons[i] = button;
            button.setPrefWidth(50);
            button.setOnAction(e -> addNumberToSelectedCell(number));
        }

        // Organizing the button panel
        buttonPanel = new HBox(5, solveButton, clearButton, generateButton, checkButton);
        buttonPanel.setPadding(new Insets(10));
        buttonPanel.setSpacing(10);

        HBox mainContent = new HBox(gridPane_board, new VBox(10, numberButtons));
        mainContent.setSpacing(20);

        root = new VBox(20, timerLabel, mainContent, buttonPanel, feedbackLabel);
        root.setPadding(new Insets(20));

        // Setup the timer
        setupTimer();
    }

    /**
     * Sets up the timer using Timeline. The timer updates every second and displays
     * the elapsed time in a minutes:seconds format.
     */
    private void setupTimer() {
        secondsElapsed = 0;
        timer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            secondsElapsed++;
            int minutes = secondsElapsed / 60;
            int seconds = secondsElapsed % 60;
            timerLabel.setText(String.format("Time: %02d:%02d", minutes, seconds));
        }));
        timer.setCycleCount(Timeline.INDEFINITE); // Run indefinitely
    }

    /**
     * Starts the timer from zero and begins counting the elapsed time.
     */
    public void startTimer() {
        secondsElapsed = 0;
        timer.playFromStart();
    }

    /**
     * Stops the timer, pausing the elapsed time count.
     */
    public void stopTimer() {
        timer.stop();
    }

    /**
     * Returns the root VBox containing all UI elements.
     * 
     * @return the root VBox of the SudokuViewer.
     */
    public VBox getRoot() {
        return root;
    }

    /**
     * Returns the 2D array of cells that make up the Sudoku board.
     * 
     * @return a 2D array of cells.
     */
    public TextField[][] getCells() {
        return cells;
    }

    /**
     * Returns the solve button.
     * 
     * @return the button labeled "Solve".
     */
    public Button getSolveButton() {
        return solveButton;
    }

    /**
     * Returns the clear button.
     * 
     * @return the button labeled "Clear".
     */
    public Button getClearButton() {
        return clearButton;
    }

    /**
     * Returns the generate button.
     * 
     * @return the button labeled "Generate".
     */
    public Button getGenerateButton() {
        return generateButton;
    }

    /**
     * Returns the check button.
     * 
     * @return the button labeled "Check".
     */
    public Button getCheckButton() {
        return checkButton;
    }

    /**
     * Adds a number to the currently selected cell on the board.
     * The number is added if the cell is editable and currently empty.
     * 
     * @param number The number to add to the selected cell.
     */
    private void addNumberToSelectedCell(int number) {
        if (selectedCell != null && selectedCell.isEditable()) {
            String currentText = selectedCell.getText();
            if (currentText.isEmpty()) {
                selectedCell.setText(String.valueOf(number));
                updateButtonState();
            }
        }
    }

    /**
     * Updates the state of the number buttons
     */
    private void updateButtonState() {
        // Reset number counts
        for (int i = 0; i < 9; i++) {
            numberCount[i] = 0;
        }

        // Count occurrences of each number on the board
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                String text = cells[row][col].getText();
                if (!text.isEmpty()) {
                    int number = Integer.parseInt(text);
                    numberCount[number - 1]++;
                }
            }
        }

        // Update buttons based on the count
        for (int i = 0; i < 9; i++) {
            if (numberCount[i] >= 9) {
                numberButtons[i].setDisable(true);
                numberButtons[i].setStyle("-fx-background-color: grey;");
            } else {
                numberButtons[i].setDisable(false);
                numberButtons[i].setStyle("");
            }
        }
    }

    /**
     * Updates the Sudoku board UI with the board state.
     * The board is cleared and then filled with the new values
     * 
     * @param board The 2D array representing the Sudoku board state.
     */
    public void updateBoard(int[][] board) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                TextField current = cells[row][col];

                // Resetting board editability and style
                current.setEditable(true);
                current.setStyle("-fx-font-size: 24px; -fx-alignment: center; -fx-background-color: white;");

                // Inputting nothing if empty grid cell
                if (board[row][col] == 0) {
                    current.setText("");
                }
                // Inputting in all starting numbers
                else {
                    current.setText(Integer.toString(board[row][col]));
                    current.setEditable(false);
                    current.setStyle("-fx-font-size: 24px; -fx-alignment: center; -fx-background-color: lightgray;");
                }
            }
        }
        updateButtonState();
    }

    /**
     * Clears only the user inputs
     * 
     * @param original The original Sudoku board with the starting numbers.
     */
    public void clearUserInputs(int[][] original) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                TextField current = cells[row][col];
                if (current.isEditable()) {
                    current.setText("");
                    current.setStyle("-fx-font-size: 24px; -fx-alignment: center; -fx-background-color: white;");
                }
            }
        }
        updateButtonState();
    }

    /**
     * Returns whether the game is won by the user.
     * 
     * @return true if the game is won, false otherwise.
     */
    public boolean isGameWon() {
        return isGameWon;
    }

    /**
     * Highlights incorrect and correct cells based on the provided solution.
     * Correct cells are highlighted in green, while incorrect cells are highlighted
     * in red.
     * The method also checks if the user has won the game by comparing the current
     * board with the solution.
     * 
     * @param board The 2D array representing the correct solution of the Sudoku
     *              puzzle.
     */
    public void highlightCells(int[][] board) {
        isGameWon = true;

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                TextField current = cells[row][col];
                // Ensure we're only checking editable cells
                if (current.isEditable()) {
                    String text = current.getText().trim(); // Trim any whitespace
                    if (!text.isEmpty()) {
                        try {
                            int value = Integer.parseInt(text);
                            if (board[row][col] != value) {
                                current.setStyle(
                                        "-fx-font-size: 24px; -fx-alignment: center; -fx-background-color: #FF6666; ");
                                // If any cell is wrong, the user hasn't won
                                isGameWon = false;
                            } else {
                                current.setStyle(
                                        "-fx-font-size: 24px; -fx-alignment: center; -fx-background-color: #66FF66; ");
                            }
                        } catch (NumberFormatException e) {
                            current.setStyle(
                                    "-fx-font-size: 24px; -fx-alignment: center; -fx-background-color: #FF6666; ");
                            // If any input is not a number, the user hasn't won
                            isGameWon = false;
                        }
                    } else {
                        current.setStyle("-fx-font-size: 24px; -fx-alignment: center; -fx-background-color: white;");
                        // If any cell is empty, the user hasn't won
                        isGameWon = false;
                    }
                }
            }
        }
    }
}
