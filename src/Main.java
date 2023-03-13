import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        Application.launch(args);

        Board pegSolitaire = new Board();
    }

    @Override
    public void start(Stage mijnStage) {
        GridPane gridpane = new GridPane();

        for(int y=0; y<8; y++) {
            for(int x=0; x<8; x++) {
                Button bt = new Button();
                bt.setPrefSize(70,70);
                bt.setShape(new Circle(1.5));
                //bt.setPadding(new Insets(30, 30, 30, 30));
                gridpane.add(bt, x, y);

                gridpane.setVgap(15);
                gridpane.setHgap(15);
            }
        }

        Scene mijnEersteScene = new Scene(gridpane);
        mijnStage.setScene(mijnEersteScene);
        mijnStage.show();
    }
}