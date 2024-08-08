
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class SudokuViewer {
    private VBox root;
    private GridPane gridPane;
    private TextField[][] cells;
    private Button solveButton;
    private Button clearButton;
    private Button generateButton;

    public SudokuViewer() {
        gridPane = new GridPane();
        cells = new TextField[9][9];

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                cells[row][col] = new TextField();
                cells[row][col].setPrefSize(50, 50);
                setCellBorder(row, col);
                gridPane.add(cells[row][col], col, row);
            }
        }

        solveButton = new Button("Solve");
        clearButton = new Button("Clear");
        generateButton = new Button("Generate");

        root = new VBox(gridPane, solveButton, clearButton, generateButton);
        root.setSpacing(10);
    }

    public VBox getRoot() {
        return root;
    }

    public TextField[][] getCells() {
        return cells;
    }

    public Button getSolveButton() {
        return solveButton;
    }

    public Button getClearButton() {
        return clearButton;
    }

    public Button getGenerateButton() {
        return generateButton;
    }

    public void updateBoard(int[][] board) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                cells[row][col].setText(board[row][col] == 0 ? "" : Integer.toString(board[row][col]));
            }
        }
    }

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
