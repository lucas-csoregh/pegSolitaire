import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Scanner;

public class Board extends Application {
    static ArrayList<Hole[][]> history = new ArrayList<>();
    static Hole[][] gamestate;
    static Player player;
    static int numberOfMoves = 0;
    static char[] alphabet = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    static int nPegs = 0;

    GridPane gridpane = new GridPane();

    /***************
     * MOVE PLAYER *
     ***************/
    public boolean swapPlayerPeg(int toX, int toY) {
        boolean swapped = false;
        if(player == null) {
            ArrayList<Player.Dir> dirs = getAvailableDirections(toX, toY);
            if(dirs.size() > 0) {
                spawnPlayer(toX, toY);
                swapped = true;
            }
        } else {
            Hole fromHole = gamestate[player.getX()][player.getY()];
            // Only allow user to swap to peg that has available directions once we get there.
            ArrayList<Player.Dir> dirs = getAvailableDirections(toX, toY);
            if(dirs.size() > 0) {
                Hole toHole = gamestate[toX][toY];
                // Only allow user to swap between pegs and nothing else
                if(toHole.getHoleStatus().equals(Hole.Status.PEG)) {
                    fromHole.setHoleStatus(Hole.Status.PEG);
                    toHole.setHoleStatus(Hole.Status.PLAYER);
                    swapped = true;
                }
            }
        }
        showGamestateGetInput();
        return swapped;
    }

    public void spawnPlayer(int x, int y) {
        player = new Player(gamestate, x, y);
        //showGamestateGetInput();
    }


    /*********
     * INPUT *
     *********/

    /*
    public void getUserInput(ArrayList<Player.Dir> dirs) {
        Scanner scanner = new Scanner(System.in);
        String answer = "";

        if(dirs.size() > 0) {
            System.out.print("\nEnter up/down/right/left or specify coordinate (ex. a1): ");
        } else {
            // a1 as in the coordinate scheme that reflects the rulers on each board
            System.out.print("\nSpecify coordinate (ex. a1): ");
        }
        answer = scanner.next();

        char[] charr =  answer.toCharArray();
        if(charr.length == 2 && Character.isDigit(charr[1]) && Character.isLetter(charr[0])) {
            int[] coord = translateChessCoordinates(charr);
            boolean swapped = swapPlayerPeg(coord[0], coord[1]);
            if(swapped) {
                showGamestateGetInput();
            }
        }

        Player.Dir dir = Player.Dir.DOWN;
        if (answer.equals("up")) {
            dir = Player.Dir.UP;
        } else if (answer.equals("down")) {
            dir = Player.Dir.DOWN;
        } else if (answer.equals("left")) {
            dir = Player.Dir.LEFT;
        } else if (answer.equals("right")) {
            dir = Player.Dir.RIGHT;
        }

        if(dirs.contains(dir)) {
            boolean jumped = player.takePeg(dir, gamestate);
            if(jumped) {
                showGamestateGetInput();
            }
        }
        getUserInput(dirs);
    }
     */

    /******************************
     * COORDINATES AND DIRECTIONS *
     ******************************/
    /*
    public boolean isValidDestionation(int x, int y) {
        Hole toHole = gamestate[x][y];

        player.getPlayerPos(gamestate);
        int pX = player.getX();
        int pY = player.getY();
        Hole fromHole = gamestate[pX][pY];

        // TODO check if toHole is
        //     * Hole.Status.VACANT
        //     * two coordinates away

        // TODO check if betweenHole is
        //     * Hole.Status.PEG
        //     * one coordinate away

        // TODO check if fromHole is
        //     * two coordinates away

        return false;
    }
    */

    public ArrayList<Player.Dir> getAvailableDirections(int x, int y) {
        ArrayList<Player.Dir> directions = new ArrayList<>();

        boolean down = false;
        boolean up = false;
        boolean left = false;
        boolean right = false;

        if(y>=2) {
            up = gamestate[x][y-2].isVacant() && gamestate[x][y-1].pegOrPlayer();
        }
        if(y < gamestate.length-2) {
            down = gamestate[x][y+2].isVacant() && gamestate[x][y+1].pegOrPlayer();
        }
        if(x>=2) {
            left = gamestate[x-2][y].isVacant() && gamestate[x-1][y].pegOrPlayer();
        }
        if(x < gamestate.length-2) {
            right = gamestate[x+2][y].isVacant() && gamestate[x+1][y].pegOrPlayer();
        }

        if(up) {
            directions.add(Player.Dir.UP);
        }
        if (down) {
            directions.add(Player.Dir.DOWN);
        }
        if (right) {
            directions.add(Player.Dir.RIGHT);
        }
        if (left) {
            directions.add(Player.Dir.LEFT);
        }

        return directions;
    }


    static char getXchar(int index) {
        return alphabet[index -1];
    }

    static public String getChessCoordinate(int x, int y) {
        return getXchar(x) + Integer.toString(y);
    }

    static public int[] translateChessCoordinates(char[] charr) {
        int x=0;
        int y=charr[1] - '0';

        // make it automatically scale to a bigger gamestate template (needs more characters, so add the rest of the alphabet)
        for(int i=0; i<alphabet.length; i++) {
            if(alphabet[i] == charr[0]) {
                x = i+1;
            }
        }

        /*
        In Java, you can convert a character that represents a digit to an integer by subtracting the Unicode value of the character '0' from the Unicode value of the digit.
        This works because the Unicode value of the character '0' is 48,
        and the Unicode values of the digits '0' to '9' are consecutive integers from 48 to 57.

        Here is an example code snippet that shows how to convert a character representing a digit to an integer:

        char myChar = '5';
        int myInt = myChar - '0';
         */
        return new int[]{x, y};
    }


    /*********************
     * SETTING THE BOARD *
     *********************/
    @Override
    public String toString() {
        // simply reads current gamestate
        StringBuilder sb = new StringBuilder();
        for(int y=0; y<gamestate.length; y++) {
            for (int x = 0; x < gamestate.length; x++) {
                drawSquare(gamestate, x, y, sb);
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    public void showGamestateGetInput() {
        // adding history right here because we call this function every time right after we've moved
        history.add(gamestate);

        if(nPegs != 1) {
            numberOfMoves++;
            System.out.println("move #" + numberOfMoves);

            System.out.println(this.toString());

            player.getPlayerPos(gamestate);
            int x = player.getX();
            int y = player.getY();
            ArrayList<Player.Dir> dirs = getAvailableDirections(x, y);

            if (dirs.size() > 0) {
                // show all available directions
                System.out.println("Directions:");
                for(Player.Dir dir: dirs) {
                    System.out.println(dir.name().toLowerCase());
                }
            }

            //getUserInput(dirs);
        } else {
            // TODO: give special win if the last ball is in 4,4 aka dead center on the board
            System.out.println("YOU WON!");
        }
    }

    public void fxAddPeg(int x, int y) {
        //System.out.println("button added");
        Button bt = new Button();
        bt.setPrefSize(70,70);
        bt.setShape(new Circle(1.5));

        gridpane.add(bt, x, y);

        bt.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.printf("%s\n", Board.getChessCoordinate(x, y));
                boolean swapped = swapPlayerPeg(x, y);
            }
        });
    }


    public void fxAddPlayer(int x, int y) {
        Button bt = new Button();
        bt.setPrefSize(70,70);
        bt.setShape(new Circle(1.5));

        Border border = new Border(new BorderStroke(
                Color.RED,
                BorderStrokeStyle.SOLID,
                null,
                new BorderWidths(4)));

        // Set the border on the button
        bt.setBorder(border);

        gridpane.add(bt, x, y);

        bt.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.printf("player at %s\n", Board.getChessCoordinate(x, y));
                // add your custom code here
            }
        });
    }

    public void fxAddHole(int x, int y) {
        //System.out.println("button added");
        Button bt = new Button();
        bt.setPrefSize(70,70);
        bt.setShape(new Circle(1.5));
        //bt.setStyle("-fx-background-color: black");

        // Create a border
        Border border = new Border(new BorderStroke(
                Color.BLACK,
                BorderStrokeStyle.SOLID,
                null,
                new BorderWidths(20)));

        // Set the border on the button
        bt.setBorder(border);

        //bt.setPadding(new Insets(30, 30, 30, 30));
        gridpane.add(bt, x, y);

        /* TODO
        when (clicking or) double clicking on a hole,
        the game assumes you want to move (and take a peg) in that direction
        and the fromHole becomes your current playerPos,
        toHole is the double-clicked hole of course
         */

        bt.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() == 2) {
                System.out.printf("Double clicked hole at coord: %s\n", Board.getChessCoordinate(x, y));
                // TODO Check if it is a valid coordinate to move to
                //  this will require you to rework the takePeg function in the Player class
                //System.out.printf("passed x:%d, y:%d", x, y);
                boolean taken = player.takePeg(gamestate, x, y);
                if(taken) {
                    showGamestateGetInput();
                }
            }
        });
    }
    // TODO MAYBE
    //  give pegs and holes that are available coordinates to move or swap to a
    //  subtle (not full opacity) color coding to the player can see the available
    //  choices visually


    public void fxAddRuler(int x, int y) {
        Label label = new Label();
        if(y == 0) {
            label.setText(String.valueOf(getXchar(x)));
            label.setAlignment(Pos.CENTER);
        } else if (x == 0) {
            label.setText(String.valueOf(y));
        }
        label.setAlignment(Pos.CENTER);
        label.setStyle("-fx-font-size: 25");
        GridPane.setHalignment(label, HPos.CENTER);
        gridpane.add(label, x, y);
    }


    public void drawSquare(Object[][] gamestate, int x, int y, StringBuilder sb) {
        nPegs = 0;
        Hole.Status status = Hole.Status.OFF_LIMITS;
        if(gamestate instanceof Hole.Status[][] boardTemplate) {
            status = boardTemplate[x][y];
        } else if(gamestate instanceof Hole[][] board) {
            status = board[x][y].getHoleStatus();
        }

        if(status.equals(Hole.Status.PEG)) {
            sb.append(" o ");
            nPegs++;

            fxAddPeg(x, y);
        } else if(status.equals(Hole.Status.PLAYER)) {
            // s for selected, selected = which PEG the player moves is up to the player
            sb.append(" S ");
            nPegs++;

            fxAddPlayer(x, y);
        } else if(status.equals(Hole.Status.OFF_LIMITS)) {
            sb.append("   ");
        } else if(status.equals(Hole.Status.VACANT)) {
            sb.append(" . ");

            fxAddHole(x, y);
        } else if(status.equals(Hole.Status.RULER)) {
            fxAddRuler(x, y);
            if(y != 0) {
                sb.append(" ").append(y).append(" ");
            } else {
                sb.append(" ").append(getXchar(x)).append(" ");
            }
        }
    }

    public String reset(Hole.Status[][] boardTemplate) {
        // set the gamestate dimensions to match those of the current boardtemplate
        gamestate = new Hole[boardTemplate.length][boardTemplate[0].length];
        // Resets the game by overwriting the current gamestate with a template
        System.out.println("-- RESET --\n");
        StringBuilder sb = new StringBuilder();

        int size=boardTemplate.length;
        for(int y=0; y<size; y++) {
            for(int x=0; x<size; x++) {
                Hole hole = new Hole(x, y, boardTemplate[x][y]);
                gamestate[x][y] = hole;

                drawSquare(boardTemplate, x, y, sb);
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public Board() {
        System.out.println("Board constructor called");
        //System.out.print(reset(BoardTemplate.german));
        // passing 0 directions bc we know the player needs to spawn first
        //getUserInput(new ArrayList<Player.Dir>());
    }

    @Override
    public void start(Stage mijnStage) {
        System.out.println("start");

        System.out.print(reset(BoardTemplate.german));

        gridpane.setVgap(15);
        gridpane.setHgap(15);
        gridpane.setAlignment(Pos.CENTER);
        Scene mijnEersteScene = new Scene(gridpane);
        mijnStage.setScene(mijnEersteScene);
        mijnStage.show();
    }


    public static void main(String[] args) {
        Application.launch(args);
    }
}