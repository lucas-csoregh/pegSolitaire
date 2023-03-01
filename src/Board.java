import java.util.ArrayList;
import java.util.Scanner;

public class Board {
    final private int maxHoles = 33;
    final private int maxPegs = 32;
    private Peg[] pegs;
    private Hole[] validCoordinates;
    private Hole[][] grid = new Hole[8][8];
    private Player player;

    // Functions like a mere template. Has no relevance anymore after the board has already been created.
    HoleStatus[][] english_cross = {
            /*
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
            };*/
            {HoleStatus.OFF_LIMITS, HoleStatus.RULER, HoleStatus.RULER, HoleStatus.RULER, HoleStatus.RULER, HoleStatus.RULER, HoleStatus.RULER, HoleStatus.RULER},
            {HoleStatus.RULER, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS},
            {HoleStatus.RULER, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS},
            {HoleStatus.RULER, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG},
            {HoleStatus.RULER, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.EMPTY, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG},
            {HoleStatus.RULER, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG},
            {HoleStatus.RULER, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS},
            {HoleStatus.RULER, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS}
    };

    public char getXchar(int index) {
        char[] chars = {'a', 'b', 'c', 'd', 'e', 'f', 'g'};
        return chars[index -1];
    }

    public Hole getHole(Peg peg) {
        return grid[peg.getX()][peg.getY()];
    }

    public Hole getHole(int x, int y, Hole[][] board) {

        /*
        Right now I don't see any other way to fix this problem than to simply loop over the 2D array
        and returning when x==xLoopIndex, y==yLoopIndex if you get what I mean.
         */
        int size = board.length;

        for(int yi=0; yi<size; yi++) {
            for(int xi=0; xi<size; xi++) {
                if(y == yi && x == xi) {
                    return grid[x][y];
                }
            }
        }
       return null;
    }

    // draw the right thing based on the current enum from the loop
    public void drawSquare(HoleStatus[][] board, int x, int y, StringBuilder sb) {

        if(board[x][y] == HoleStatus.PEG) {
            sb.append(" o ");
        } else if(board[x][y] == HoleStatus.PLAYER) {
            // s for selected, selected = which PEG the player moves is up to the player
            sb.append(" S ");
        } else if(board[x][y] == HoleStatus.OFF_LIMITS) {
            sb.append("   ");
        } else if(board[x][y] == HoleStatus.EMPTY) {
            sb.append(" . ");
        } else if(board[x][y] == HoleStatus.RULER) {
            if(y != 0) {
                sb.append(" "+y+" ");
            } else {
                sb.append(" "+getXchar(x)+" ");
            }
        }
    }

    public void drawSquare(Hole[][] board, int x, int y, StringBuilder sb) {

        if(board[x][y].getHoleStatus() == HoleStatus.PEG) {
            sb.append(" o ");
        } else if(board[x][y].getHoleStatus() == HoleStatus.PLAYER) {
            // s for selected, selected = which PEG the player moves is up to the player
            sb.append(" S ");
        } else if(board[x][y].getHoleStatus() == HoleStatus.OFF_LIMITS) {
            sb.append("   ");
        } else if(board[x][y].getHoleStatus() == HoleStatus.EMPTY) {
            sb.append(" . ");
        } else if(board[x][y].getHoleStatus() == HoleStatus.RULER) {
            if(y != 0) {
                sb.append(" "+y+" ");
            } else {
                sb.append(" "+getXchar(x)+" ");
            }
        }
    }


    public String reset(HoleStatus[][] boardTemplate) {
        System.out.println("-- RESET --\n");
        int pegCount = 0;
        int holeCount = 0;
        pegs = new Peg[maxPegs];
        validCoordinates = new Hole[maxHoles];
        StringBuilder sb = new StringBuilder();

        int size=boardTemplate.length;
        // Draw the grid (/Looping over the HoleStatus[][] / Template)
        for(int y=0; y<size; y++) {
            for(int x=0; x<size; x++) {
                Hole hole = new Hole(x, y, boardTemplate[x][y]);
                grid[x][y] = hole;

                boolean createPeg = (boardTemplate[x][y] != HoleStatus.EMPTY);
                boolean validCoordinate = (boardTemplate[x][y] != HoleStatus.OFF_LIMITS) && (boardTemplate[x][y] != HoleStatus.RULER);

                // Conditionally add hole to valid coordinates
                if(validCoordinate) {
                    validCoordinates[holeCount] = hole;
                    holeCount++;

                    if(createPeg) {
                        Peg peg = new Peg(x, y);

                        hole.setPeg(peg);

                        pegs[pegCount] = peg;
                        pegCount++;
                    }
                }

                drawSquare(boardTemplate, x, y, sb);
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public void setPlayerPeg(int x, int y) {
        Hole hole = getHole(x, y, grid);
        // changing the state of the board, which now needs to be refreshed
        hole.setHoleStatus(HoleStatus.PLAYER);
        // associating the player with the peg
        player = new Player(hole.getPeg());

        // refresh the board to show the player once the user has chosen the position they want to start in
        System.out.println();
        refreshBoard();
        Player.Dir[] dirs = getValidDirections();

        System.out.println("Options:");
        for(Player.Dir dir: dirs) {
            if(dir!=null) {
                System.out.println(dir.name());
            }
        }

        System.out.println("\nChoose your direction up/down/right/left");
        Scanner scanner = new Scanner(System.in);
        String direction =  scanner.next();


    }

    public Board() {
        System.out.print(reset(english_cross));
    }

    public Player.Dir[] getValidDirections() {
        StringBuilder sb = new StringBuilder();
        Peg peg = player.playerPeg;
        int x = peg.getX();
        int y = peg.getY();
        //StringBuilder sb = new StringBuilder();
        //System.out.println(x+", "+y);
        sb.append("\n");
        int valid = 0;
        // ---- START BUG AREA

        boolean up = grid[x][y-2].isEmpty() && grid[x][y-1].getHoleStatus() == HoleStatus.PEG;
        boolean down = grid[x][y+2].isEmpty() && grid[x][y+1].getHoleStatus() == HoleStatus.PEG;
        boolean left = grid[x-2][y].isEmpty() && grid[x-1][y].getHoleStatus() == HoleStatus.PEG;
        boolean right = grid[x+2][y].isEmpty() && grid[x+1][y].getHoleStatus() == HoleStatus.PEG;
        // ---- END BUG AREA
        Player.Dir[] directions = new Player.Dir[4];

        // TODO: get the 'f6' type name of each position so you can show it here
        sb.append(x).append(", ").append(y).append(" ").append("def\n");
        if(up) {
            sb.append(x).append(", ").append(y+2).append(" ").append("up\n");
            directions[valid] = Player.Dir.UP;
            valid++;
        } else if (down) {
            sb.append(x).append(", ").append(y-2).append(" ").append("down\n");
            directions[valid] = Player.Dir.DOWN;
            valid++;
        } else if (right) {
            sb.append(x+2).append(", ").append(y).append(" ").append("right\n");
            directions[valid] = Player.Dir.RIGHT;
            valid++;
        } else if (left) {
            sb.append(x-2).append(", ").append(y).append(" ").append("left\n");
            directions[valid] = Player.Dir.LEFT;
            valid++;
        }

        System.out.print(sb);

        if(valid == 1) {
            System.out.println("Excuting only option");
            player.jump(directions[0], grid);
            refreshBoard();
        }

        return directions;
    }

    public void refreshBoard() {

        int size = grid.length;
        StringBuilder sb = new StringBuilder();
        for(int y=0; y<size; y++) {
            for (int x = 0; x < size; x++) {
                drawSquare(grid, x, y, sb);
            }
            sb.append("\n");
        }
        // TODO: allow the user to enter their choice (1, 2, ...)
        // TODO: if there is only one possible direction, automatically move the player there (but still show the option in the output)

        System.out.println(sb);
    }
}

/*
TODO: Write the code so that the user can switch to another peg at any time during the game
 */