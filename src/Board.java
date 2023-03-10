import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Board {

    // gamestate
    private Hole[][] gamestate = new Hole[8][8];

    private Player player;
    private int numberOfMoves = 0;
    //static ArrayList<Hole> history = new ArrayList<>();
    private int nPegs = 0;

    HoleStatus[][] english_cross = {
            /*
                Template:
                ---------
                {2, 2, 2, 2, 2, 2, 2, 2},
                {2, 1, 1, 0, 0, 0, 1, 1},
                {2, 1, 1, 0, 0, 0, 1, 1},
                {2, 0, 0, 0, 0, 0, 0, 0},
                {2, 0, 0, 0, 3, 0, 0, 0},
                {2, 0, 0, 0, 0, 0, 0, 0},
                {2, 1, 1, 0, 0, 0, 1, 1},
                {2, 1, 1, 0, 0, 0, 1, 1}

                0 = PEG
                1 = off limits
                2 = ruler
                3 = empty
                4 = player


                CLI Result:
                -----------
                    a  b  c  d  e  f  g
                 1        o  o  o
                 2        o  o  o
                 3  o  o  o  o  o  o  o
                 4  o  o  o  .  o  o  o
                 5  o  o  o  o  o  o  o
                 6        o  o  o
                 7        o  o  o
            };*/
            {HoleStatus.OFF_LIMITS, HoleStatus.RULER, HoleStatus.RULER, HoleStatus.RULER, HoleStatus.RULER, HoleStatus.RULER, HoleStatus.RULER, HoleStatus.RULER},
            {HoleStatus.RULER, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS},
            {HoleStatus.RULER, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS},
            {HoleStatus.RULER, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG},
            {HoleStatus.RULER, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.VACANT, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG},
            {HoleStatus.RULER, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG},
            {HoleStatus.RULER, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS},
            {HoleStatus.RULER, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS}
    };

    public char getXchar(int index) {
        char[] chars = {'a', 'b', 'c', 'd', 'e', 'f', 'g'};
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

    public boolean swapPlayerPeg(int toX, int toY) {
        Hole fromHole = gamestate[player.getX()][player.getY()];

        // Only allow user to swap to peg that has available directions once we get there.
        ArrayList<Player.Dir> dirs = getAvailableDirections(toX, toY);
        if(dirs.size() > 0) {
            Hole toHole = gamestate[toX][toY];
            // Only allow user to swap between pegs and nothing else
            if(toHole.getHoleStatus().equals(HoleStatus.PEG)) {
                fromHole.setHoleStatus(HoleStatus.PEG);
                toHole.setHoleStatus(HoleStatus.PLAYER);
                return true;
            }
        }
        return false;
    }

    public void spawnPlayer(int x, int y) {
        player = new Player(gamestate, x, y);
        readAndShowCurrentGamestate();
    }


    public Board() {
        System.out.print(reset(english_cross));
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
        // a1 as in the coordinate scheme that reflects the rulers on each board

        if(dirs.size() > 0) {
            System.out.print("\nEnter up/down/right/left or specify coordinate (ex. a1): ");
        } else {
            System.out.print("\nSpecify coordinate (ex. a1): ");
        }
        answer = scanner.next();

        char[] charr =  answer.toCharArray();
        boolean swapped = false;
        if(charr.length == 2 && Character.isDigit(charr[1]) && Character.isLetter(charr[0])) {
            int[] coord = translateChessCoordinates(charr);
            swapped = swapPlayerPeg(coord[0], coord[1]);
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
    // read gamestate from gamestate (Hole[][] gamestate) and get new player action
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