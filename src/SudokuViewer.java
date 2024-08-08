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

    public SudokuViewer() {
        gridPane = new GridPane();
        cells = new TextField[9][9];

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                cells[row][col] = new TextField();
                cells[row][col].setPrefSize(50, 50);
                gridPane.add(cells[row][col], col, row);
            }
        }

        solveButton = new Button("Solve");
        clearButton = new Button("Clear");

        root = new VBox(gridPane, solveButton, clearButton);
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

    public void updateBoard(int[][] board) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                cells[row][col].setText(board[row][col] == 0 ? "" : Integer.toString(board[row][col]));
            }
        }
    }
}
