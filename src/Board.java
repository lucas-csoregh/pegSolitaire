// TODO BEFORE LAST code golf (iow try to make everything as short as possible)
// TODO LAST make one or two coordinate classes (regular or chess)
//      to do everything to do w coordinates if you think it would make your code more clean/impressive
// TODO MAYBE make playerSpawned an attribute of player instead of a local var
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Board {

    // gamestate
    private Hole[][] gamestate = new Hole[8][8];
    private Player player;

    private int frames = 0;

    static ArrayList<Hole> history = new ArrayList<>();



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

    // draw the right thing based on the current enum from the loop
    public void drawSquare(HoleStatus[][] gamestate, int x, int y, StringBuilder sb) {

        if(gamestate[x][y] == HoleStatus.PEG) {
            sb.append(" o ");
        } else if(gamestate[x][y] == HoleStatus.PLAYER) {
            // s for selected, selected = which PEG the player moves is up to the player
            sb.append(" S ");
        } else if(gamestate[x][y] == HoleStatus.OFF_LIMITS) {
            sb.append("   ");
        } else if(gamestate[x][y] == HoleStatus.VACANT) {
            sb.append(" . ");
        } else if(gamestate[x][y] == HoleStatus.RULER) {
            if(y != 0) {
                sb.append(" ").append(y).append(" ");
            } else {
                sb.append(" ").append(getXchar(x)).append(" ");
            }
        }
    }

    public void drawSquare(Hole[][] gamestate, int x, int y, StringBuilder sb) {

        if(gamestate[x][y].getHoleStatus() == HoleStatus.PEG) {
            sb.append(" o ");
        } else if(gamestate[x][y].getHoleStatus() == HoleStatus.PLAYER) {
            // s for selected, selected = which PEG the player moves is up to the player
            sb.append(" S ");
        } else if(gamestate[x][y].getHoleStatus() == HoleStatus.OFF_LIMITS) {
            sb.append("   ");
        } else if(gamestate[x][y].getHoleStatus() == HoleStatus.VACANT) {
            sb.append(" . ");
        } else if(gamestate[x][y].getHoleStatus() == HoleStatus.RULER) {
            if(y != 0) {
                sb.append(" ").append(y).append(" ");
            } else {
                sb.append(" ").append(getXchar(x)).append(" ");
            }
        }
    }


    public String reset(HoleStatus[][] boardTemplate) {
        System.out.println("-- RESET --\n");
        int holeCount = 0;
        //pegs = new Peg[maxPegs];
        //private Peg[] pegs;
        int maxHoles = 33;
        Hole[] validCoordinates = new Hole[maxHoles];
        StringBuilder sb = new StringBuilder();

        int size=boardTemplate.length;
        // Draw the gamestate (/Looping over the HoleStatus[][] / Template)
        for(int y=0; y<size; y++) {
            for(int x=0; x<size; x++) {
                Hole hole = new Hole(x, y, boardTemplate[x][y]);
                gamestate[x][y] = hole;

                //boolean createPeg = (boardTemplate[x][y] != HoleStatus.VACANT);
                boolean validCoordinate = (boardTemplate[x][y] != HoleStatus.OFF_LIMITS) && (boardTemplate[x][y] != HoleStatus.RULER);

                // Conditionally add hole to valid coordinates
                if(validCoordinate) {
                    validCoordinates[holeCount] = hole;
                    holeCount++;
                }

                drawSquare(boardTemplate, x, y, sb);
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public void movePlayer(int toX, int toY) {
        if(containsCoordinate(getAvailablePositions(), toX, toY)) {
            //System.out.println("valid coordinate");

            Hole fromHole = gamestate[player.getX()][player.getY()];

            Board.history.add(fromHole);
            fromHole.setHoleStatus(HoleStatus.PEG);

            Hole toHole = gamestate[toX][toY];
            toHole.setHoleStatus(HoleStatus.PLAYER);

            //System.out.println();
            refreshBoard();
        } else {
            // IS COORDINATE
            System.out.println("Correct format, but not a valid option.\nPlease select one of the possible player coordinates listed above.");
            // TODO OPTIONAL show the options again to the user
            specifyCoordinatePrompt(getAvailableDirections(player.getX(), player.getY()), getAvailablePositions());
        }
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

    public Hole[] getAvailablePositions() {
        // DO LATER: undo hard coding
        Hole[] possiblePlayerPositions = new Hole[33];
        int validPositions = 0;
        int size = gamestate.length;
        for(int y=0; y<size; y++) {
            for(int x = 0; x < size; x++) {
                Player.Dir[] dirs = getAvailableDirections(x, y);
                int validDirs = 0;
                for(Player.Dir dir: dirs) {
                    if(dir != null) {
                        validDirs++;
                    }
                }

                if(validDirs > 0) {
                    possiblePlayerPositions[validPositions] = gamestate[x][y];
                    validPositions++;
                }
            }
        }

        return possiblePlayerPositions;
    }

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

    public void specifyCoordinatePrompt(Player.Dir[] dirs, Hole[] coords) {
        /* Modes
            1. choose coord
            2. chooose coord or direction
            3. choose direction
         */
        int validDirs=0;
        int validCoords=0;
        for(Player.Dir dir: dirs) {
            if(dir!=null) {
                validDirs++;
            }
        }
        for(Hole coord: coords) {
            if(coord!=null) {
               validCoords++;
            }
        }
        int mode =0;
        if(validDirs > 0 && validCoords > 0) {
            mode = 2;
        } else if(validCoords > 0 && validDirs == 0) {
            mode = 1;
        }

        Scanner scanner = new Scanner(System.in);
        String answer = "";
        if(mode == 2) {
            // a1 as in the coordinate scheme that reflects the rulers on the gamestate
            System.out.print("\nEnter up/down/right/left or specify coordinate (ex. a1): ");
            answer = scanner.next();
        } else if(mode == 1) {
            System.out.print("\nSpecify coordinate (ex. a1): ");
            answer = scanner.next();
        }


        if(mode == 2 || mode == 1) {
            char[] charr =  answer.toCharArray();
            if(Character.isDigit(charr[1]) && Character.isLetter(charr[0])) {
                //System.out.println("is coordinate");
                int[] coord = translateChessCoordinates(charr);
                movePlayer(coord[0], coord[1]);
            }
        }


        if(mode == 2) {
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
    }

    // read gamestate from gamestate (Hole[][] gamestate) and get new player action
    public void refreshBoard() {
        frames++;
        System.out.println("move #" + frames);
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

        Hole[] possiblePositions = getAvailablePositions();
        for(Hole possiblePosition: possiblePositions) {
            if(possiblePosition != null) {
                System.out.println(possiblePosition);
            }
        }

        specifyCoordinatePrompt(getAvailableDirections(player.getX(), player.getY()), getAvailablePositions());
        refreshBoard();
    }
}