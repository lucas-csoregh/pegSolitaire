import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Board {
    private Peg[] pegs;
    private Hole[] validCoordinates;

    // gamestate
    private Hole[][] grid = new Hole[8][8];
    private Player player;

    private Player.Dir[] dirs;

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
                sb.append(" ").append(y).append(" ");
            } else {
                sb.append(" ").append(getXchar(x)).append(" ");
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
                sb.append(" ").append(y).append(" ");
            } else {
                sb.append(" ").append(getXchar(x)).append(" ");
            }
        }
    }


    public String reset(HoleStatus[][] boardTemplate) {
        System.out.println("-- RESET --\n");
        int pegCount = 0;
        int holeCount = 0;
        int maxPegs = 32;
        pegs = new Peg[maxPegs];
        int maxHoles = 33;
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
    }

    public Board() {
        System.out.print(reset(english_cross));
    }

    public Hole[] possiblePlayerPositions() {
        // DO LATER: undo hard coding
        Hole[] possiblePlayerPositions = new Hole[33];
        int validPositions = 0;
        int size = grid.length;
        for(int y=0; y<size; y++) {
            for(int x = 0; x < size; x++) {
                /*
                System.out.println(validPositions);
                System.out.println(getValidDirections(x, y).length);
                System.out.println(Arrays.toString(getValidDirections(x, y)));
                */
                Player.Dir[] dirs = getValidDirections(x, y);
                int validDirs = 0;
                for(Player.Dir dir: dirs) {
                    if(dir != null) {
                        validDirs++;
                    }
                }



                if(validDirs > 0) {
                    possiblePlayerPositions[validPositions] = getHole(x, y, grid);
                    validPositions++;
                }
            }
        }

        return possiblePlayerPositions;
    }

    public Player.Dir[] getValidDirections(int x, int y) {
        //StringBuilder sb = new StringBuilder();
        //StringBuilder sb = new StringBuilder();
        //System.out.println(x+", "+y);
        //sb.append("\n");

        int valid = 0;

        boolean down = false;
        boolean up = false;
        boolean left = false;
        boolean right = false;

        if(y>=2) {
            up = grid[x][y-2].isEmpty() && grid[x][y-1].getHoleStatus() == HoleStatus.PEG;
        }
        if(y < 6) {
            down = grid[x][y+2].isEmpty() && grid[x][y+1].getHoleStatus() == HoleStatus.PEG;
        }
        if(x>=2) {
            left = grid[x-2][y].isEmpty() && grid[x-1][y].getHoleStatus() == HoleStatus.PEG;
        }
        if(x < 6) {
            right = grid[x+2][y].isEmpty() && grid[x+1][y].getHoleStatus() == HoleStatus.PEG;
        }
        Player.Dir[] directions = new Player.Dir[4];

        // EXTRA: get the 'f6' type name of each position so you can show it here
        //sb.append(x).append(", ").append(y).append(" ").append("def\n");
        if(up) {
            //sb.append(x).append(", ").append(y-2).append(" ").append("up\n");
            directions[valid] = Player.Dir.UP;
            valid++;
        } else if (down) {
            //sb.append(x).append(", ").append(y+2).append(" ").append("down\n");
            directions[valid] = Player.Dir.DOWN;
            valid++;
        } else if (right) {
            //sb.append(x+2).append(", ").append(y).append(" ").append("right\n");
            directions[valid] = Player.Dir.RIGHT;
            valid++;
        } else if (left) {
            //sb.append(x-2).append(", ").append(y).append(" ").append("left\n");
            directions[valid] = Player.Dir.LEFT;
            valid++;
        }

        //System.out.print(sb);

        return directions;
    }


    // read board from gamestate (Hole[][] grid) and get new player action
    public void refreshBoard() {
        int size = grid.length;
        StringBuilder sb = new StringBuilder();
        for(int y=0; y<size; y++) {
            for (int x = 0; x < size; x++) {
                drawSquare(grid, x, y, sb);
            }
            sb.append("\n");
        }

        System.out.println(sb);

        //Player.Dir[] dirs = getValidDirections();

        Peg peg = player.playerPeg;
        int x = peg.getX();
        int y = peg.getY();
        dirs = getValidDirections(x, y);

        System.out.println("Options:");
        // show all valid directions
        int validDirs = 0;
        for(Player.Dir dir: dirs) {
            if(dir!=null) {
                System.out.println(dir.name());
                validDirs++;
            }
        }

        // TODO show all valid coordinates to select as new playerPeg
        Hole[] possiblePositions = possiblePlayerPositions();
        int validPositions = 0;
        for(Hole possiblePosition: possiblePositions) {
            if(possiblePosition != null) {
                System.out.println(possiblePosition);
            }
        }

        Scanner scanner = new Scanner(System.in);
        if(validDirs == 0) {

            System.out.print("\nSpecify coordinate (ex. a1): ");
            String answer = scanner.next();
            System.out.println(answer);
            System.out.println(answer.toCharArray());

            // TODO: Write the code so that the user can switch to another peg at any time during the game
        } else {
            //System.out.print("\nEnter up/down/right/left");
            //String direction =  scanner.next();

            boolean validAnswer = false;

            while(!validAnswer) {
                // a1 as in the coordinate scheme that reflects the rulers on the board
                System.out.print("\nEnter up/down/right/left or specify coordinate (ex. a1): ");
                String answer = scanner.next();

                char[] charr =  answer.toCharArray();
                if(Character.isDigit(charr[1])) {
                    System.out.println("is coordinate");
                } else {
                    switch (answer) {
                        case "up":
                            // jump up
                            player.jump(Player.Dir.UP, grid);
                            validAnswer = true;
                            break;
                        case "down":
                            // jump down
                            player.jump(Player.Dir.DOWN, grid);
                            validAnswer = true;
                            break;
                        case "left":
                            player.jump(Player.Dir.LEFT, grid);
                            // jump left
                            validAnswer = true;
                            break;
                        case "right":
                            player.jump(Player.Dir.RIGHT, grid);
                            // jump right
                            validAnswer = true;
                            break;
                        default:
                            break;
                    }
                }
                System.out.println();
            }
            refreshBoard();
        }
    }
}