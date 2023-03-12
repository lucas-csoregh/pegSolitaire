import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Board {
    private Hole[][] gamestate;

    private Player player;
    private int numberOfMoves = 0;

    // TODO add gamestate to history each time it changes
    //public static ArrayList<Hole[][]> history = new ArrayList<>();
    private int nPegs = 0;

    public char getXchar(int index) {
        char[] chars = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        return chars[index -1];
    }

    public void drawSquare(Object[][] gamestate, int x, int y, StringBuilder sb) {
        nPegs = 0;
        HoleStatus status = HoleStatus.OFF_LIMITS;
        if(gamestate instanceof HoleStatus[][] boardTemplate) {
            status = boardTemplate[x][y];
        } else if(gamestate instanceof Hole[][] board) {
            status = board[x][y].getHoleStatus();
        }

        if(status.equals(HoleStatus.PEG)) {
            sb.append(" o ");
            nPegs++;
        } else if(status.equals(HoleStatus.PLAYER)) {
            // s for selected, selected = which PEG the player moves is up to the player
            sb.append(" S ");
            nPegs++;
        } else if(status.equals(HoleStatus.OFF_LIMITS)) {
            sb.append("   ");
        } else if(status.equals(HoleStatus.VACANT)) {
            sb.append(" . ");
        } else if(status.equals(HoleStatus.RULER)) {
            if(y != 0) {
                sb.append(" ").append(y).append(" ");
            } else {
                sb.append(" ").append(getXchar(x)).append(" ");
            }
        }
    }

    public String reset(HoleStatus[][] boardTemplate) {
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
        System.out.print(reset(BoardTemplate.german));
        getUserInput(new ArrayList<Player.Dir>());
    }

    public boolean swapPlayerPeg(int toX, int toY) {
        if(this.player == null) {
            ArrayList<Player.Dir> dirs = getAvailableDirections(toX, toY);
            if(dirs.size() > 0) {
                spawnPlayer(toX, toY);
                return true;
            }
        } else {
            Hole fromHole = gamestate[player.getX()][player.getY()];
            // Only allow user to swap to peg that has available directions once we get there.
            ArrayList<Player.Dir> dirs = getAvailableDirections(toX, toY);
            if(dirs.size() > 0) {
                Hole toHole = gamestate[toX][toY];
                // Only allow user to swap between pegs and nothing else
                if(toHole.getHoleStatus().equals(HoleStatus.PEG)) {
                    fromHole.setHoleStatus(HoleStatus.PEG);
                    toHole.setHoleStatus(HoleStatus.PLAYER);

                    // KEEP
                    //history.add(gamestate);
                    return true;
                }
            }
        }
        return false;
    }

    public void spawnPlayer(int x, int y) {
        player = new Player(gamestate, x, y);
        readAndShowCurrentGamestate();

        // KEEP
        //history.add(gamestate);
    }

    public ArrayList<Player.Dir> getAvailableDirections(int x, int y) {
        ArrayList<Player.Dir> directions = new ArrayList<>();

        boolean down = false;
        boolean up = false;
        boolean left = false;
        boolean right = false;

        if(y>=2) {
            up = gamestate[x][y-2].isVacant() && gamestate[x][y-1].pegOrPlayer();
        }
        if(y < 6) {
            down = gamestate[x][y+2].isVacant() && gamestate[x][y+1].pegOrPlayer();
        }
        if(x>=2) {
            left = gamestate[x-2][y].isVacant() && gamestate[x-1][y].pegOrPlayer();
        }
        if(x < 6) {
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


    public int[] translateChessCoordinates(char[] charr) {
        int x=0;
        int y=charr[1] - '0';

        // make it automatically scale to a bigger gamestate template (needs more characters, so add the rest of the alphabet)
        char[] strs = {'a', 'b', 'c', 'd', 'e', 'f', 'g'};
        for(int i=0; i<strs.length; i++) {
            if(strs[i] == charr[0]) {
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
                readAndShowCurrentGamestate();
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
                readAndShowCurrentGamestate();
            }
        }

        getUserInput(dirs);
    }

    public void readAndShowCurrentGamestate() {
        //boolean again = false;

        if(nPegs != 1) {
            numberOfMoves++;
            System.out.println("move #" + numberOfMoves);
            int size = gamestate.length;
            StringBuilder sb = new StringBuilder();
            for(int y=0; y<size; y++) {
                for (int x = 0; x < size; x++) {
                    drawSquare(gamestate, x, y, sb);
                }
                sb.append("\n");
            }

            System.out.println(sb);

            player.getPlayerPos(gamestate);
            int x = player.getX();
            int y = player.getY();
            ArrayList<Player.Dir> dirs = getAvailableDirections(x, y);

            if (dirs.size() > 0) {
                // show all available directions
                System.out.println("Directions:");
                for(Player.Dir dir: dirs) {
                    System.out.println(dir.name());
                }
            }

            getUserInput(dirs);
        } else {
            // TODO: give special win if the last ball is in 4,4 aka dead center on the board
            System.out.println("YOU WON!");
        }
    }
}