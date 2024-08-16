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
    private Button singlePlayerButton;
    private Button competitiveModeButton;
    private Button quitButton;

    public HomePage(Stage primaryStage) {
    	
        root = new VBox(10);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        title = new Text("Sudoku");
        title.setStyle("-fx-font-size: 36px; -fx-font-weight: bold;");
        
        // Adds an empty block of space
        Region spacer1 = new Region();
        spacer1.setMinHeight(5);

        subtitle = new Text("By Sophie Liu, Yuqing Peng, & Annabel Zhang");
        subtitle.setStyle("-fx-font-size: 18px; -fx-font-weight: normal;");
        
     // Adds an empty block of space
        Region spacer2 = new Region();
        spacer2.setMinHeight(1);

        singlePlayerButton = new Button("Singleplayer Mode");
        competitiveModeButton = new Button("Competitive Mode");
        quitButton = new Button("Quit");

        root.getChildren().addAll(title, subtitle, spacer1, spacer2, singlePlayerButton, competitiveModeButton, quitButton);

        scene = new Scene(root, 600, 400);
    }

    public Scene getScene() {
        return scene;
    }

    public Button getSinglePlayerButton() {
        return singlePlayerButton;
    }

    public Button getCompetitiveModeButton() {
        return competitiveModeButton;
    }

    public Button getQuitButton() {
        return quitButton;
    }
}
