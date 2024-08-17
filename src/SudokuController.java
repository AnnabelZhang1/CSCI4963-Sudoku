import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Optional;

/**
 * This class represents the controller in the MVC pattern for the Sudoku
 * application.
 * It manages the interaction between the user inputs, the Sudoku model, and the
 * view.
 * 
 * Author: Sophie Liu, Yuqing Peng, & Annabel Zhang
 * 
 * Version: 1.0
 */
public class SudokuController {
	private Sudoku model;
	private SudokuViewer view;
	private Stage primaryStage;

	/**
	 * Constructs a SudokuController with the specified model, view, and primary
	 * stage.
	 * 
	 * @param model        The Sudoku model representing the game logic.
	 * @param view         The Sudoku view that displays the game interface.
	 * @param primaryStage The primary stage for this application.
	 */
	public SudokuController(Sudoku model, SudokuViewer view, Stage primaryStage) {
		this.model = model;
		this.view = view;
		this.primaryStage = primaryStage;
		initialize();
	}

	/**
	 * Initializes the controller by setting up event handlers for the buttons in
	 * the view.
	 * It also starts the game timer and updates the view with the current board
	 * state.
	 */
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

	/**
	 * Starts the Sudoku game by setting the scene and displaying the primary stage.
	 * 
	 * @param primaryStage The primary stage for this application.
	 */
	public void start(Stage primaryStage) {
		Scene scene = new Scene(view.getRoot(), 600, 700);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Sudoku :: Team Snickerdoodle");
		primaryStage.show();
	}

	/**
	 * Returns the current scene being displayed.
	 * 
	 * @return the scene currently set in the primary stage.
	 */
	public Scene getScene() {
		return primaryStage.getScene();
	}

	/**
	 * Generates a new Sudoku puzzle, updates the view with the new puzzle,
	 * and restarts the game timer.
	 */
	public void generateNewPuzzle() {
		model.clearBoard();
		model.generatePuzzle();
		view.updateBoard(model.getBoard());
		view.startTimer(); // Restart the timer for the new puzzle
	}

	/**
	 * Displays a confirmation dialog when the user attempts to solve the puzzle.
	 * If the user confirms, the puzzle is solved, and the view is updated with the
	 * solution.
	 * The game timer is stopped once the puzzle is solved.
	 */
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

	/**
	 * Displays a warning alert if the user tries to check the solution without
	 * entering any numbers in the puzzle.
	 */
	private void showNoInputAlert() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("No Input");
		alert.setHeaderText("No input detected");
		alert.setContentText("Please enter numbers in the puzzle before checking your solution.");
		alert.showAndWait();
	}

	/**
	 * Checks if the Sudoku board is empty (i.e., no user input).
	 * 
	 * @return true if the board is empty, false otherwise.
	 */
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

	/**
	 * Displays the winning page if the user correctly solves the Sudoku puzzle.
	 * Stops the game timer and shows the winning screen.
	 */
	private void showWinningPage() {
		view.stopTimer(); // Stop the timer since the user has won
		WinningPage winningPage = new WinningPage(this); // Pass the controller to the WinningPage
		winningPage.show(); // Show the winning window
	}
}
