public class Board {
    final private int maxHoles = 33;
    final private int maxPegs = 32;
    private Peg[] pegs;
    /*
    when we loop over the HoleStatus[][] at creation,
    we need to create ... when
        pegs        !HoleStatus.OFF_LIMITS && !HoleStatusEMPTY
        and holes   !HoleStatus.OFF_LIMITS

    this way, we can pass the valid x and y coordinates as values
     */
    private Hole[] validCoordinates;
    private int pegCount = 0;
    private int holeCount = 0;
    private Hole[][] grid = new Hole[8][8];
    static Player player;

    // Functions like a mere template. Has no relevance anymore after the board has already been created.
    HoleStatus[][] english_cross = {
            /*
                OLD

                {2, 2, 2, 2, 2, 2, 2, 2},
                {2, 1, 1, 0, 0, 0, 1, 1},
                {2, 1, 1, 0, 0, 0, 1, 1},
                {2, 0, 0, 0, 0, 0, 0, 0},
                {2, 0, 4, 0, 3, 0, 0, 0},
                {2, 0, 0, 0, 0, 0, 0, 0},
                {2, 1, 1, 0, 0, 0, 1, 1},
                {2, 1, 1, 0, 0, 0, 1, 1}

                0 = marble
                1 = off limits
                2 = ruler
                3 = empty
                4 = player

                peg = !off limits, !ruler, !empty
                hole = !off limits, !ruler

            is still symbolically equivalent to HoleStatus[][] english_cross
            };*/
            {HoleStatus.OFF_LIMITS, HoleStatus.RULER, HoleStatus.RULER, HoleStatus.RULER, HoleStatus.RULER, HoleStatus.RULER, HoleStatus.RULER, HoleStatus.RULER},
            {HoleStatus.RULER, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS, HoleStatus.MARBLE, HoleStatus.MARBLE, HoleStatus.MARBLE, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS},
            {HoleStatus.RULER, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS, HoleStatus.MARBLE, HoleStatus.MARBLE, HoleStatus.MARBLE, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS},
            {HoleStatus.RULER, HoleStatus.MARBLE, HoleStatus.MARBLE, HoleStatus.MARBLE, HoleStatus.MARBLE, HoleStatus.MARBLE, HoleStatus.MARBLE, HoleStatus.MARBLE},
            {HoleStatus.RULER, HoleStatus.MARBLE, HoleStatus.MARBLE, HoleStatus.MARBLE, HoleStatus.EMPTY, HoleStatus.MARBLE, HoleStatus.MARBLE, HoleStatus.MARBLE},
            {HoleStatus.RULER, HoleStatus.MARBLE, HoleStatus.MARBLE, HoleStatus.MARBLE, HoleStatus.MARBLE, HoleStatus.MARBLE, HoleStatus.MARBLE, HoleStatus.MARBLE},
            {HoleStatus.RULER, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS, HoleStatus.MARBLE, HoleStatus.MARBLE, HoleStatus.MARBLE, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS},
            {HoleStatus.RULER, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS, HoleStatus.MARBLE, HoleStatus.MARBLE, HoleStatus.MARBLE, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS}
    };

    public char getXchar(int index) {
        char[] chars = {'a', 'b', 'c', 'd', 'e', 'f', 'g'};
        return chars[index -1];
    }

    // TODO: test this function
    // [x]: create a gethole function that takes a peg as a parameter and based on that can find that
    public Hole getHole(Peg peg) {
        return grid[peg.getX()][peg.getY()];
    }

    // TODO: test this function
    // [x]: create a function that retrieves a peg based on its coordinates on the board
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
        if(board[x][y] == HoleStatus.MARBLE) {
            sb.append(" o ");
        } else if(board[x][y] == HoleStatus.PLAYER) {
            // s for selected, selected = which marble the player moves is up to the player
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

    public String reset(HoleStatus[][] boardTemplate) {
        System.out.println("-- RESET --\n");
        pegCount = 0;
        holeCount = 0;
        pegs = new Peg[maxPegs];
        validCoordinates = new Hole[maxHoles];
        StringBuilder sb = new StringBuilder();
        //StringBuilder graph = new StringBuilder();

        int size=boardTemplate.length;
        // Draw the grid (/Looping over the HoleStatus[][])
        for(int y=0; y<size; y++) {
            for(int x=0; x<size; x++) {
                Hole hole = new Hole(x, y, boardTemplate[x][y]);
                grid[x][y] = hole;

                // Conditionally create peg
                boolean createPeg = (boardTemplate[x][y] != HoleStatus.EMPTY);
                boolean validCoordinate = (boardTemplate[x][y] != HoleStatus.OFF_LIMITS) && (boardTemplate[x][y] != HoleStatus.RULER);

                // Conditionally add hole to valid coordinates
                if(validCoordinate) {
                    validCoordinates[holeCount] = hole;
                    holeCount++;

                    if(createPeg) {
                        Peg peg = new Peg(x, y);

                        hole.setPeg(peg);
                        //System.out.println("Hole: "+holeCount+", Peg: "+peg);

                        pegs[pegCount] = peg;
                        pegCount++;
                    }
                }

                drawSquare(boardTemplate, x, y, sb);
                //graph.append("{x="+x+", y="+y+"}");
            }
            sb.append("\n");
            //graph.append("\n");
        }
        //System.out.print("\n"+ graph + "\n");
        return sb.toString();
    }



    public void setPlayerPeg(int x, int y) {
        player = new Player(getHole(x, y, grid).getPeg());
    }

    public Board() {
        //reset(english_cross);

        System.out.print(reset(english_cross));

        /*
        System.out.println("\nPresenting valid coordinates and their contents (sanity check, must match)");
        System.out.println("--------------------------------------------------------------------------");
        for(Hole hole: validCoordinates) {
            System.out.printf("x:%s, y:%s\t", getXchar(hole.getX()), hole.getY());
            if(hole.getPeg() != null) {
                System.out.print(hole.getPeg() + "\n");
            } else {
                System.out.print("This coordinate is empty\n");
            }
        }
         */
    }
}
