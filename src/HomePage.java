import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * This class represents the Home Page of the Sudoku application. It provides the title, subtitle, and buttons for starting a new game or quitting the application.
 * The layout is designed using a VBox with a dark blue background and styled buttons.
 * 
 * <p>Author: Sophie Liu, Yuqing Peng, & Annabel Zhang</p>
 * <p>Version: 1.0</p>
 */
public class HomePage {

    private VBox root;
    private Scene scene;
    private Text title;
    private Text subtitle;
    private Button startNewGameButton;
    private Button quitButton;

    /**
     * Constructs a HomePage and sets up the layout and components.
     * 
     * @param primaryStage The primary stage for this application.
     */
    public HomePage(Stage primaryStage) {
        root = new VBox(20); 
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.CENTER);
        // Set a dark background color
        root.setStyle("-fx-background-color: #2C3E50;"); 

        title = new Text("Sudoku");
        // Larger font size and light color
        title.setStyle("-fx-font-size: 48px; -fx-font-weight: bold; -fx-fill: #ECF0F1;"); 

        subtitle = new Text("By Sophie Liu, Yuqing Peng, & Annabel Zhang");
        // Lighter color for subtitle
        subtitle.setStyle("-fx-font-size: 18px; -fx-font-weight: normal; -fx-fill: #BDC3C7;"); 

        // Button Styles
        String buttonStyle = "-fx-font-size: 18px; -fx-padding: 10px 20px; -fx-background-color: #3498DB; -fx-text-fill: white; -fx-background-radius: 5;";

        startNewGameButton = new Button("Start New Game");
        startNewGameButton.setStyle(buttonStyle);

        // Red color for quit button
        quitButton = new Button("Quit");
        quitButton.setStyle(buttonStyle + "-fx-background-color: #E74C3C;"); 

        // Adds an empty block of space
        Region spacer1 = new Region();
        spacer1.setMinHeight(20);

        Region spacer2 = new Region();
        spacer2.setMinHeight(20);

        //adding all component to the VBox
        root.getChildren().addAll(title, subtitle, spacer1, startNewGameButton, spacer2, quitButton);

        scene = new Scene(root, 600, 400);
    }

    /**
     * Returns the Scene of the Home Page.
     * 
     * @return the scene displaying the Home Page.
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * Returns the Button for starting a new game.
     * 
     * @return the button labeled "Start New Game".
     */
    public Button getStartNewGameButton() {
        return startNewGameButton;
    }

    /**
     * Returns the Button for quitting the application.
     * 
     * @return the button labeled "Quit".
     */
    public Button getQuitButton() {
        return quitButton;
    }
}
