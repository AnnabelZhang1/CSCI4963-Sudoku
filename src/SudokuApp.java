import javafx.application.Application;
import javafx.stage.Stage;

public class SudokuApp extends Application {

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

    // Method to start the Sudoku game
    private void startSudokuGame(Stage primaryStage) {
        Sudoku model = new Sudoku();
        SudokuViewer view = new SudokuViewer();
        SudokuController controller = new SudokuController(model, view, primaryStage);

        controller.start(primaryStage); // Switch to the Sudoku game scene
    }

    public static void main(String[] args) {
        launch(args);
    }
}
