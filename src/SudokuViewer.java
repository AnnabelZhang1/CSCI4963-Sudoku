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

public class SudokuViewer {

    private VBox root;
    private GridPane gridPane_board;
    private HBox buttonPanel;
    private TextField[][] cells;
    private TextField selectedCell;
    private Label timerLabel; // Label to display the timer
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

    public SudokuViewer() {

        // UI Setup
        gridPane_board = new GridPane();
        cells = new TextField[9][9];

        // Sets up board
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                cells[row][col] = new TextField();
                cells[row][col].setPrefSize(50, 50);

                cells[row][col].setStyle("-fx-font-size: 24px; -fx-alignment: center; -fx-background-color: white;"); // Set
                                                                                                                      // larger
                                                                                                                      // font
                                                                                                                      // size
                                                                                                                      // and
                                                                                                                      // center
                                                                                                                      // alignment

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
     * Sets up the timer using Timeline.
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
     * Starts the timer.
     */
    public void startTimer() {
        secondsElapsed = 0;
        timer.playFromStart();
    }

    /**
     * Stops the timer.
     */
    public void stopTimer() {
        timer.stop();
    }

    /**
     * Returns the JavaFX elements
     * 
     * @return Returns all the JavaFX elements
     */
    public VBox getRoot() {
        return root;
    }

    /**
     * Returns the board cells
     * 
     * @return A 2D array of TextField[][] cells
     */
    public TextField[][] getCells() {
        return cells;
    }

    /**
     * @return The button for Solve
     */
    public Button getSolveButton() {
        return solveButton;
    }

    /**
     * @return The button for Clear
     */
    public Button getClearButton() {
        return clearButton;
    }

    /**
     * @return The button for Generate
     */
    public Button getGenerateButton() {
        return generateButton;
    }

    /**
     * @return The button for Check
     */
    public Button getCheckButton() {
        return checkButton;
    }

    /**
     * Adds a number to the selected cell based on the button clicked
     * 
     * @param number The number of the button clicked
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
     * Checks to grey out buttons when necessary
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
     * Updates the board with a new Sudoku board
     * 
     * @param board The generated Sudoku board
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
     * Clears only the user inputs, leaving the original puzzle intact.
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
     * Highlights incorrect and correct cells based on the provided solution.
     */
    public void highlightCells(int[][] board) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                TextField current = cells[row][col];
                if (current.isEditable()) { // Ensure we're only checking editable cells
                    String text = current.getText().trim(); // Trim any whitespace
                    if (!text.isEmpty()) { // Check if the cell is not empty
                        try {
                            int value = Integer.parseInt(text);
                            if (board[row][col] != value) {
                                current.setStyle(
                                        "-fx-font-size: 24px; -fx-alignment: center; -fx-background-color: #FF6666; ");

                            } else {
                                current.setStyle(
                                        "-fx-font-size: 24px; -fx-alignment: center; -fx-background-color: #66FF66; ");
                            }
                        } catch (NumberFormatException e) {
                            // Handle the case where the user input is not a number, which should not happen
                            current.setStyle(
                                    "-fx-font-size: 24px; -fx-alignment: center; -fx-background-color: #FF6666; ");
                        }
                    } else {
                        current.setStyle("-fx-font-size: 24px; -fx-alignment: center; -fx-background-color: white;");
                    }
                }
            }
        }

    }

}
