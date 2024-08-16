import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class SudokuViewer {
    
    private VBox root;
    private GridPane gridPane_board;
    private GridPane gridPane_buttons;
    private TextField[][] cells;
    private TextField selectedCell;
    
    // Function Buttons
    private Button solveButton;
    private Button clearButton;
    private Button generateButton;
    
    // Number Buttons
    private Button[] numberButtons = new Button[9];
    private int[] numberCount = new int[9];	// Tracks numbers for button grey-out

    public SudokuViewer() {
    	
    	// UI Setup
        gridPane_board = new GridPane();
        gridPane_buttons = new GridPane();
        cells = new TextField[9][9];

        // Sets up board
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                cells[row][col] = new TextField();
                cells[row][col].setPrefSize(50, 50);
                setCellBorder(row, col);
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
        
        gridPane_buttons.setPadding(new Insets(5));
        gridPane_buttons.setHgap(5);
        gridPane_buttons.setVgap(5);
        
        // Creates buttons from 1-9
        for (int i = 0; i < 9; i++) {
            int number = i + 1;
            Button button = new Button(String.valueOf(number));
            numberButtons[i] = button;
            gridPane_buttons.add(button, i % 3, i / 3);
            button.setOnAction(e -> addNumberToSelectedCell(number));
            System.out.println("boop added");
        }
        
        HBox mainContent = new HBox(gridPane_board, gridPane_buttons);
        mainContent.setSpacing(10);
        
        root = new VBox(mainContent, solveButton, clearButton, generateButton);
        root.setSpacing(10);
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
    	//System.out.println("updating button state");
    	
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
            }
            else {
                numberButtons[i].setDisable(false);
                numberButtons[i].setStyle("");
            }
        }
    }
    
    
    /**
     * Updates the board with a new blank Sudoku board
     * 
     * @param board The generated Sudoku board
     */
    public void updateBoard(int[][] board) {
    	
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
            	
            	TextField current = cells[row][col];
            	
            	// Resetting board editability
            	current.setEditable(true);
//            	current.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-width: 1");
            	
            	// Inputting nothing if empty grid cell
            	if (board[row][col] == 0) {
            		current.setText("");
            	}
            	// Inputting in all starting numbers
            	else {
            		current.setText(Integer.toString(board[row][col]));
            		current.setEditable(false);
//            		current.setStyle("-fx-background-color: #D89412");
            	}
            }
        }
        updateButtonState();
    }

    /**
     * Styles the cell borders
     * 
     * @param row The row of the cell
     * @param col The column of the cell
     */
    private void setCellBorder(int row, int col) {
        String style = "";
        if (row == 2 || row == 5) {
            style += "-fx-border-width: 0 0 2px 0; -fx-border-color: black; -fx-border-style: solid;";
        }
        if (col == 2 || col == 5) {
            style += "-fx-border-width: 0 2px 0 0; -fx-border-color: black; -fx-border-style: solid;";
        }
        if (row == 2 && col == 5) {
            style += "-fx-border-width: 0 2px 2px 0; -fx-border-color: black; -fx-border-style: solid;";
        }
        if (row == 5 && col == 5) {
            style += "-fx-border-width: 0 2px 2px 0; -fx-border-color: black; -fx-border-style: solid;";
        }
        if (row == 2 && col == 2) {
            style += "-fx-border-width: 0 2px 2px 0; -fx-border-color: black; -fx-border-style: solid;";
        }
        if (row == 5 && col == 2) {
            style += "-fx-border-width: 0 2px 2px 0; -fx-border-color: black; -fx-border-style: solid;";
        }
       
        cells[row][col].setStyle(style);
    }

}
