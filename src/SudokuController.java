import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Optional;

public class SudokuController {
	private Sudoku model;
	private SudokuViewer view;
	private Stage primaryStage;

	public SudokuController(Sudoku model, SudokuViewer view, Stage primaryStage) {
		this.model = model;
		this.view = view;
		this.primaryStage = primaryStage;
		initialize();
	}

	private void initialize() {
		view.updateBoard(model.getBoard());
		view.startTimer(); // Start the timer when the game starts

		view.getSolveButton().setOnAction(e -> showSolveConfirmation());
		view.getClearButton().setOnAction(e -> view.clearUserInputs(model.getBoard()));
		view.getGenerateButton().setOnAction(e -> generateNewPuzzle());
		view.getCheckButton().setOnAction(e -> {
			if (isBoardEmpty()) {
				showNoInputAlert();
			} else {
				view.highlightCells(model.getSolution());
				if (view.isGameWon()) {
					showWinningPage();
				}
			}
		});
	}

	public void start(Stage primaryStage) {
		Scene scene = new Scene(view.getRoot(), 600, 700);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Sudoku :: Team Snickerdoodle");
		primaryStage.show();
	}

	public Scene getScene() {
		return primaryStage.getScene();
	}

	public void generateNewPuzzle() {
		model.clearBoard();
		model.generatePuzzle();
		view.updateBoard(model.getBoard());
		view.startTimer(); // Restart the timer for the new puzzle
	}

	private void showSolveConfirmation() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Solve Puzzle");
		alert.setHeaderText("Are you sure you want to solve the puzzle?");
		alert.setContentText("Clicking 'Solve' will reveal the solution \n and you won't be able to continue.");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			if (model.solve()) {
				view.updateBoard(model.getBoard());
				view.stopTimer(); // Stop the timer when the puzzle is solved
			} else {
				System.out.println("Unsolvable puzzle.");
			}
		}
	}

	private void showNoInputAlert() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("No Input");
		alert.setHeaderText("No input detected");
		alert.setContentText("Please enter numbers in the puzzle before checking your solution.");
		alert.showAndWait();
	}

	private boolean isBoardEmpty() {
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				TextField cell = view.getCells()[row][col];
				if (!cell.getText().trim().isEmpty()) {
					return false;
				}
			}
		}
		return true;
	}

	private void showWinningPage() {
		view.stopTimer(); // Stop the timer since the user has won
		WinningPage winningPage = new WinningPage(this); // Pass the controller to the WinningPage
		winningPage.show(); // Show the winning window
	}
}
