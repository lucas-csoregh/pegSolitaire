import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        //Board pegSolitaire = new Board();

        Application.launch(args);
    }

    @Override
    public void start(Stage mijnStage) {
        GridPane gridpane = new GridPane();

        for(int y=0; y<8; y++) {
            for(int x=0; x<8; x++) {
                Button bt = new Button();
                bt.setPrefSize(50,50);
                bt.setShape(new Circle(1.5));
                gridpane.add(bt, x, y);
            }
        }

        Scene mijnEersteScene = new Scene(gridpane);
        mijnStage.setScene(mijnEersteScene);
        mijnStage.show();
    }
}