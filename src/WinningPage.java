import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * This class represents the winning page displayed when the user successfully
 * solves the Sudoku puzzle.
 * The winning page is displayed in a separate stage.
 * 
 * Author: Sophie Liu, Yuqing Peng, & Annabel Zhang
 * Version: 1.0
 */
public class WinningPage {

    private Stage winningStage;

    /**
     * Constructs a WinningPage with a congratulatory message and a "Play Again"
     * button.
     * The page is displayed in a new Stage separate from the main game window.
     * 
     * @param controller The SudokuController instance used to generate a new puzzle
     *                   when the user chooses to play again.
     */
    public WinningPage(SudokuController controller) {
        VBox root = new VBox(20);
        root.setStyle("-fx-alignment: center; -fx-padding: 20; -fx-background-color: #282C34;");

        Label winningMessage = new Label("🎉 Congratulations! 🎉");
        winningMessage.setFont(Font.font("Arial", FontWeight.BOLD, 36));
        winningMessage.setTextFill(Color.GOLD);

        Label subtitle = new Label("You solved the Sudoku!");
        subtitle.setFont(Font.font("Arial", FontWeight.NORMAL, 24));
        subtitle.setTextFill(Color.WHITE);

        Button playAgainButton = new Button("Play Again");
        playAgainButton.setStyle(
                "-fx-font-size: 18px; -fx-background-color: #4CAF50; -fx-text-fill: white; -fx-padding: 10px 20px;");

        playAgainButton.setOnAction(e -> {
            // Regenerate the puzzle and close the winning page
            controller.generateNewPuzzle();
            winningStage.close(); // Close the winning window
        });

        root.getChildren().addAll(winningMessage, subtitle, playAgainButton);

        Scene scene = new Scene(root, 500, 400);

        // Initialize the Stage
        winningStage = new Stage();
        winningStage.setTitle("You Won!");
        winningStage.setScene(scene);
    }

    /**
     * Displays the winning page in a new window.
     */
    public void show() {
        winningStage.show(); // Show the winning window
    }
}
