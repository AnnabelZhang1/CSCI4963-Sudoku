import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HomePage {

    private VBox root;
    private Scene scene;
    private Text title;
    private Text subtitle;
    private Button startNewGameButton;
    private Button quitButton;

    public HomePage(Stage primaryStage) {
        root = new VBox(20); // Increased spacing between elements for a cleaner look
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #2C3E50;"); // Set a modern dark background color

        title = new Text("Sudoku");
        title.setStyle("-fx-font-size: 48px; -fx-font-weight: bold; -fx-fill: #ECF0F1;"); // Larger font size and light color

        subtitle = new Text("By Sophie Liu, Yuqing Peng, & Annabel Zhang");
        subtitle.setStyle("-fx-font-size: 18px; -fx-font-weight: normal; -fx-fill: #BDC3C7;"); // Lighter color for subtitle

        // Button Styles
        String buttonStyle = "-fx-font-size: 18px; -fx-padding: 10px 20px; -fx-background-color: #3498DB; -fx-text-fill: white; -fx-background-radius: 5;";

        startNewGameButton = new Button("Start New Game");
        startNewGameButton.setStyle(buttonStyle);

        quitButton = new Button("Quit");
        quitButton.setStyle(buttonStyle + "-fx-background-color: #E74C3C;"); // Red color for quit button

        // Adds an empty block of space
        Region spacer1 = new Region();
        spacer1.setMinHeight(20);

        Region spacer2 = new Region();
        spacer2.setMinHeight(20);

        // Ensure all components are initialized before adding them to the VBox
        root.getChildren().addAll(title, subtitle, spacer1, startNewGameButton, spacer2, quitButton);

        scene = new Scene(root, 600, 400);
    }

    public Scene getScene() {
        return scene;
    }

    public Button getStartNewGameButton() {
        return startNewGameButton;
    }

    public Button getQuitButton() {
        return quitButton;
    }
}
