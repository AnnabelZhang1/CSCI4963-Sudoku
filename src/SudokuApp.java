import javafx.application.Application;
import javafx.stage.Stage;

/**
 * This class represents the main entry point for the Sudoku application.
 * It includs the initial display of the home page and transitioning to the
 * Sudoku game when the user chooses to start a new game.
 * 
 * Author: Sophie Liu, Yuqing Peng, & Annabel Zhang
 * Version: 1.0
 */
public class SudokuApp extends Application {

    /**
     * The main entry point for all JavaFX applications.
     * The start method is called after the JavaFX runtime has been initialized.
     * 
     * @param primaryStage The primary stage for this application.
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("CSCI4963 :: Sudoku");

        // Create the home page
        HomePage homePage = new HomePage(primaryStage);
        primaryStage.setScene(homePage.getScene());

        // Add functionality to the "Start New Game" button
        homePage.getStartNewGameButton().setOnAction(e -> {
            startSudokuGame(primaryStage);
        });

        // Add functionality to the "Quit" button
        homePage.getQuitButton().setOnAction(e -> {
            primaryStage.close();
        });

        primaryStage.show();
    }

    /**
     * Starts a new Sudoku game by creating the model, view, and controller.
     * It then transitions to the Sudoku game scene.
     * 
     * @param primaryStage The primary stage for this application.
     */
    private void startSudokuGame(Stage primaryStage) {
        Sudoku model = new Sudoku();
        SudokuViewer view = new SudokuViewer();
        SudokuController controller = new SudokuController(model, view, primaryStage);

        controller.start(primaryStage); // Switch to the Sudoku game scene
    }

    /**
     * The main method is the entry point for the application.
     * It launches the JavaFX application.
     * 
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
