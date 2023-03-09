// TODO BEFORE LAST code golf (iow try to make everything as short as possible)
// TODO LAST make one or two coordinate classes (regular or chess)
//      to do everything to do w coordinates if you think it would make your code more clean/impressive
import java.util.Scanner;

public class Board {

    // gamestate
    private Hole[][] gamestate = new Hole[8][8];
    private Player player;
    private int numberOfMoves = 0;
    //static ArrayList<Hole> history = new ArrayList<>();

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
        HoleStatus status = HoleStatus.OFF_LIMITS;
        if(gamestate instanceof HoleStatus[][] boardTemplate) {
            status = boardTemplate[x][y];
        } else if(gamestate instanceof Hole[][] board) {
            status = board[x][y].getHoleStatus();
        }

        if(status.equals(HoleStatus.PEG)) {
            sb.append(" o ");
        } else if(status.equals(HoleStatus.PLAYER)) {
            // s for selected, selected = which PEG the player moves is up to the player
            sb.append(" S ");
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
        /* Doc:
         * Resets the game by overwriting the current gamestate with a template
         */
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

    public void movePlayer(int toX, int toY) {
        //System.out.println("valid coordinate");

        Hole fromHole = gamestate[player.getX()][player.getY()];

        // TODO BUG this should set the hole to whatever it was before the player touched it
        // TODO BUG, I can move to coordinates that are not available
        fromHole.setHoleStatus(HoleStatus.PEG);

        Hole toHole = gamestate[toX][toY];
        toHole.setHoleStatus(HoleStatus.PLAYER);

        //System.out.println();
        refreshBoard();
    }

    public void spawnPlayer(int x, int y) {
        player = new Player(gamestate, x, y);

        // refresh the gamestate to show the player once the user has chosen the position they want to start in
        System.out.println();
        refreshBoard();
    }


    public Board() {
        System.out.print(reset(english_cross));
    }

    /*
    public Hole[] getAvailableCoordinates() {
        // DO LATER: undo hard coding
        Hole[] availableCoordinates = new Hole[33];
        int coords = 0;
        int size = gamestate.length;
        for(int y=0; y<size; y++) {
            for(int x=0; x<size; x++) {
                Player.Dir[] dirs = getAvailableDirections(x, y);
                int validDirs = 0;
                for(Player.Dir dir: dirs) {
                    if(dir != null) {
                        validDirs++;
                    }
                }

                if(validDirs > 0) {
                    availableCoordinates[coords] = gamestate[x][y];
                    coords++;
                }
            }
        }

        return availableCoordinates;
    }
     */

    public Player.Dir[] getAvailableDirections(int x, int y) {
        int valid = 0;

        boolean down = false;
        boolean up = false;
        boolean left = false;
        boolean right = false;

        if(y>=2) {
            up = gamestate[x][y-2].getHoleStatus().equals(HoleStatus.VACANT) && gamestate[x][y-1].getHoleStatus().equals(HoleStatus.PEG);
        }
        if(y < 6) {
            down = gamestate[x][y+2].getHoleStatus().equals(HoleStatus.VACANT) && gamestate[x][y+1].getHoleStatus().equals(HoleStatus.PEG);
        }
        if(x>=2) {
            left = gamestate[x-2][y].getHoleStatus().equals(HoleStatus.VACANT) && gamestate[x-1][y].getHoleStatus().equals(HoleStatus.PEG);
        }
        if(x < 6) {
            right = gamestate[x+2][y].getHoleStatus().equals(HoleStatus.VACANT) && gamestate[x+1][y].getHoleStatus().equals(HoleStatus.PEG);
        }
        Player.Dir[] directions = new Player.Dir[4];

        if(up) {
            directions[valid] = Player.Dir.UP;
            valid++;
        } else if (down) {
            directions[valid] = Player.Dir.DOWN;
            valid++;
        } else if (right) {
            directions[valid] = Player.Dir.RIGHT;
            valid++;
        } else if (left) {
            directions[valid] = Player.Dir.LEFT;
            valid++;
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

    public boolean containsCoordinate(Hole[] possiblePositions, int x, int y) {
        boolean containsCoordinate = false;
        for(Hole hole: possiblePositions) {
            if(hole!= null && hole.getX() == x && hole.getY() == y) {
                //System.out.println(hole.getX()+" "+hole.getY() +" | "+ x+" "+y);
                containsCoordinate  = true;
            }
        }
        return containsCoordinate;
    }

    public boolean containsDirection(Player.Dir[] dirs, Player.Dir findDir) {
        boolean containsDirection = false;
        for(Player.Dir dir: dirs) {
            if(dir != null && dir == findDir) {
                containsDirection = true;
            }
        }
        return containsDirection;
    }

    public void specifyCoordinatePrompt() {
        /* Modes
            1. choose coord
            2. chooose coord or direction
         */
        Player.Dir[] dirs = getAvailableDirections(player.getX(), player.getY());

        int validDirs=0;
        int validCoords=0;
        for(Player.Dir dir: dirs) {
            if(dir!=null) {
                validDirs++;
            }
        }


        Scanner scanner = new Scanner(System.in);
        String answer = "";
        // a1 as in the coordinate scheme that reflects the rulers on the gamestate
        System.out.print("\nEnter up/down/right/left or specify coordinate (ex. a1): ");
        answer = scanner.next();



        char[] charr =  answer.toCharArray();
        if(Character.isDigit(charr[1]) && Character.isLetter(charr[0]) && charr.length == 2) {
            //System.out.println("is coordinate");
            int[] coord = translateChessCoordinates(charr);
            movePlayer(coord[0], coord[1]);
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
        if(containsDirection(dirs, dir)) {
            player.jump(dir, gamestate);
        }
    }

    // read gamestate from gamestate (Hole[][] gamestate) and get new player action
    public void refreshBoard() {
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

        //Peg peg = player.playerPeg;
        player.getPlayerPos(gamestate);
        int x = player.getX();
        int y = player.getY();
        Player.Dir[] dirs = getAvailableDirections(x, y);

        System.out.println("Options:");
        // show all valid directions
        int validDirs = 0;
        for(Player.Dir dir: dirs) {
            if(dir!=null) {
                System.out.println(dir.name());
                validDirs++;
            }
        }

        specifyCoordinatePrompt();
        refreshBoard();
    }
}