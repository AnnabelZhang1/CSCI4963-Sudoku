import javafx.application.Application;
import javafx.stage.Stage;

public class SudokuApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("CSCI4963 :: Sudoku");

        // Creates and shows the home page first
        HomePage homePage = new HomePage(primaryStage);
        primaryStage.setScene(homePage.getScene());

        // Gives functionality to buttons
        homePage.getSinglePlayerButton().setOnAction(e -> {
            startSudokuGame(primaryStage);
        });

        homePage.getCompetitiveModeButton().setOnAction(e -> {
            startSudokuGame(primaryStage);
        });

        homePage.getQuitButton().setOnAction(e -> {
            primaryStage.close();
        });

        primaryStage.show();
    }

    // Shows the singleplayer game
    private void startSudokuGame(Stage primaryStage) {
        Sudoku model = new Sudoku();
        SudokuViewer view = new SudokuViewer();
        SudokuController controller = new SudokuController(model, view);

        controller.start(primaryStage); // This switches the scene to the Sudoku game
        view.updateBoard(model.getBoard()); // Apply styles to the board immediately after switching the scene
    }

    public static void main(String[] args) {
        launch(args);
    }
}
