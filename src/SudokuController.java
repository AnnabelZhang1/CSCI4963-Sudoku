

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
        view.updateBoard(model.getBoard());

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
                            event.consume();
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
                            cell.setText("");
                        }
                    }
                });
            }
        }

        view.getSolveButton().setOnAction(e -> {
            if (model.solve()) {
                view.updateBoard(model.getBoard());
            } else {
                System.out.println("Unsolvable puzzle.");
            }
        });

        view.getClearButton().setOnAction(e -> {
            model.clearBoard();
            view.updateBoard(model.getBoard());
        });

        view.getGenerateButton().setOnAction(e -> {
            model.clearBoard();
            model.generatePuzzle();
            view.updateBoard(model.getBoard());
        });
    }

    public void start(Stage primaryStage) {
        Scene scene = new Scene(view.getRoot(), 500, 550);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Sudoku");
        primaryStage.show();
    }
}
