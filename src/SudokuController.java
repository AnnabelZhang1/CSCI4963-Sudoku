import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class SudokuController {
    private Sudoku model;
    private SudokuViewer view;

    public SudokuController(Sudoku model, SudokuViewer view) {
        this.model = model;
        this.view = view;
        initialize();
    }

    private void initialize() {
        // Populate the initial board
        view.updateBoard(model.getBoard());

        // Add input validation and interaction handlers
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                TextField cell = view.getCells()[row][col];
                final int r = row;
                final int c = col;

                cell.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        String character = event.getCharacter();
                        if (!character.matches("[1-9]")) {
                            event.consume(); // Ignore non-numeric input
                        }
                    }
                });

                cell.setOnAction(e -> {
                    String text = cell.getText();
                    if (text.matches("[1-9]")) {
                        int value = Integer.parseInt(text);
                        if (model.isValidMove(r, c, value)) {
                            model.getBoard()[r][c] = value;
                        } else {
                            cell.setText(""); // Clear the invalid input
                        }
                    }
                });
            }
        }

        // Add functionality for a "Solve" button
        view.getSolveButton().setOnAction(e -> {
            if (model.solve()) {
                view.updateBoard(model.getBoard());
            } else {
                // Show a message indicating the puzzle is unsolvable
                System.out.println("Unsolvable puzzle.");
            }
        });

        // Add functionality for a "Clear" button
        view.getClearButton().setOnAction(e -> {
            model.clearBoard();
            view.updateBoard(model.getBoard());
        });

        // Additional initialization as needed
    }

    public void start(Stage primaryStage) {
        Scene scene = new Scene(view.getRoot(), 450, 450);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Sudoku");
        primaryStage.show();
    }
}
