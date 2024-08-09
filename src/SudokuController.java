import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class SudokuController {
    private Sudoku model;
    private SudokuViewer view;

    public SudokuController(Sudoku model, SudokuViewer view) {
        this.model = model;
        this.view = view;
        initialize();
    }

    private void initialize() {
        // Populate the initial board
        view.updateBoard(model.getBoard());

        // Add input validation and interaction handlers
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                TextField cell = view.getCells()[row][col];
                cell.setStyle("-fx-border-color: black;");
                //cell.setStyle("-fx-background-color: #CCCCFF;");
                final int r = row;
                final int c = col;

                cell.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        String character = event.getCharacter();
                        if (!character.matches("[1-9]")) {
                            event.consume(); // Ignore non-numeric input
                            return;
                        }
                        
                    }
                });
                
                cell.addEventFilter(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
                	 @Override
                     public void handle(MouseEvent event) {
                		 
                		 // 3x3 boards
                		 int maxRow = 0;
                		 int maxCol = 0;
                		 
                		 switch (r) {
                		 	case 3:
                		 	case 4:
                		 	case 5:
                		 		maxRow = 3;
                		 		break;
                		 	case 6:
                		 	case 7:
                		 	case 8:
                		 		maxRow = 6;
                		 		break;
                		 }
                		 
                		 switch (c) {
	             		 	case 3:
	             		 	case 4:
	             		 	case 5:
	             		 		maxCol = 3;
	             		 		break;
	             		 	case 6:
	             		 	case 7:
	             		 	case 8:
	             		 		maxCol = 6;
	             		 		break;
                		 }
                		 
                		 for (int i = maxRow; i <= maxRow + 2; i++) {
                			 for (int j = maxCol; j <= maxCol + 2; j++) {
                				 view.getCells()[i][j].setStyle("-fx-background-color: #FBE451; -fx-border-color: black; -fx-border-width: 1");
                			 }
                		 }
             		 
                		 
                		 for (int i = 0; i < 9; i++) {
                			 
                			 if ((r - 2) % 3 == 0) {
                				 view.getCells()[r][i].setStyle("-fx-background-color: #FBE451; -fx-border-color: black; -fx-border-width: 1 1 4 1");
                			 }
                			 else if (r % 3 == 0) {
                				 view.getCells()[r][i].setStyle("-fx-background-color: #FBE451; -fx-border-color: black; -fx-border-width: 4 1 1 1");
                			 }
                			 else {
                				 view.getCells()[r][i].setStyle("-fx-background-color: #FBE451; -fx-border-color: black; -fx-border-width: 1");
                			 }
                		 }
                		 
                		 for (int i = 0; i < 9; i++) {
                   			 if ((c-2) % 3 == 0) {
                   				 view.getCells()[i][c].setStyle("-fx-background-color: #FBE451; -fx-border-color: black; -fx-border-width: 1 4 1 1");
                   			 }
                   			 else if (c % 3 == 0) {
                   				 view.getCells()[i][c].setStyle("-fx-background-color: #FBE451; -fx-border-color: black; -fx-border-width: 1 1 1 4");
                   			 }
                   			 else {
                    			 view.getCells()[i][c].setStyle("-fx-background-color: #FBE451; -fx-border-color: black; -fx-border-width: 1");

                   			 }
                		 }
                		 cell.setStyle("-fx-background-color: #E2CA2D; -fx-border-color: black; -fx-border-width: 1");
                	 }
                });
                
                cell.addEventFilter(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
               	 @Override
                    public void handle(MouseEvent event) {
               	// 3x3 boards
            		 int maxRow = 0;
            		 int maxCol = 0;
            		 
            		 switch (r) {
            		 	case 3:
            		 	case 4:
            		 	case 5:
            		 		maxRow = 3;
            		 		break;
            		 	case 6:
            		 	case 7:
            		 	case 8:
            		 		maxRow = 6;
            		 		break;
            		 }
            		 
            		 switch (c) {
             		 	case 3:
             		 	case 4:
             		 	case 5:
             		 		maxCol = 3;
             		 		break;
             		 	case 6:
             		 	case 7:
             		 	case 8:
             		 		maxCol = 6;
             		 		break;
            		 }
            		 
            		 for (int i = maxRow; i <= maxRow + 2; i++) {
            			 for (int j = maxCol; j <= maxCol + 2; j++) {
            				 view.getCells()[i][j].setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-width: 1");
            			 }
            		 }
               		 for (int i = 0; i < 9; i++) {
               			 view.getCells()[r][i].setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-width: 1");
      
               		 }
               		 for (int i = 0; i < 9; i++) {
               			 view.getCells()[i][c].setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-width: 1");
               		 }
               		 cell.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-width: 1");
               	 }
               });

                cell.setOnAction(e -> {
                	
                    String text = cell.getText();
                    if (text.matches("[1-9]")) {
                        int value = Integer.parseInt(text);
                        if (model.isValidMove(r, c, value)) {
                            model.getBoard()[r][c] = value;
                            
                            //cell.setStyle("-fx-background-color: #CCCCFF;");
                            
                        } else {
                            cell.setText(""); // Clear the invalid input
                        }
                        
                    }
                });
            }
        }

        // Add functionality for a "Solve" button
        view.getSolveButton().setOnAction(e -> {
            if (model.solve()) {
                view.updateBoard(model.getBoard());
            } else {
                // Show a message indicating the puzzle is unsolvable
                System.out.println("Unsolvable puzzle.");
            }
        });

        // Add functionality for a "Clear" button
        view.getClearButton().setOnAction(e -> {
            model.clearBoard();
            view.updateBoard(model.getBoard());
        });

        // Additional initialization as needed
    }
    /*
    private Canvas createGrid(int width, int height) {
    	Canvas grid = new Canvas(width, height);
        GraphicsContext gc = grid.getGraphicsContext2D();
        gc.setLineWidth(3);
        gc.moveTo(155, 0);
        gc.lineTo(155, height);
        gc.stroke();
        return grid;
    }
*/
    public void start(Stage primaryStage) {
        Scene scene = new Scene(view.getRoot(), 470, 470);
        //Canvas grid = createGrid(470, 470);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Sudoku :: Team Snickerdoodle");
        primaryStage.show();
    }
}
