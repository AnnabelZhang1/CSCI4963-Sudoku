import javafx.application.Application;
import javafx.stage.Stage;

public class SudokuApp extends Application {
	
    @Override
    public void start(Stage primaryStage) {
    	
        Sudoku model = new Sudoku();
        SudokuViewer view = new SudokuViewer();
        SudokuController controller = new SudokuController(model, view);

        controller.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
